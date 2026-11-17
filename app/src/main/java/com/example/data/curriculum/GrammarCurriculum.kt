package com.example.data.curriculum

import com.example.model.AssessmentQuestion
import com.example.model.DailyStudyTaskSpec
import com.example.model.GrammarTopic
import com.example.model.SkillType

object GrammarCurriculum {
    val allTopics: List<GrammarTopic> by lazy {
        (GrammarCurriculumPart1.topics + GrammarCurriculumPart2.topics).sortedBy { it.order }
    }

    fun topicById(id: String): GrammarTopic? = allTopics.find { it.id == id }

    val dailyStudyPlanSpecs = listOf(
        DailyStudyTaskSpec("grammar", "Complete 1 Grammar Lesson & Quiz", "1 व्याकरण पाठ और क्विज़ पूरा करें", "learn"),
        DailyStudyTaskSpec("daily_routine", "Practice Daily Routine in English", "अंग्रेज़ी में दैनिक दिनचर्या बोलें", "routine"),
        DailyStudyTaskSpec("speaking", "Speak 3 Sentences with AI Teacher", "AI शिक्षक के साथ 3 वाक्य बोलें", "teacher"),
        DailyStudyTaskSpec("listening", "Finish 1 Listening Passage", "1 Listening अभ्यास सुनें और हल करें", "skills"),
        DailyStudyTaskSpec("reading", "Read 1 Story with Hindi Meanings", "हिंदी अर्थ के साथ 1 कहानी पढ़ें", "skills"),
        DailyStudyTaskSpec("writing", "Write & Auto-Check 1 Paragraph", "1 पैराग्राफ लिखें और व्याकरण जांचें", "skills"),
        DailyStudyTaskSpec("genz_10", "Learn Today's 10 Gen Z Words", "आज के 10 नए Gen Z शब्द सीखें", "words"),
        DailyStudyTaskSpec("vocab_10", "Learn Today's 10 Vocabulary Words", "आज के 10 नए Vocabulary शब्द सीखें", "words"),
        DailyStudyTaskSpec("phrases_idioms", "Learn Today's Phrases & Idioms", "आज के नए मुहावरे व वाक्यांश सीखें", "words"),
        DailyStudyTaskSpec("daily_quiz", "Take the 20-Word Daily Challenge", "20 शब्दों की दैनिक चुनौती क्विज़ दें", "words"),
        DailyStudyTaskSpec("revision", "Revise 1 Saved Topic or Mistake", "1 पिछला पाठ या गलती दोहराएं", "progress")
    )

    val assessmentQuestions = listOf(
        AssessmentQuestion(
            id = "aq_grammar_1",
            skill = SkillType.GRAMMAR,
            promptEn = "Choose the correct sentence:",
            promptHi = "सही वाक्य चुनें:",
            options = listOf(
                "He go to college every day.",
                "He goes to college every day.",
                "He going to college every day.",
                "He gone to college every day."
            ),
            correctIndex = 1,
            weakTopicId = "tenses"
        ),
        AssessmentQuestion(
            id = "aq_grammar_2",
            skill = SkillType.GRAMMAR,
            promptEn = "Fill in the blank: 'She is ___ honest teacher.'",
            promptHi = "रिक्त स्थान भरें: 'She is ___ honest teacher.'",
            options = listOf("a", "an", "the", "no article"),
            correctIndex = 1,
            weakTopicId = "articles"
        ),
        AssessmentQuestion(
            id = "aq_grammar_3",
            skill = SkillType.GRAMMAR,
            promptEn = "Pick the right preposition: 'The meeting is ___ Monday.'",
            promptHi = "सही Preposition चुनें: 'The meeting is ___ Monday.'",
            options = listOf("in", "at", "on", "for"),
            correctIndex = 2,
            weakTopicId = "preposition"
        ),
        AssessmentQuestion(
            id = "aq_vocab_1",
            skill = SkillType.VOCABULARY,
            promptEn = "What is the closest synonym of 'Confident'?",
            promptHi = "'Confident' का सबसे करीबी समानार्थी शब्द क्या है?",
            options = listOf("Nervous", "Self-assured", "Silent", "Careless"),
            correctIndex = 1,
            weakTopicId = "adjective"
        ),
        AssessmentQuestion(
            id = "aq_vocab_2",
            skill = SkillType.VOCABULARY,
            promptEn = "What does the word 'Consistent' mean?",
            promptHi = "'Consistent' शब्द का अर्थ क्या है?",
            options = listOf("Regular and steady", "Very angry", "Always late", "Expensive"),
            correctIndex = 0,
            weakTopicId = "adverb"
        ),
        AssessmentQuestion(
            id = "aq_reading_1",
            skill = SkillType.READING,
            promptEn = "Read: 'Riya practiced speaking for ten minutes daily, and within a month her hesitation vanished.' Why did Riya improve?",
            promptHi = "पढ़ें और बताएं: रिया का संकोच कैसे दूर हुआ?",
            options = listOf(
                "She bought expensive books",
                "She practiced speaking daily",
                "She avoided talking to people",
                "She studied only once a month"
            ),
            correctIndex = 1,
            weakTopicId = "sentence_structure"
        ),
        AssessmentQuestion(
            id = "aq_listening_1",
            skill = SkillType.LISTENING,
            promptEn = "Listen carefully (tap Play Audio) and choose the main message of the speaker.",
            promptHi = "ध्यान से सुनें (Play Audio दबाएं) और वक्ता का मुख्य संदेश चुनें।",
            options = listOf(
                "Mistakes help you learn faster",
                "Never speak until you know every word",
                "Grammar is unnecessary",
                "Stop practicing on weekends"
            ),
            correctIndex = 0,
            weakTopicId = "modals"
        ),
        AssessmentQuestion(
            id = "aq_grammar_4",
            skill = SkillType.GRAMMAR,
            promptEn = "Which is the correct Passive Voice of 'Students respect teachers'?",
            promptHi = "'Students respect teachers' का सही Passive Voice चुनें:",
            options = listOf(
                "Teachers are respected by students.",
                "Teachers is respected by students.",
                "Teachers respected by students.",
                "Teachers were respect by students."
            ),
            correctIndex = 0,
            weakTopicId = "voice"
        ),
        AssessmentQuestion(
            id = "aq_grammar_5",
            skill = SkillType.GRAMMAR,
            promptEn = "Complete the Conditional: 'If I ___ you, I would practice daily.'",
            promptHi = "वाक्य पूरा करें: 'If I ___ you, I would practice daily.'",
            options = listOf("am", "was", "were", "will be"),
            correctIndex = 2,
            weakTopicId = "conditionals"
        )
    )
}
