package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM student_profile WHERE id = 1")
    fun observeProfile(): Flow<StudentProfileEntity?>

    @Query("SELECT * FROM student_profile WHERE id = 1")
    suspend fun getProfileOnce(): StudentProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: StudentProfileEntity)

    @Query("SELECT * FROM topic_progress")
    fun observeTopicProgress(): Flow<List<TopicProgressEntity>>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId")
    suspend fun getTopicProgressOnce(topicId: String): TopicProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopicProgress(progress: TopicProgressEntity)

    @Query("SELECT * FROM mistake_logs ORDER BY timestampMillis DESC LIMIT 50")
    fun observeMistakes(): Flow<List<MistakeLogEntity>>

    @Query("SELECT * FROM mistake_logs WHERE category = :category LIMIT 1")
    suspend fun findMistakeByCategory(category: String): MistakeLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(mistake: MistakeLogEntity)

    @Query("SELECT * FROM learned_words ORDER BY learnedEpochDay DESC")
    fun observeLearnedWords(): Flow<List<LearnedWordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLearnedWord(word: LearnedWordEntity)

    @Query("SELECT * FROM saved_news ORDER BY savedAtMillis DESC")
    fun observeSavedNews(): Flow<List<SavedNewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSavedNews(news: SavedNewsEntity)

    @Query("DELETE FROM saved_news WHERE articleId = :articleId")
    suspend fun deleteSavedNews(articleId: String)

    @Query("SELECT * FROM daily_study_tasks WHERE epochDay = :epochDay")
    fun observeDailyTasks(epochDay: Long): Flow<List<DailyStudyTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyTask(task: DailyStudyTaskEntity)

    @Query("SELECT * FROM achievement_badges ORDER BY unlockedAtMillis DESC")
    fun observeBadges(): Flow<List<AchievementBadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBadge(badge: AchievementBadgeEntity)

    // Privacy: Complete local wipe
    @Query("DELETE FROM student_profile")
    suspend fun clearProfiles()

    @Query("DELETE FROM topic_progress")
    suspend fun clearTopicProgress()

    @Query("DELETE FROM mistake_logs")
    suspend fun clearMistakes()

    @Query("DELETE FROM learned_words")
    suspend fun clearLearnedWords()

    @Query("DELETE FROM saved_news")
    suspend fun clearSavedNews()

    @Query("DELETE FROM daily_study_tasks")
    suspend fun clearDailyTasks()

    @Query("DELETE FROM achievement_badges")
    suspend fun clearBadges()
}
