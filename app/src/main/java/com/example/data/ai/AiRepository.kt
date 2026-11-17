package com.example.data.ai

import com.example.model.GrammarMistakeFeedback
import com.example.model.GrammarTopic
import com.example.model.StudentLevel
import com.example.model.TeacherStage

data class TeacherReply(
    val englishText: String,
    val hindiText: String,
    val nextStage: TeacherStage,
    val whiteboardRuleEn: String,
    val whiteboardRuleHi: String,
    val whiteboardExamples: List<String>,
    val feedback: GrammarMistakeFeedback? = null,
    val awardStars: Int = 1,
    val isSampleMode: Boolean = true
)

data class SpeakingEvaluation(
    val grammarNote: String,
    val pronunciationTip: String,
    val fluencyScore: Int,
    val sentenceFormationFeedback: GrammarMistakeFeedback,
    val encouragement: String,
    val isSampleMode: Boolean = true
)

interface AiRepository {
    val isSampleImplementation: Boolean

    suspend fun analyzeGrammar(sentence: String): GrammarMistakeFeedback

    suspend fun evaluateWriting(promptTitle: String, text: String): GrammarMistakeFeedback

    suspend fun evaluateSpeaking(
        scenarioTitle: String,
        spokenText: String
    ): SpeakingEvaluation

    suspend fun teachNextTurn(
        studentReply: String,
        currentTopic: GrammarTopic,
        currentStage: TeacherStage,
        studentLevel: StudentLevel,
        weakCategories: List<String>,
        nickname: String
    ): TeacherReply

    fun motivationalCompliment(context: String = "practice"): String

    fun performanceCompliment(
        scoreRatio: Float,
        isCorrect: Boolean = true,
        context: String = "practice"
    ): String
}
