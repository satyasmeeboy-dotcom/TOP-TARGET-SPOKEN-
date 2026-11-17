package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.BilingualMode
import com.example.model.SkillType
import com.example.model.StudentLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

private val Context.settingsDataStore by preferencesDataStore(name = "top_target_prefs")

class StudentRepository(
    private val context: Context,
    private val dao: AppDao
) {
    private object PrefKeys {
        val BILINGUAL_MODE = stringPreferencesKey("bilingual_mode")
        val SPEECH_RATE = floatPreferencesKey("speech_rate")
        val AUTO_SPEAK_TEACHER = booleanPreferencesKey("auto_speak_teacher")
        val OPTIONAL_SYNC_ENABLED = booleanPreferencesKey("optional_sync_enabled")
    }

    val profileFlow: Flow<StudentProfileEntity?> = dao.observeProfile()
    val topicProgressFlow: Flow<List<TopicProgressEntity>> = dao.observeTopicProgress()
    val mistakesFlow: Flow<List<MistakeLogEntity>> = dao.observeMistakes()
    val learnedWordsFlow: Flow<List<LearnedWordEntity>> = dao.observeLearnedWords()
    val savedNewsFlow: Flow<List<SavedNewsEntity>> = dao.observeSavedNews()
    val badgesFlow: Flow<List<AchievementBadgeEntity>> = dao.observeBadges()

    fun observeTodayStudyTasks(): Flow<List<DailyStudyTaskEntity>> {
        val today = currentEpochDay()
        return dao.observeDailyTasks(today)
    }

    val bilingualModeFlow: Flow<BilingualMode> = context.settingsDataStore.data.map { prefs ->
        val raw = prefs[PrefKeys.BILINGUAL_MODE] ?: BilingualMode.BILINGUAL.name
        runCatching { BilingualMode.valueOf(raw) }.getOrDefault(BilingualMode.BILINGUAL)
    }

    val speechRateFlow: Flow<Float> = context.settingsDataStore.data.map { prefs ->
        prefs[PrefKeys.SPEECH_RATE] ?: 0.92f
    }

    val autoSpeakTeacherFlow: Flow<Boolean> = context.settingsDataStore.data.map { prefs ->
        prefs[PrefKeys.AUTO_SPEAK_TEACHER] ?: true
    }

    val optionalSyncFlow: Flow<Boolean> = context.settingsDataStore.data.map { prefs ->
        prefs[PrefKeys.OPTIONAL_SYNC_ENABLED] ?: false
    }

    suspend fun ensureAnonymousProfileExists(): StudentProfileEntity {
        val today = currentEpochDay()
        val existing = dao.getProfileOnce()
        if (existing != null) {
            // Update daily streak gracefully without collecting personal info
            val updated = when {
                existing.lastActiveEpochDay == today -> existing
                existing.lastActiveEpochDay == today - 1 -> existing.copy(
                    streakDays = existing.streakDays + 1,
                    lastActiveEpochDay = today
                )
                else -> existing.copy(
                    streakDays = if (existing.lastActiveEpochDay == 0L) 1 else 1,
                    lastActiveEpochDay = today
                )
            }
            if (updated != existing) dao.upsertProfile(updated)
            return updated
        }

        val fresh = StudentProfileEntity(
            anonymousStudentId = generateAnonymousId(),
            nickname = "",
            currentLevel = StudentLevel.BEGINNER.name,
            stars = 5,
            xp = 20,
            streakDays = 1,
            lastActiveEpochDay = today
        )
        dao.upsertProfile(fresh)
        unlockBadgeIfAbsent(
            badgeId = "first_step",
            titleEn = "First Target Step",
            titleHi = "पहला कदम",
            descriptionEn = "Started your English journey with Top Target Spoken English!"
        )
        return fresh
    }

    suspend fun updateNickname(nickname: String) {
        val current = ensureAnonymousProfileExists()
        dao.upsertProfile(current.copy(nickname = nickname.trim().take(24)))
    }

    suspend fun setBilingualMode(mode: BilingualMode) {
        context.settingsDataStore.edit { it[PrefKeys.BILINGUAL_MODE] = mode.name }
    }

    suspend fun setSpeechRate(rate: Float) {
        context.settingsDataStore.edit { it[PrefKeys.SPEECH_RATE] = rate.coerceIn(0.65f, 1.35f) }
    }

    suspend fun setAutoSpeakTeacher(enabled: Boolean) {
        context.settingsDataStore.edit { it[PrefKeys.AUTO_SPEAK_TEACHER] = enabled }
    }

    suspend fun setOptionalSync(enabled: Boolean) {
        context.settingsDataStore.edit { it[PrefKeys.OPTIONAL_SYNC_ENABLED] = enabled }
    }

    suspend fun recordAssessmentResult(
        level: StudentLevel,
        weakTopicIds: List<String>,
        skillScores: Map<SkillType, Int>,
        attempted: Int,
        correct: Int
    ) {
        val current = ensureAnonymousProfileExists()
        val updated = current.copy(
            currentLevel = level.name,
            assessmentCompleted = true,
            recommendedTopicIdsCsv = weakTopicIds.ifEmpty { listOf("tenses", "articles", "preposition") }.joinToString(","),
            totalAttempted = current.totalAttempted + attempted,
            totalCorrect = current.totalCorrect + correct,
            stars = current.stars + 10,
            xp = current.xp + (correct * 15) + 25,
            grammarMastery = skillScores[SkillType.GRAMMAR] ?: current.grammarMastery,
            vocabularyMastery = skillScores[SkillType.VOCABULARY] ?: current.vocabularyMastery,
            speakingMastery = skillScores[SkillType.SPEAKING] ?: current.speakingMastery,
            listeningMastery = skillScores[SkillType.LISTENING] ?: current.listeningMastery,
            readingMastery = skillScores[SkillType.READING] ?: current.readingMastery,
            writingMastery = skillScores[SkillType.WRITING] ?: current.writingMastery
        )
        dao.upsertProfile(updated)
        unlockBadgeIfAbsent(
            badgeId = "assessment_done",
            titleEn = "Level Discovered",
            titleHi = "स्तर की पहचान",
            descriptionEn = "Completed the full Knowledge Assessment and unlocked a personalized plan."
        )
    }

    suspend fun recordPracticeAttempt(
        skill: SkillType,
        correctCount: Int,
        totalCount: Int,
        earnedStars: Int = 2,
        earnedXp: Int = 20
    ): StudentLevel? {
        val current = ensureAnonymousProfileExists()
        val boost = if (totalCount > 0) ((correctCount.toFloat() / totalCount) * 8f).toInt().coerceAtLeast(1) else 2
        val newGrammar = if (skill == SkillType.GRAMMAR) (current.grammarMastery + boost).coerceAtMost(100) else current.grammarMastery
        val newVocab = if (skill == SkillType.VOCABULARY) (current.vocabularyMastery + boost).coerceAtMost(100) else current.vocabularyMastery
        val newSpeaking = if (skill == SkillType.SPEAKING) (current.speakingMastery + boost).coerceAtMost(100) else current.speakingMastery
        val newListening = if (skill == SkillType.LISTENING) (current.listeningMastery + boost).coerceAtMost(100) else current.listeningMastery
        val newReading = if (skill == SkillType.READING) (current.readingMastery + boost).coerceAtMost(100) else current.readingMastery
        val newWriting = if (skill == SkillType.WRITING) (current.writingMastery + boost).coerceAtMost(100) else current.writingMastery

        val newAttempted = current.totalAttempted + totalCount
        val newCorrect = current.totalCorrect + correctCount
        val avgMastery = (newGrammar + newVocab + newSpeaking + newListening + newReading + newWriting) / 6
        val accuracy = if (newAttempted > 0) newCorrect.toFloat() / newAttempted.toFloat() else 0f

        val oldLevel = runCatching { StudentLevel.valueOf(current.currentLevel) }.getOrDefault(StudentLevel.BEGINNER)
        val detectedLevel = when {
            newAttempted >= 12 && avgMastery >= 78 && accuracy >= 0.78f -> StudentLevel.ADVANCED
            newAttempted >= 8 && avgMastery >= 58 && accuracy >= 0.65f -> StudentLevel.INTERMEDIATE
            newAttempted >= 4 && avgMastery >= 36 && accuracy >= 0.50f -> StudentLevel.ELEMENTARY
            else -> oldLevel
        }
        val finalLevel = if (detectedLevel.ordinal > oldLevel.ordinal) detectedLevel else oldLevel

        val updated = current.copy(
            currentLevel = finalLevel.name,
            totalAttempted = newAttempted,
            totalCorrect = newCorrect,
            stars = current.stars + earnedStars,
            xp = current.xp + earnedXp,
            grammarMastery = newGrammar,
            vocabularyMastery = newVocab,
            speakingMastery = newSpeaking,
            listeningMastery = newListening,
            readingMastery = newReading,
            writingMastery = newWriting
        )
        dao.upsertProfile(updated)
        if (updated.stars >= 25) {
            unlockBadgeIfAbsent(
                badgeId = "rising_star",
                titleEn = "Rising English Star",
                titleHi = "उभरता सितारा",
                descriptionEn = "Collected 25+ learning stars through consistent practice!"
            )
        }
        return if (finalLevel != oldLevel) finalLevel else null
    }

    suspend fun updateTopicProgress(
        topicId: String,
        quizScore: Int,
        totalQuiz: Int,
        completedExercise: Boolean = false,
        homeworkDone: Boolean = false,
        revised: Boolean = false
    ) {
        val existing = dao.getTopicProgressOnce(topicId)
        val merged = TopicProgressEntity(
            topicId = topicId,
            completedExercises = (existing?.completedExercises ?: 0) + if (completedExercise) 1 else 0,
            bestQuizScore = maxOf(existing?.bestQuizScore ?: 0, quizScore),
            totalQuizQuestions = maxOf(existing?.totalQuizQuestions ?: 0, totalQuiz),
            isCompleted = (existing?.isCompleted == true) || (totalQuiz > 0 && quizScore * 2 >= totalQuiz),
            homeworkDone = (existing?.homeworkDone == true) || homeworkDone,
            revisionCount = (existing?.revisionCount ?: 0) + if (revised) 1 else 0,
            lastStudiedMillis = System.currentTimeMillis()
        )
        dao.upsertTopicProgress(merged)
    }

    suspend fun logMistake(
        original: String,
        corrected: String,
        category: String,
        ruleHindi: String
    ) {
        val existing = dao.findMistakeByCategory(category)
        if (existing != null) {
            dao.insertMistake(
                existing.copy(
                    originalSentence = original,
                    correctedSentence = corrected,
                    occurrenceCount = existing.occurrenceCount + 1,
                    timestampMillis = System.currentTimeMillis()
                )
            )
        } else {
            dao.insertMistake(
                MistakeLogEntity(
                    originalSentence = original,
                    correctedSentence = corrected,
                    category = category,
                    ruleHindi = ruleHindi
                )
            )
        }
    }

    suspend fun markWordLearned(wordId: String, word: String, isGenZ: Boolean) {
        dao.upsertLearnedWord(
            LearnedWordEntity(
                wordId = wordId,
                word = word,
                isGenZ = isGenZ,
                learnedEpochDay = currentEpochDay(),
                isMastered = true
            )
        )
        val current = ensureAnonymousProfileExists()
        dao.upsertProfile(
            current.copy(
                stars = current.stars + 1,
                xp = current.xp + 10,
                vocabularyMastery = (current.vocabularyMastery + 1).coerceAtMost(100)
            )
        )
    }

    suspend fun toggleSaveNews(articleId: String, headlineEn: String, currentlySaved: Boolean) {
        if (currentlySaved) {
            dao.deleteSavedNews(articleId)
        } else {
            dao.upsertSavedNews(SavedNewsEntity(articleId = articleId, headlineEn = headlineEn))
        }
    }

    suspend fun toggleDailyTask(taskKey: String, completed: Boolean) {
        val today = currentEpochDay()
        dao.upsertDailyTask(
            DailyStudyTaskEntity(
                compositeKey = "$today:$taskKey",
                epochDay = today,
                taskKey = taskKey,
                isCompleted = completed
            )
        )
        if (completed) {
            val current = ensureAnonymousProfileExists()
            dao.upsertProfile(current.copy(stars = current.stars + 1, xp = current.xp + 10))
        }
    }

    suspend fun unlockBadgeIfAbsent(
        badgeId: String,
        titleEn: String,
        titleHi: String,
        descriptionEn: String
    ) {
        dao.insertBadge(
            AchievementBadgeEntity(
                badgeId = badgeId,
                titleEn = titleEn,
                titleHi = titleHi,
                descriptionEn = descriptionEn
            )
        )
    }

    suspend fun deleteAllLocalData() {
        dao.clearProfiles()
        dao.clearTopicProgress()
        dao.clearMistakes()
        dao.clearLearnedWords()
        dao.clearSavedNews()
        dao.clearDailyTasks()
        dao.clearBadges()
        context.settingsDataStore.edit { it.clear() }
        ensureAnonymousProfileExists()
    }

    fun currentEpochDay(): Long = System.currentTimeMillis() / 86_400_000L

    private fun generateAnonymousId(): String {
        val num = Random.nextInt(1000, 9999)
        return "TT-$num"
    }
}
