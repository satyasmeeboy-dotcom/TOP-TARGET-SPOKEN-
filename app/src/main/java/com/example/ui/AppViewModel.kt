package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AiRepository
import com.example.data.ai.SampleAiRepository
import com.example.data.ai.SpeakingEvaluation
import com.example.data.curriculum.DailyRoutineCatalog
import com.example.data.curriculum.GrammarCurriculum
import com.example.data.curriculum.WordsCatalog
import com.example.data.local.AchievementBadgeEntity
import com.example.data.local.AppDatabase
import com.example.data.local.DailyStudyTaskEntity
import com.example.data.local.LearnedWordEntity
import com.example.data.local.MistakeLogEntity
import com.example.data.local.SavedNewsEntity
import com.example.data.local.StudentProfileEntity
import com.example.data.local.StudentRepository
import com.example.data.local.TopicProgressEntity
import com.example.model.BilingualMode
import com.example.model.CertificateSpec
import com.example.model.GrammarMistakeFeedback
import com.example.model.GrammarTopic
import com.example.model.SkillType
import com.example.model.StudentLevel
import com.example.model.TeacherMessage
import com.example.model.TeacherStage
import com.example.model.WhiteboardState
import com.example.speech.ConnectivityObserver
import com.example.speech.SpeechHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CelebrationBanner(
    val title: String,
    val message: String,
    val starsEarned: Int
)

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).appDao()
    private val repository = StudentRepository(application, dao)
    private val aiRepository: AiRepository = SampleAiRepository()
    private val connectivityObserver = ConnectivityObserver(application)
    val speechHelper = SpeechHelper(application)

    val profile: StateFlow<StudentProfileEntity?> = repository.profileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val topicProgress: StateFlow<List<TopicProgressEntity>> = repository.topicProgressFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val mistakes: StateFlow<List<MistakeLogEntity>> = repository.mistakesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val learnedWords: StateFlow<List<LearnedWordEntity>> = repository.learnedWordsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedNews: StateFlow<List<SavedNewsEntity>> = repository.savedNewsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val badges: StateFlow<List<AchievementBadgeEntity>> = repository.badgesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayTasks: StateFlow<List<DailyStudyTaskEntity>> = repository.observeTodayStudyTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bilingualMode: StateFlow<BilingualMode> = repository.bilingualModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BilingualMode.BILINGUAL)

    val speechRate: StateFlow<Float> = repository.speechRateFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.92f)

    val autoSpeakTeacher: StateFlow<Boolean> = repository.autoSpeakTeacherFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val optionalSyncEnabled: StateFlow<Boolean> = repository.optionalSyncFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isOnline: StateFlow<Boolean> = connectivityObserver.observeIsOnline()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // Teacher Classroom State
    private val _activeTopic = MutableStateFlow(GrammarCurriculum.allTopics.first())
    val activeTopic: StateFlow<GrammarTopic> = _activeTopic.asStateFlow()

    private val _teacherStage = MutableStateFlow(TeacherStage.WELCOME)
    val teacherStage: StateFlow<TeacherStage> = _teacherStage.asStateFlow()

    private val _whiteboard = MutableStateFlow(
        WhiteboardState(
            topicTitle = GrammarCurriculum.allTopics.first().titleEn,
            stageLabel = TeacherStage.WELCOME.labelEn,
            ruleEnglish = GrammarCurriculum.allTopics.first().summaryEn,
            ruleHindi = GrammarCurriculum.allTopics.first().explanationHi,
            examples = GrammarCurriculum.allTopics.first().examples.map { it.english }
        )
    )
    val whiteboard: StateFlow<WhiteboardState> = _whiteboard.asStateFlow()

    private val _teacherMessages = MutableStateFlow<List<TeacherMessage>>(emptyList())
    val teacherMessages: StateFlow<List<TeacherMessage>> = _teacherMessages.asStateFlow()

    private val _isTeacherThinking = MutableStateFlow(false)
    val isTeacherThinking: StateFlow<Boolean> = _isTeacherThinking.asStateFlow()

    // Grammar & Writing & Speaking check states
    private val _grammarCheckResult = MutableStateFlow<GrammarMistakeFeedback?>(null)
    val grammarCheckResult: StateFlow<GrammarMistakeFeedback?> = _grammarCheckResult.asStateFlow()

    val weakTopicTags: StateFlow<List<String>> = combine(profile, mistakes) { prof, mistakeList ->
        val recommended = prof?.recommendedTopicIdsCsv
            ?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotBlank() }
            .orEmpty()
        val fromMistakes = mistakeList.mapNotNull { m ->
            when {
                m.category.contains("Article", ignoreCase = true) -> "articles"
                m.category.contains("Tense", ignoreCase = true) -> "tenses"
                m.category.contains("Preposition", ignoreCase = true) -> "preposition"
                m.category.contains("Verb", ignoreCase = true) || m.category.contains("Agreement", ignoreCase = true) -> "sva"
                m.category.contains("Word Order", ignoreCase = true) || m.category.contains("Sentence", ignoreCase = true) -> "sentence_structure"
                m.category.contains("Vocabulary", ignoreCase = true) -> "vocabulary"
                else -> null
            }
        }
        (recommended + fromMistakes).distinct()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _routineCheckResult = MutableStateFlow<GrammarMistakeFeedback?>(null)
    val routineCheckResult: StateFlow<GrammarMistakeFeedback?> = _routineCheckResult.asStateFlow()

    private val _dailyPracticeFeedback = MutableStateFlow<GrammarMistakeFeedback?>(null)
    val dailyPracticeFeedback: StateFlow<GrammarMistakeFeedback?> = _dailyPracticeFeedback.asStateFlow()

    private val _speakingEvaluation = MutableStateFlow<SpeakingEvaluation?>(null)
    val speakingEvaluation: StateFlow<SpeakingEvaluation?> = _speakingEvaluation.asStateFlow()

    private val _celebration = MutableStateFlow<CelebrationBanner?>(null)
    val celebration: StateFlow<CelebrationBanner?> = _celebration.asStateFlow()

    private val _dailyCompliment = MutableStateFlow(
        (aiRepository as? SampleAiRepository)?.bilingualComplimentPair(0)
            ?: ("Excellent! You are doing amazing!" to "शाबाश! आप बहुत अच्छा कर रहे हैं!")
    )
    val dailyCompliment: StateFlow<Pair<String, String>> = _dailyCompliment.asStateFlow()

    private var complimentSeed = 1

    val currentEpochDay: Long
        get() = repository.currentEpochDay()

    init {
        viewModelScope.launch {
            val prof = repository.ensureAnonymousProfileExists()
            val completedIds = repository.topicProgressFlow.firstOrNull()
                ?.filter { it.isCompleted }
                ?.map { it.topicId }
                ?.toSet()
                .orEmpty()
            val recommendedFirst = prof.recommendedTopicIdsCsv
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .mapNotNull { GrammarCurriculum.topicById(it) }
                .firstOrNull { it.id !in completedIds }
                ?: GrammarCurriculum.allTopics.firstOrNull { it.id !in completedIds }
                ?: GrammarCurriculum.allTopics.first()
            startTeacherLesson(recommendedFirst, speakGreeting = false)
        }
    }

    fun startTeacherLesson(topic: GrammarTopic, speakGreeting: Boolean = true) {
        _activeTopic.value = topic
        _teacherStage.value = TeacherStage.INTRODUCE
        val name = profile.value?.nickname?.takeIf { it.isNotBlank() } ?: "Student"
        val welcomeMsg = TeacherMessage(
            id = "t_${System.currentTimeMillis()}",
            fromTeacher = true,
            englishText = "Hello $name! I am CHENI, your personal AI English teacher and study friend. Today we will learn '${topic.titleEn}' together. Never worry about mistakes—I am right here to guide you step by step!",
            hindiText = "नमस्ते $name! मैं CHENI हूँ, आपकी AI अंग्रेज़ी शिक्षक और दोस्त। आज हम '${topic.titleHi}' सीखेंगे। गलतियों से बिल्कुल न घबराएं—मैं चरण-दर-चरण आपकी मदद करूँगी!",
            isSampleMode = aiRepository.isSampleImplementation
        )
        _teacherMessages.value = listOf(welcomeMsg)
        _whiteboard.value = WhiteboardState(
            topicTitle = topic.titleEn,
            stageLabel = TeacherStage.WELCOME.labelEn,
            ruleEnglish = topic.summaryEn,
            ruleHindi = topic.explanationHi,
            examples = topic.examples.map { "${it.english} — ${it.hindi}" }
        )
        if (speakGreeting && autoSpeakTeacher.value) {
            speechHelper.speakEnglish(welcomeMsg.englishText, speechRate.value)
        }
    }

    fun sendTeacherStudentMessage(input: String) {
        val clean = input.trim()
        if (clean.isEmpty()) return
        val studentMsg = TeacherMessage(
            id = "s_${System.currentTimeMillis()}",
            fromTeacher = false,
            englishText = clean
        )
        _teacherMessages.value = _teacherMessages.value + studentMsg

        viewModelScope.launch {
            _isTeacherThinking.value = true
            val prof = profile.value ?: repository.ensureAnonymousProfileExists()
            val level = runCatching { StudentLevel.valueOf(prof.currentLevel) }
                .getOrDefault(StudentLevel.BEGINNER)
            val weakCats = mistakes.value.map { it.category }.distinct().take(3)
            val reply = aiRepository.teachNextTurn(
                studentReply = clean,
                currentTopic = _activeTopic.value,
                currentStage = _teacherStage.value,
                studentLevel = level,
                weakCategories = weakCats,
                nickname = prof.nickname
            )
            _teacherStage.value = reply.nextStage
            _whiteboard.value = WhiteboardState(
                topicTitle = _activeTopic.value.titleEn,
                stageLabel = reply.nextStage.labelEn,
                ruleEnglish = reply.whiteboardRuleEn,
                ruleHindi = reply.whiteboardRuleHi,
                examples = reply.whiteboardExamples,
                highlightedCorrection = reply.feedback?.takeIf { !it.isCorrect }?.correctedSentence
            )
            val tMsg = TeacherMessage(
                id = "t_${System.currentTimeMillis() + 1}",
                fromTeacher = true,
                englishText = reply.englishText,
                hindiText = reply.hindiText,
                feedback = reply.feedback,
                isSampleMode = reply.isSampleMode
            )
            _teacherMessages.value = _teacherMessages.value + tMsg

            var leveledUp: StudentLevel? = null
            reply.feedback?.let { fb ->
                if (!fb.isCorrect) {
                    repository.logMistake(
                        original = fb.originalSentence,
                        corrected = fb.correctedSentence,
                        category = fb.mistakeCategory,
                        ruleHindi = fb.grammarRuleHi
                    )
                }
                leveledUp = repository.recordPracticeAttempt(
                    skill = SkillType.SPEAKING,
                    correctCount = if (fb.isCorrect) 1 else 0,
                    totalCount = 1,
                    earnedStars = reply.awardStars,
                    earnedXp = 15
                )
            }

            if (reply.nextStage == TeacherStage.WELCOME) {
                repository.toggleDailyTask("speaking", true)
                val isAccurate = reply.feedback?.isCorrect != false
                val complimentMsg = aiRepository.performanceCompliment(
                    scoreRatio = if (isAccurate) 1.0f else 0.65f,
                    isCorrect = isAccurate,
                    context = _activeTopic.value.titleEn
                )
                val levelMsg = leveledUp?.let { " Level Up! Welcome to ${it.labelEn}." } ?: ""
                showCelebration(
                    title = if (leveledUp != null) "Level Up! Welcome to ${leveledUp!!.labelEn}" else "Lesson Session Complete!",
                    message = "$complimentMsg$levelMsg",
                    stars = 3
                )
            } else if (leveledUp != null) {
                showCelebration(
                    title = "Level Up! Welcome to ${leveledUp!!.labelEn}",
                    message = "CHENI noticed your strong accuracy and upgraded your level to ${leveledUp!!.labelEn}!",
                    stars = 5
                )
            }
            if (autoSpeakTeacher.value) {
                speechHelper.speakEnglish(reply.englishText, speechRate.value)
            }
            _isTeacherThinking.value = false
        }
    }

    fun advanceTeacherStagePrompt() {
        val defaultInput = when (_teacherStage.value) {
            TeacherStage.WELCOME -> "Yes Teacher, I am ready!"
            TeacherStage.INTRODUCE -> "Please explain the rules with Hindi help."
            TeacherStage.EXPLAIN -> "Show me daily-life examples please."
            TeacherStage.EXAMPLES -> "Give me a question to try!"
            TeacherStage.ASK_QUESTION -> _activeTopic.value.exercises.firstOrNull()?.sampleAnswer ?: "I practice English every day."
            TeacherStage.LISTEN_CORRECT -> _activeTopic.value.exercises.firstOrNull()?.sampleAnswer ?: "I practice English every day."
            TeacherStage.HOMEWORK -> "Thank you! I will do my homework."
        }
        sendTeacherStudentMessage(defaultInput)
    }

    fun checkGrammarSentence(sentence: String, skill: SkillType = SkillType.GRAMMAR) {
        viewModelScope.launch {
            val result = aiRepository.analyzeGrammar(sentence)
            _grammarCheckResult.value = result
            if (!result.isCorrect && sentence.isNotBlank()) {
                repository.logMistake(
                    original = result.originalSentence,
                    corrected = result.correctedSentence,
                    category = result.mistakeCategory,
                    ruleHindi = result.grammarRuleHi
                )
            }
            if (sentence.isNotBlank()) {
                val leveledUp = repository.recordPracticeAttempt(
                    skill = skill,
                    correctCount = if (result.isCorrect) 1 else 0,
                    totalCount = 1,
                    earnedStars = if (result.isCorrect) 2 else 1,
                    earnedXp = if (result.isCorrect) 20 else 10
                )
                if (skill == SkillType.WRITING) {
                    repository.toggleDailyTask("writing", true)
                }
                if (leveledUp != null) {
                    showCelebration(
                        title = "Level Up! Welcome to ${leveledUp.labelEn}",
                        message = "Great improvement! CHENI updated your English level to ${leveledUp.labelEn}.",
                        stars = 5
                    )
                }
            }
        }
    }

    fun clearGrammarCheckResult() {
        _grammarCheckResult.value = null
    }

    fun checkDailyPracticeSentence(sentence: String, skill: SkillType) {
        viewModelScope.launch {
            val result = aiRepository.analyzeGrammar(sentence)
            _dailyPracticeFeedback.value = result
            if (!result.isCorrect && sentence.isNotBlank()) {
                repository.logMistake(
                    original = result.originalSentence,
                    corrected = result.correctedSentence,
                    category = result.mistakeCategory,
                    ruleHindi = result.grammarRuleHi
                )
            }
            if (sentence.isNotBlank()) {
                val leveledUp = repository.recordPracticeAttempt(
                    skill = skill,
                    correctCount = if (result.isCorrect) 1 else 0,
                    totalCount = 1,
                    earnedStars = if (result.isCorrect) 2 else 1,
                    earnedXp = if (result.isCorrect) 20 else 10
                )
                if (leveledUp != null) {
                    showCelebration(
                        title = "Level Up! Welcome to ${leveledUp.labelEn}",
                        message = "Your daily 10-question practice unlocked ${leveledUp.labelEn}!",
                        stars = 5
                    )
                }
            }
        }
    }

    fun clearDailyPracticeFeedback() {
        _dailyPracticeFeedback.value = null
    }

    fun checkRoutineSentence(sentence: String) {
        viewModelScope.launch {
            val result = aiRepository.analyzeGrammar(sentence)
            _routineCheckResult.value = result
            if (!result.isCorrect && sentence.isNotBlank()) {
                repository.logMistake(
                    original = result.originalSentence,
                    corrected = result.correctedSentence,
                    category = result.mistakeCategory,
                    ruleHindi = result.grammarRuleHi
                )
            }
            if (sentence.isNotBlank()) {
                val leveledUp = repository.recordPracticeAttempt(
                    skill = SkillType.SPEAKING,
                    correctCount = if (result.isCorrect) 1 else 0,
                    totalCount = 1,
                    earnedStars = if (result.isCorrect) 2 else 1,
                    earnedXp = if (result.isCorrect) 20 else 10
                )
                if (leveledUp != null) {
                    showCelebration(
                        title = "Level Up! Welcome to ${leveledUp.labelEn}",
                        message = "Daily Routine practice upgraded your level to ${leveledUp.labelEn}!",
                        stars = 5
                    )
                }
            }
        }
    }

    fun clearRoutineFeedback() {
        _routineCheckResult.value = null
    }

    fun evaluateSpeakingResponse(scenarioTitle: String, spokenText: String) {
        viewModelScope.launch {
            val eval = aiRepository.evaluateSpeaking(scenarioTitle, spokenText)
            _speakingEvaluation.value = eval
            if (!eval.sentenceFormationFeedback.isCorrect && spokenText.isNotBlank()) {
                repository.logMistake(
                    original = eval.sentenceFormationFeedback.originalSentence,
                    corrected = eval.sentenceFormationFeedback.correctedSentence,
                    category = eval.sentenceFormationFeedback.mistakeCategory,
                    ruleHindi = eval.sentenceFormationFeedback.grammarRuleHi
                )
            }
            val leveledUp = repository.recordPracticeAttempt(
                skill = SkillType.SPEAKING,
                correctCount = if (eval.sentenceFormationFeedback.isCorrect) 1 else 0,
                totalCount = 1,
                earnedStars = 3,
                earnedXp = 25
            )
            repository.toggleDailyTask("speaking", true)
            if (leveledUp != null) {
                showCelebration(
                    title = "Level Up! Welcome to ${leveledUp.labelEn}",
                    message = "Your speaking confidence earned you ${leveledUp.labelEn} level!",
                    stars = 5
                )
            }
        }
    }

    fun submitAssessment(
        answers: Map<String, Int>,
        writingSample: String = "",
        speakingSample: String = ""
    ): Pair<StudentLevel, List<String>> {
        val questions = GrammarCurriculum.assessmentQuestions
        var correctCount = 0
        val weakTopicIds = mutableListOf<String>()
        val skillTotals = mutableMapOf<SkillType, Int>()
        val skillCorrect = mutableMapOf<SkillType, Int>()

        for (q in questions) {
            skillTotals[q.skill] = (skillTotals[q.skill] ?: 0) + 1
            val picked = answers[q.id]
            if (picked == q.correctIndex) {
                correctCount++
                skillCorrect[q.skill] = (skillCorrect[q.skill] ?: 0) + 1
            } else {
                weakTopicIds.add(q.weakTopicId)
            }
        }
        val ratio = correctCount.toFloat() / questions.size.coerceAtLeast(1)
        val determinedLevel = when {
            ratio >= 0.85f -> StudentLevel.ADVANCED
            ratio >= 0.65f -> StudentLevel.INTERMEDIATE
            ratio >= 0.40f -> StudentLevel.ELEMENTARY
            else -> StudentLevel.BEGINNER
        }

        viewModelScope.launch {
            val updatedWeak = weakTopicIds.toMutableList()
            val updatedSkillTotals = skillTotals.toMutableMap()
            val updatedSkillCorrect = skillCorrect.toMutableMap()
            var totalAttempted = questions.size
            var totalCorrect = correctCount

            if (writingSample.isNotBlank()) {
                val writeFb = aiRepository.analyzeGrammar(writingSample)
                updatedSkillTotals[SkillType.WRITING] = (updatedSkillTotals[SkillType.WRITING] ?: 0) + 1
                totalAttempted++
                if (writeFb.isCorrect) {
                    updatedSkillCorrect[SkillType.WRITING] = (updatedSkillCorrect[SkillType.WRITING] ?: 0) + 1
                    totalCorrect++
                } else {
                    updatedWeak.add("sentence_structure")
                    repository.logMistake(
                        original = writeFb.originalSentence,
                        corrected = writeFb.correctedSentence,
                        category = writeFb.mistakeCategory,
                        ruleHindi = writeFb.grammarRuleHi
                    )
                }
            }
            if (speakingSample.isNotBlank()) {
                val speakFb = aiRepository.analyzeGrammar(speakingSample)
                updatedSkillTotals[SkillType.SPEAKING] = (updatedSkillTotals[SkillType.SPEAKING] ?: 0) + 1
                totalAttempted++
                if (speakFb.isCorrect) {
                    updatedSkillCorrect[SkillType.SPEAKING] = (updatedSkillCorrect[SkillType.SPEAKING] ?: 0) + 1
                    totalCorrect++
                } else {
                    updatedWeak.add("tenses")
                    repository.logMistake(
                        original = speakFb.originalSentence,
                        corrected = speakFb.correctedSentence,
                        category = speakFb.mistakeCategory,
                        ruleHindi = speakFb.grammarRuleHi
                    )
                }
            }

            val finalRatio = totalCorrect.toFloat() / totalAttempted.coerceAtLeast(1)
            val finalLevel = when {
                finalRatio >= 0.85f -> StudentLevel.ADVANCED
                finalRatio >= 0.65f -> StudentLevel.INTERMEDIATE
                finalRatio >= 0.40f -> StudentLevel.ELEMENTARY
                else -> StudentLevel.BEGINNER
            }
            val skillScores = SkillType.entries.associateWith { skill ->
                val t = updatedSkillTotals[skill] ?: 0
                val c = updatedSkillCorrect[skill] ?: 0
                if (t == 0) (finalRatio * 100).toInt().coerceIn(20, 85)
                else ((c.toFloat() / t) * 100).toInt().coerceIn(20, 100)
            }

            val completedIds = repository.topicProgressFlow.firstOrNull()
                ?.filter { it.isCompleted }
                ?.map { it.topicId }
                ?.toSet()
                .orEmpty()
            val distinctWeak = updatedWeak.distinct()
            repository.recordAssessmentResult(
                level = finalLevel,
                weakTopicIds = distinctWeak,
                skillScores = skillScores,
                attempted = totalAttempted,
                correct = totalCorrect
            )
            val firstRecommended = distinctWeak
                .mapNotNull { GrammarCurriculum.topicById(it) }
                .firstOrNull { it.id !in completedIds }
                ?: GrammarCurriculum.allTopics.firstOrNull { it.id !in completedIds }
            if (firstRecommended != null) {
                startTeacherLesson(firstRecommended, speakGreeting = false)
            }
            showCelebration(
                title = "Level Assessed: ${finalLevel.labelEn}!",
                message = "Your personal study plan is ready. ${aiRepository.performanceCompliment(finalRatio, finalRatio >= 0.5f, "assessment")}",
                stars = 10
            )
        }
        return determinedLevel to weakTopicIds.distinct()
    }

    fun recordTopicQuizResult(
        topic: GrammarTopic,
        score: Int,
        total: Int,
        homeworkDone: Boolean = false,
        revised: Boolean = false
    ) {
        viewModelScope.launch {
            repository.updateTopicProgress(
                topicId = topic.id,
                quizScore = score,
                totalQuiz = total,
                completedExercise = true,
                homeworkDone = homeworkDone,
                revised = revised
            )
            val leveledUp = repository.recordPracticeAttempt(
                skill = SkillType.GRAMMAR,
                correctCount = score,
                totalCount = total,
                earnedStars = score + 2,
                earnedXp = score * 15 + 15
            )
            repository.toggleDailyTask("grammar", true)
            if (revised) {
                repository.toggleDailyTask("revision", true)
            }
            val ratio = score.toFloat() / total.coerceAtLeast(1)
            val compliment = aiRepository.performanceCompliment(
                scoreRatio = ratio,
                isCorrect = score * 2 >= total,
                context = topic.titleEn
            )
            val levelNote = leveledUp?.let { " Level Up! Welcome to ${it.labelEn}." } ?: ""
            showCelebration(
                title = if (leveledUp != null) "Level Up! Welcome to ${leveledUp.labelEn}" else "${topic.titleEn} Quiz Checked!",
                message = "Score: $score / $total. $compliment$levelNote",
                stars = score + 2
            )
        }
    }

    fun recordSkillQuiz(skill: SkillType, correct: Int, total: Int, dailyTaskKey: String) {
        viewModelScope.launch {
            val leveledUp = repository.recordPracticeAttempt(
                skill = skill,
                correctCount = correct,
                totalCount = total,
                earnedStars = correct + 2,
                earnedXp = correct * 12 + 15
            )
            repository.toggleDailyTask(dailyTaskKey, true)
            val ratio = correct.toFloat() / total.coerceAtLeast(1)
            val compliment = aiRepository.performanceCompliment(
                scoreRatio = ratio,
                isCorrect = correct * 2 >= total,
                context = skill.titleEn
            )
            val levelNote = leveledUp?.let { " Level Up! Welcome to ${it.labelEn}." } ?: ""
            showCelebration(
                title = if (leveledUp != null) "Level Up! Welcome to ${leveledUp.labelEn}" else "${skill.titleEn} Practice Checked!",
                message = "Score: $correct / $total. $compliment$levelNote",
                stars = correct + 2
            )
        }
    }

    fun markWordLearned(wordId: String, word: String, isGenZ: Boolean) {
        viewModelScope.launch {
            repository.markWordLearned(wordId, word, isGenZ)
            val learnedIds = learnedWords.value.map { it.wordId }.toSet() + wordId
            val todayGenZ = WordsCatalog.todaysTenGenZWords(currentEpochDay).count { it.id in learnedIds }
            val todayVocab = WordsCatalog.todaysTenVocabWords(currentEpochDay).count { it.id in learnedIds }
            if (todayGenZ >= 10) repository.toggleDailyTask("genz_10", true)
            if (todayVocab >= 10) repository.toggleDailyTask("vocab_10", true)
        }
    }

    fun markPhraseOrIdiomLearned(phraseId: String, phrase: String) {
        viewModelScope.launch {
            repository.markWordLearned(phraseId, phrase, isGenZ = false)
            val learnedIds = learnedWords.value.map { it.wordId }.toSet() + phraseId
            val todayPhrases = WordsCatalog.todaysPhrasesAndIdioms(currentEpochDay).count { it.id in learnedIds }
            if (todayPhrases >= 5) {
                repository.toggleDailyTask("phrases_idioms", true)
            }
            if (todayPhrases == 10) {
                val msg = when (bilingualMode.value) {
                    BilingualMode.HINDI_ASSIST -> "CHENI कहती हैं: ${(aiRepository as? SampleAiRepository)?.bilingualComplimentPair(0)?.second ?: aiRepository.motivationalCompliment("phrases")}"
                    else -> "CHENI says: ${aiRepository.motivationalCompliment("phrases")}"
                }
                showCelebration(
                    title = "Phrases & Idioms Mastered!",
                    message = msg,
                    stars = 3
                )
            }
        }
    }

    fun markRoutineStepPracticed(routineId: String) {
        viewModelScope.launch {
            val key = "routine_$routineId"
            val alreadyDone = todayTasks.value.any { it.taskKey == key && it.isCompleted }
            var leveledUp: StudentLevel? = null
            if (!alreadyDone) {
                repository.toggleDailyTask(key, true)
                leveledUp = repository.recordPracticeAttempt(
                    skill = SkillType.SPEAKING,
                    correctCount = 1,
                    totalCount = 1,
                    earnedStars = 0,
                    earnedXp = 10
                )
            }
            val practicedRoutineIds = todayTasks.value
                .filter { it.isCompleted && it.taskKey.startsWith("routine_") }
                .map { it.taskKey.removePrefix("routine_") }
                .toSet() + routineId
            val totalRoutineItems = DailyRoutineCatalog.items.size
            if (practicedRoutineIds.size >= 3 && todayTasks.value.none { it.taskKey == "daily_routine" && it.isCompleted }) {
                repository.toggleDailyTask("daily_routine", true)
            }
            if (practicedRoutineIds.size == totalRoutineItems) {
                showCelebration(
                    title = "Full Daily Routine Mastered!",
                    message = "CHENI says: ${aiRepository.performanceCompliment(1.0f, true, "daily routine")}",
                    stars = 5
                )
            } else if (leveledUp != null) {
                showCelebration(
                    title = "Level Up! Welcome to ${leveledUp.labelEn}",
                    message = "Consistent routine practice promoted you to ${leveledUp.labelEn}!",
                    stars = 5
                )
            }
        }
    }

    fun refreshCompliment(speakAloud: Boolean = false) {
        complimentSeed++
        val pair = (aiRepository as? SampleAiRepository)?.bilingualComplimentPair(complimentSeed)
            ?: (aiRepository.motivationalCompliment("home") to "शाबाश! रोज़ थोड़ा अभ्यास करते रहें!")
        _dailyCompliment.value = pair
        if (speakAloud) {
            speechHelper.speakEnglish(pair.first, speechRate.value)
        }
    }

    fun toggleSaveNews(articleId: String, headlineEn: String, currentlySaved: Boolean) {
        viewModelScope.launch {
            repository.toggleSaveNews(articleId, headlineEn, currentlySaved)
        }
    }

    fun toggleDailyTask(taskKey: String, completed: Boolean) {
        viewModelScope.launch {
            repository.toggleDailyTask(taskKey, completed)
        }
    }

    fun updateNickname(name: String) {
        viewModelScope.launch { repository.updateNickname(name) }
    }

    fun setBilingualMode(mode: BilingualMode) {
        viewModelScope.launch { repository.setBilingualMode(mode) }
    }

    fun setSpeechRate(rate: Float) {
        viewModelScope.launch { repository.setSpeechRate(rate) }
    }

    fun setAutoSpeakTeacher(enabled: Boolean) {
        viewModelScope.launch { repository.setAutoSpeakTeacher(enabled) }
    }

    fun setOptionalSync(enabled: Boolean) {
        viewModelScope.launch { repository.setOptionalSync(enabled) }
    }

    fun deleteAllLocalData() {
        viewModelScope.launch {
            repository.deleteAllLocalData()
            _grammarCheckResult.value = null
            _routineCheckResult.value = null
            _dailyPracticeFeedback.value = null
            _speakingEvaluation.value = null
            startTeacherLesson(GrammarCurriculum.allTopics.first(), speakGreeting = false)
            showCelebration(
                title = "Local Data Cleared",
                message = "All local learning history was safely deleted and a fresh anonymous ID was created.",
                stars = 0
            )
        }
    }

    fun showCelebration(title: String, message: String, stars: Int) {
        _celebration.value = CelebrationBanner(title, message, stars)
    }

    fun dismissCelebration() {
        _celebration.value = null
    }

    fun buildCertificates(
        prof: StudentProfileEntity?,
        progressList: List<TopicProgressEntity>,
        words: List<LearnedWordEntity>
    ): List<CertificateSpec> {
        val completedTopics = progressList.count { it.isCompleted }
        val totalTopics = GrammarCurriculum.allTopics.size.coerceAtLeast(1)
        val grammarMastery = prof?.grammarMastery ?: 10
        val vocabCount = words.size
        val stars = prof?.stars ?: 0

        return listOf(
            CertificateSpec(
                id = "cert_beginner",
                titleEn = "Beginner Spoken English Certificate",
                titleHi = "प्रारंभिक अंग्रेज़ी प्रमाणपत्र",
                requirementDescription = "Complete Knowledge Assessment or earn 15+ Stars",
                isUnlocked = (prof?.assessmentCompleted == true) || stars >= 15,
                progressRatio = ((stars.toFloat() / 15f).coerceIn(0f, 1f))
            ),
            CertificateSpec(
                id = "cert_intermediate",
                titleEn = "Intermediate Fluency Certificate",
                titleHi = "मध्यम धाराप्रवाह प्रमाणपत्र",
                requirementDescription = "Complete 5+ Grammar Topics & reach 50% Speaking/Grammar mastery",
                isUnlocked = completedTopics >= 5 && grammarMastery >= 50,
                progressRatio = ((completedTopics.toFloat() / 5f).coerceIn(0f, 1f))
            ),
            CertificateSpec(
                id = "cert_advanced",
                titleEn = "Advanced Communicator Certificate",
                titleHi = "उन्नत संवाद प्रमाणपत्र",
                requirementDescription = "Complete 12+ Grammar Topics & earn 80+ Stars",
                isUnlocked = completedTopics >= 12 && stars >= 80,
                progressRatio = ((completedTopics.toFloat() / 12f).coerceIn(0f, 1f))
            ),
            CertificateSpec(
                id = "cert_grammar",
                titleEn = "Complete English Grammar Master",
                titleHi = "संपूर्ण व्याकरण पाठ्यक्रम प्रमाणपत्र",
                requirementDescription = "Complete all $totalTopics Grammar Topics",
                isUnlocked = completedTopics >= totalTopics,
                progressRatio = (completedTopics.toFloat() / totalTopics.toFloat()).coerceIn(0f, 1f)
            ),
            CertificateSpec(
                id = "cert_vocab",
                titleEn = "Vocabulary & Gen Z Word Champion",
                titleHi = "शब्दावली चैंपियन प्रमाणपत्र",
                requirementDescription = "Learn 20+ Daily Vocabulary & Gen Z Words",
                isUnlocked = vocabCount >= 20,
                progressRatio = (vocabCount.toFloat() / 20f).coerceIn(0f, 1f)
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        speechHelper.release()
    }
}
