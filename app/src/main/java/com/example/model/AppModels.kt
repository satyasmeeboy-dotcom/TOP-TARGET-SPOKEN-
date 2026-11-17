package com.example.model

enum class StudentLevel(
    val labelEn: String,
    val labelHi: String,
    val descriptionEn: String,
    val descriptionHi: String
) {
    BEGINNER(
        labelEn = "Beginner",
        labelHi = "शुरुआती (Beginner)",
        descriptionEn = "Building core sentence structure, basic nouns, verbs, and daily greetings.",
        descriptionHi = "बुनियादी वाक्य, संज्ञा, क्रिया और रोज़मर्रा के अभिवादन सीखना।"
    ),
    ELEMENTARY(
        labelEn = "Elementary",
        labelHi = "प्रारंभिक (Elementary)",
        descriptionEn = "Mastering everyday tenses, articles, prepositions, and simple conversations.",
        descriptionHi = "दैनिक काल (Tenses), articles, prepositions और सरल बातचीत में महारत।"
    ),
    INTERMEDIATE(
        labelEn = "Intermediate",
        labelHi = "मध्यम (Intermediate)",
        descriptionEn = "Fluent conversation, modals, voice, narration, and confident writing.",
        descriptionHi = "धाराप्रवाह बातचीत, modals, active-passive और आत्मविश्वास से लेखन।"
    ),
    ADVANCED(
        labelEn = "Advanced",
        labelHi = "उन्नत (Advanced)",
        descriptionEn = "Complex conditionals, nuanced vocabulary, public speaking, and news comprehension.",
        descriptionHi = "जटिल वाक्य, उन्नत शब्दावली, पब्लिक स्पीकिंग और समाचार समझ।"
    )
}

enum class BilingualMode(val label: String) {
    BILINGUAL("EN + हिं"),
    ENGLISH_ONLY("EN"),
    HINDI_ASSIST("हिं + EN")
}

enum class SkillType(val titleEn: String, val titleHi: String) {
    GRAMMAR("Grammar", "व्याकरण"),
    VOCABULARY("Vocabulary", "शब्दावली"),
    SPEAKING("Speaking", "बोलना"),
    LISTENING("Listening", "सुनना"),
    READING("Reading", "पढ़ना"),
    WRITING("Writing", "लिखना")
}

data class GrammarExample(
    val english: String,
    val hindi: String,
    val contextTag: String
)

data class GrammarMcq(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationEn: String,
    val explanationHi: String
)

data class GrammarExercise(
    val prompt: String,
    val hintHi: String,
    val expectedKeywords: List<String>,
    val sampleAnswer: String
)

data class GrammarTopic(
    val id: String,
    val order: Int,
    val titleEn: String,
    val titleHi: String,
    val level: StudentLevel,
    val summaryEn: String,
    val explanationHi: String,
    val keyRules: List<String>,
    val examples: List<GrammarExample>,
    val dailyLifeExamples: List<GrammarExample>,
    val exercises: List<GrammarExercise>,
    val mcqQuestions: List<GrammarMcq>,
    val homeworkEn: String,
    val homeworkHi: String,
    val revisionPoints: List<String>
)

data class GrammarMistakeFeedback(
    val isCorrect: Boolean,
    val originalSentence: String,
    val correctedSentence: String,
    val highlightedMistake: String,
    val mistakeCategory: String,
    val whyIncorrectEn: String,
    val grammarRuleHi: String,
    val extraExamples: List<String>,
    val tryAgainPrompt: String,
    val compliment: String,
    val isSampleMode: Boolean = true
)

data class DailyWord(
    val id: String,
    val word: String,
    val isGenZ: Boolean,
    val pronunciation: String,
    val hindiMeaning: String,
    val exampleSentence: String,
    val hindiTranslation: String,
    val realLifeUsage: String,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val level: StudentLevel = StudentLevel.BEGINNER
)

data class DailyPhraseOrIdiom(
    val id: String,
    val phrase: String,
    val isIdiom: Boolean,
    val hindiMeaning: String,
    val usageTipEn: String,
    val exampleSentence: String,
    val hindiTranslation: String,
    val level: StudentLevel = StudentLevel.BEGINNER
)

data class SpeakingScenario(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val teacherPromptEn: String,
    val teacherPromptHi: String,
    val sampleLines: List<String>,
    val keyPhrases: List<String>,
    val level: StudentLevel
)

