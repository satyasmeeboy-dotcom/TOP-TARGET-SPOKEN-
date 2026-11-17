package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_profile")
data class StudentProfileEntity(
    @PrimaryKey val id: Int = 1,
    val anonymousStudentId: String,
    val nickname: String = "",
    val currentLevel: String = "BEGINNER",
    val stars: Int = 5,
    val xp: Int = 0,
    val streakDays: Int = 1,
    val lastActiveEpochDay: Long = 0L,
    val assessmentCompleted: Boolean = false,
    val recommendedTopicIdsCsv: String = "noun,tenses,articles",
    val totalAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val grammarMastery: Int = 10,
    val vocabularyMastery: Int = 10,
    val speakingMastery: Int = 10,
    val listeningMastery: Int = 10,
    val readingMastery: Int = 10,
    val writingMastery: Int = 10
)

@Entity(tableName = "topic_progress")
data class TopicProgressEntity(
    @PrimaryKey val topicId: String,
    val completedExercises: Int = 0,
    val bestQuizScore: Int = 0,
    val totalQuizQuestions: Int = 0,
    val isCompleted: Boolean = false,
    val homeworkDone: Boolean = false,
    val revisionCount: Int = 0,
    val lastStudiedMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "mistake_logs")
data class MistakeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val originalSentence: String,
    val correctedSentence: String,
    val category: String,
    val ruleHindi: String,
    val occurrenceCount: Int = 1,
    val timestampMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "learned_words")
data class LearnedWordEntity(
    @PrimaryKey val wordId: String,
    val word: String,
    val isGenZ: Boolean,
    val learnedEpochDay: Long,
    val isMastered: Boolean = true
)

@Entity(tableName = "saved_news")
data class SavedNewsEntity(
    @PrimaryKey val articleId: String,
    val headlineEn: String,
    val savedAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_study_tasks")
data class DailyStudyTaskEntity(
    @PrimaryKey val compositeKey: String, // "$epochDay:$taskKey"
    val epochDay: Long,
    val taskKey: String,
    val isCompleted: Boolean = true
)

@Entity(tableName = "achievement_badges")
data class AchievementBadgeEntity(
    @PrimaryKey val badgeId: String,
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val unlockedAtMillis: Long = System.currentTimeMillis()
)