data class ListeningLesson(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val category: String,
    val level: StudentLevel,
    val audioScriptEn: String,
    val hindiSummary: String,
    val comprehensionQuiz: List<GrammarMcq>
)

data class DifficultWord(
    val word: String,
    val meaningEn: String,
    val meaningHi: String
)

data class ReadingPassage(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val category: String,
    val level: StudentLevel,
    val contentEn: String,
    val contentHi: String,
    val difficultWords: List<DifficultWord>,
    val quiz: List<GrammarMcq>
)

data class WritingPrompt(
    val id: String,
    val category: String,
    val titleEn: String,
    val titleHi: String,
    val instructionEn: String,
    val instructionHi: String,
    val starterTemplate: String
)

data class NewsArticle(
    val id: String,
    val dateLabel: String,
    val headlineEn: String,
    val headlineHi: String,
    val bodyEn: String,
    val bodyHi: String,
    val difficultWords: List<DifficultWord>,
    val grammarNoteEn: String,
    val grammarNoteHi: String,
    val quiz: List<GrammarMcq>
)

enum class TeacherStage(val stepNumber: Int, val labelEn: String, val labelHi: String) {
    WELCOME(1, "Welcome", "स्वागत"),
    INTRODUCE(2, "Introduce Lesson", "पाठ परिचय"),
    EXPLAIN(3, "Explain Topic", "विषय व्याख्या"),
    EXAMPLES(4, "Daily Examples", "दैनिक उदाहरण"),
    ASK_QUESTION(5, "Live Q&A", "प्रश्नोत्तर"),
    LISTEN_CORRECT(6, "Listen & Correct", "सुनें और सुधारें"),
    HOMEWORK(7, "Homework & Praise", "गृहकार्य व प्रशंसा")
}

data class TeacherMessage(
    val id: String,
    val fromTeacher: Boolean,
    val englishText: String,
    val hindiText: String = "",
    val feedback: GrammarMistakeFeedback? = null,
    val isSampleMode: Boolean = true
)

data class WhiteboardState(
    val topicTitle: String,
    val stageLabel: String,
    val ruleEnglish: String,
    val ruleHindi: String,
    val examples: List<String>,
    val highlightedCorrection: String? = null
)

data class AssessmentQuestion(
    val id: String,
    val skill: SkillType,
    val promptEn: String,
    val promptHi: String,
    val options: List<String>,
    val correctIndex: Int,
    val weakTopicId: String
)

data class CertificateSpec(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val requirementDescription: String,
    val isUnlocked: Boolean,
    val progressRatio: Float
)

data class DailyStudyTaskSpec(
    val key: String,
    val titleEn: String,
    val titleHi: String,
    val routeHint: String
)

enum class RoutinePeriod(
    val labelEn: String,
    val labelHi: String,
    val timeRange: String
) {
    MORNING("Morning", "सुबह", "5:30 AM – 11:30 AM"),
    AFTERNOON("Afternoon", "दोपहर", "12:00 PM – 4:30 PM"),
    EVENING("Evening", "शाम", "5:00 PM – 8:00 PM"),
    NIGHT("Night", "रात", "8:30 PM – 10:30 PM")
}

data class DailyRoutineItem(
    val id: String,
    val period: RoutinePeriod,
    val timeLabel: String,
    val titleEn: String,
    val titleHi: String,
    val sentenceEn: String,
    val sentenceHi: String,
    val pastTenseVariantEn: String,
    val pastTenseVariantHi: String,
    val grammarTipEn: String,
    val grammarTipHi: String
)

enum class PracticeQuestionKind(val labelEn: String, val labelHi: String) {
    GRAMMAR("Grammar", "व्याकरण"),
    VOCABULARY("Vocabulary", "शब्दावली"),
    SENTENCE_FORMATION("Sentence Formation", "वाक्य निर्माण"),
    TRANSLATION("Translation", "अनुवाद"),
    CONVERSATION("Conversation", "बातचीत"),
    READING("Reading", "पठन"),
    WRITING("Writing", "लेखन"),
    SPEAKING("Speaking", "बोलना")
}

data class DailyPracticeQuestion(
    val id: String,
    val kind: PracticeQuestionKind,
    val weakTopicTag: String,
    val promptEn: String,
    val promptHi: String,
    val readingSnippet: String = "",
    val options: List<String> = emptyList(),
    val correctIndex: Int = -1,
    val sampleResponse: String = "",
    val explanationEn: String,
    val explanationHi: String
)
