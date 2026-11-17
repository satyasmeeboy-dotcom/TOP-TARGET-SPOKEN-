package com.example.data.curriculum

import com.example.model.DailyPracticeQuestion
import com.example.model.PracticeQuestionKind

object DailyPracticeCatalog {
    val questionPool: List<DailyPracticeQuestion> = listOf(
        // Grammar
        DailyPracticeQuestion(
            id = "dp_gram_1",
            kind = PracticeQuestionKind.GRAMMAR,
            weakTopicTag = "tenses",
            promptEn = "Choose the correct sentence:",
            promptHi = "सही वाक्य चुनें:",
            options = listOf(
                "She go to class every morning.",
                "She goes to class every morning.",
                "She going to class every morning.",
                "She gone to class every morning."
            ),
            correctIndex = 1,
            explanationEn = "With singular 'She' in Simple Present, add '-es' to 'go' -> 'goes'.",
            explanationHi = "एकवचन 'She' के साथ Simple Present में क्रिया में '-es' लगता है -> 'goes'।"
        ),
        DailyPracticeQuestion(
            id = "dp_gram_2",
            kind = PracticeQuestionKind.GRAMMAR,
            weakTopicTag = "articles",
            promptEn = "Fill in the blank: 'Rohan waited for ___ hour outside the library.'",
            promptHi = "रिक्त स्थान भरें: 'Rohan waited for ___ hour outside the library.'",
            options = listOf("a", "an", "the", "no article"),
            correctIndex = 1,
            explanationEn = "'Hour' begins with a silent 'h' and a vowel sound ('our'), so use 'an'.",
            explanationHi = "'Hour' का उच्चारण स्वर ('आवर') से शुरू होता है, इसलिए 'an' लगेगा।"
        ),
        DailyPracticeQuestion(
            id = "dp_gram_3",
            kind = PracticeQuestionKind.GRAMMAR,
            weakTopicTag = "preposition",
            promptEn = "Pick the right preposition: 'The English workshop is ___ Friday.'",
            promptHi = "सही Preposition चुनें: 'The English workshop is ___ Friday.'",
            options = listOf("in", "at", "on", "to"),
            correctIndex = 2,
            explanationEn = "Always use 'on' before specific days of the week (on Friday).",
            explanationHi = "सप्ताह के दिनों के नाम से पहले हमेशा 'on' (on Friday) लगाते हैं।"
        ),
        DailyPracticeQuestion(
            id = "dp_gram_4",
            kind = PracticeQuestionKind.GRAMMAR,
            weakTopicTag = "sva",
            promptEn = "Choose the grammatically accurate sentence:",
            promptHi = "व्याकरण की दृष्टि से सही वाक्य चुनें:",
            options = listOf(
                "Everyone in the class are ready.",
                "Everyone in the class is ready.",
                "Everyone in the class were ready.",
                "Everyone in the class am ready."
            ),
            correctIndex = 1,
            explanationEn = "Indefinite pronoun 'Everyone' is always singular and takes 'is'.",
            explanationHi = "'Everyone' हमेशा एकवचन माना जाता है, इसलिए इसके साथ 'is' आता है।"
        ),
        DailyPracticeQuestion(
            id = "dp_gram_5",
            kind = PracticeQuestionKind.GRAMMAR,
            weakTopicTag = "modals",
            promptEn = "Fix the sentence: 'You must ___ politely to elders.'",
            promptHi = "सही क्रिया रूप चुनें: 'You must ___ politely to elders.'",
            options = listOf("to speak", "speaking", "speak", "spoke"),
            correctIndex = 2,
            explanationEn = "After modal verbs ('must', 'can', 'should'), always use base verb V1 without 'to'.",
            explanationHi = "Modal verbs (must, can, should) के बाद बिना 'to' के क्रिया का पहला रूप (V1) आता है।"
        ),

        // Vocabulary
        DailyPracticeQuestion(
            id = "dp_voc_1",
            kind = PracticeQuestionKind.VOCABULARY,
            weakTopicTag = "vocabulary",
            promptEn = "What is the best meaning of 'Diligent'?",
            promptHi = "'Diligent' शब्द का सही अर्थ क्या है?",
            options = listOf("Lazy", "Hardworking & careful", "Forgetful", "Angry"),
            correctIndex = 1,
            explanationEn = "'Diligent' describes someone who works steadily and carefully.",
            explanationHi = "'Diligent' का अर्थ है मेहनती और लगन से काम करने वाला।"
        ),
        DailyPracticeQuestion(
            id = "dp_voc_2",
            kind = PracticeQuestionKind.VOCABULARY,
            weakTopicTag = "vocabulary",
            promptEn = "Which Gen Z expression means 'for real / honestly'?",
            promptHi = "किस Gen Z अभिव्यक्ति का अर्थ 'सच में / ईमानदारी से' है?",
            options = listOf("Lowkey", "No cap", "Ghosting", "Touch grass"),
            correctIndex = 1,
            explanationEn = "'No cap' emphasizes truth or honesty in casual English.",
            explanationHi = "'No cap' का उपयोग बात को सच और पक्का बताने के लिए किया जाता है।"
        ),
        DailyPracticeQuestion(
            id = "dp_voc_3",
            kind = PracticeQuestionKind.VOCABULARY,
            weakTopicTag = "vocabulary",
            promptEn = "Choose the antonym (opposite) of 'Hesitate':",
            promptHi = "'Hesitate' (हिचकिचाना) का विलोम शब्द चुनें:",
            options = listOf("Pause", "Proceed confidently", "Doubt", "Stammer"),
            correctIndex = 1,
            explanationEn = "To 'proceed confidently' is the opposite of hesitating nervously.",
            explanationHi = "बिना झिझक आगे बढ़ना ('proceed confidently') हिचकिचाने का विपरीत है।"
        ),

        // Sentence Formation
        DailyPracticeQuestion(
            id = "dp_sf_1",
            kind = PracticeQuestionKind.SENTENCE_FORMATION,
            weakTopicTag = "sentence_structure",
            promptEn = "Arrange into a natural question: 'are / going / where / you'",
            promptHi = "सही प्रश्नवाचक वाक्य चुनें: 'are / going / where / you'",
            options = listOf(
                "Where you are going?",
                "Where are you going?",
                "Are where you going?",
                "You going where are?"
            ),
            correctIndex = 1,
            explanationEn = "In Wh-questions, helping verb ('are') comes BEFORE the subject ('you').",
            explanationHi = "प्रश्नवाचक वाक्यों में सहायक क्रिया ('are') हमेशा Subject ('you') से पहले आती है।"
        ),
        DailyPracticeQuestion(
            id = "dp_sf_2",
            kind = PracticeQuestionKind.SENTENCE_FORMATION,
            weakTopicTag = "sentence_structure",
            promptEn = "Select the correct Subject + Verb + Object order:",
            promptHi = "सही वाक्य क्रम चुनें:",
            options = listOf(
                "English daily practice students.",
                "Students practice English daily.",
                "Practice English students daily.",
                "Daily students English practice."
            ),
            correctIndex = 1,
            explanationEn = "English sentences follow Subject ('Students') + Verb ('practice') + Object ('English').",
            explanationHi = "अंग्रेज़ी वाक्य रचना Subject + Verb + Object क्रम में होती है।"
        ),
        DailyPracticeQuestion(
            id = "dp_sf_3",
            kind = PracticeQuestionKind.SENTENCE_FORMATION,
            weakTopicTag = "conditionals",
            promptEn = "Complete cleanly: 'If it rains tomorrow, we ___ stay indoors.'",
            promptHi = "सही विकल्प चुनें: 'If it rains tomorrow, we ___ stay indoors.'",
            options = listOf("will", "would had", "stayed", "are stay"),
            correctIndex = 0,
            explanationEn = "First conditional pairs Simple Present ('rains') with 'will + V1'.",
            explanationHi = "First Conditional में 'If + Present Simple' के साथ 'will + V1' आता है।"
        ),

        // Translation
        DailyPracticeQuestion(
            id = "dp_tr_1",
            kind = PracticeQuestionKind.TRANSLATION,
            weakTopicTag = "tenses",
            promptEn = "Translate into English: 'मैं कल बाज़ार गया था।'",
            promptHi = "सही अंग्रेज़ी अनुवाद चुनें: 'मैं कल बाज़ार गया था।'",
            options = listOf(
                "I go to market yesterday.",
                "I went to the market yesterday.",
                "I am going to market yesterday.",
                "I gone to the market yesterday."
            ),
            correctIndex = 1,
            explanationEn = "'Yesterday' shows completed past action, so use V2 'went'.",
            explanationHi = "'कल (yesterday)' बीते हुए समय का संकेत है, इसलिए V2 'went' आएगा।"
        ),
        DailyPracticeQuestion(
            id = "dp_tr_2",
            kind = PracticeQuestionKind.TRANSLATION,
            weakTopicTag = "modals",
            promptEn = "Translate politely: 'क्या आप इसे दोबारा समझा सकते हैं?'",
            promptHi = "सही विनम्र अनुवाद चुनें:",
            options = listOf(
                "Can you to explain this again?",
                "Could you please explain this again?",
                "You explain this again?",
                "Please explaining this again."
            ),
            correctIndex = 1,
            explanationEn = "'Could you please + V1...?' is the most polite spoken request.",
            explanationHi = "विनम्र अनुरोध के लिए 'Could you please + V1...?' सबसे अच्छा तरीका है।"
        ),
        DailyPracticeQuestion(
            id = "dp_tr_3",
            kind = PracticeQuestionKind.TRANSLATION,
            weakTopicTag = "preposition",
            promptEn = "Translate into English: 'वह अंग्रेज़ी बोलने में अच्छी है।'",
            promptHi = "सही अनुवाद चुनें: 'वह अंग्रेज़ी बोलने में अच्छी है।'",
            options = listOf(
                "She is good in speaking English.",
                "She is good at speaking English.",
                "She is good on speak English.",
                "She good at speaking English."
            ),
            correctIndex = 1,
            explanationEn = "Always use 'good at' (not 'good in') for skills and subjects.",
            explanationHi = "किसी कौशल या विषय में निपुणता के लिए 'good at' का प्रयोग होता है, 'good in' नहीं।"
        ),

        // Conversation
        DailyPracticeQuestion(
            id = "dp_conv_1",
            kind = PracticeQuestionKind.CONVERSATION,
            weakTopicTag = "speaking",
            promptEn = "A classmate says: 'Thank you so much for helping me!' What is the most natural reply?",
            promptHi = "जब कोई 'Thank you' कहे, तो सबसे स्वाभाविक उत्तर क्या होगा?",
            options = listOf(
                "Never mention it—you're very welcome!",
                "Go away now.",
                "Yes, I did big help.",
                "Why you saying thanks?"
            ),
            correctIndex = 0,
            explanationEn = "'You're very welcome!' or 'My pleasure!' sounds warm and polite.",
            explanationHi = "'You're very welcome!' एक विनम्र और दोस्ताना जवाब है।"
        ),
        DailyPracticeQuestion(
            id = "dp_conv_2",
            kind = PracticeQuestionKind.CONVERSATION,
            weakTopicTag = "speaking",
            promptEn = "You didn't hear your teacher clearly. How should you respond respectfully?",
            promptHi = "यदि आप शिक्षक की बात साफ न सुन पाएं, तो क्या कहेंगे?",
            options = listOf(
                "Speak loudly!",
                "I beg your pardon, could you repeat that please?",
                "Say again fast.",
                "I am not hearing you."
            ),
            correctIndex = 1,
            explanationEn = "'Could you repeat that please?' or 'I beg your pardon' is polite classroom English.",
            explanationHi = "'Could you repeat that please?' विनम्र और सभ्य तरीका है।"
        ),

        // Reading
        DailyPracticeQuestion(
            id = "dp_read_1",
            kind = PracticeQuestionKind.READING,
            weakTopicTag = "reading",
            promptEn = "Read the short note and answer: What is the key takeaway?",
            promptHi = "छोटा अनुच्छेद पढ़ें और मुख्य निष्कर्ष चुनें:",
            readingSnippet = "Reading aloud for five minutes daily trains your mouth muscles and reduces fear when talking in front of others.",
            options = listOf(
                "Reading silently is the only way.",
                "Five minutes of daily aloud reading builds confidence.",
                "You must read for five hours straight.",
                "Fear never goes away."
            ),
            correctIndex = 1,
            explanationEn = "The passage explains that five minutes of reading aloud daily builds confidence.",
            explanationHi = "अनुच्छेद बताता है कि रोज़ 5 मिनट ज़ोर से पढ़ने से झिझक और डर कम होता है।"
        ),
        DailyPracticeQuestion(
            id = "dp_read_2",
            kind = PracticeQuestionKind.READING,
            weakTopicTag = "reading",
            promptEn = "Read and answer: Why did Aman carry a pocket notebook?",
            promptHi = "पढ़ें और बताएं: अमन अपनी जेब में छोटी कॉपी क्यों रखता था?",
            readingSnippet = "Whenever Aman heard an unfamiliar word in class, he wrote it down in a pocket notebook and revised three words every evening.",
            options = listOf(
                "To draw cartoons",
                "To note and revise new English words",
                "To hide his homework",
                "To keep grocery bills"
            ),
            correctIndex = 1,
            explanationEn = "He wrote unfamiliar words and revised three every evening.",
            explanationHi = "वह नए शब्द लिखकर शाम को उनका अभ्यास करता था।"
        ),

        // Writing (evaluated by CHENI grammar analyzer!)
        DailyPracticeQuestion(
            id = "dp_write_1",
            kind = PracticeQuestionKind.WRITING,
            weakTopicTag = "writing",
            promptEn = "Write 1 clean English sentence about a goal you want to achieve this month.",
            promptHi = "इस महीने आप जो लक्ष्य हासिल करना चाहते हैं, उस पर 1 सही अंग्रेज़ी वाक्य लिखें।",
            sampleResponse = "I want to practice spoken English with CHENI every day.",
            explanationEn = "Start with a capital letter, use 'to + V1', and end with a full stop.",
            explanationHi = "वाक्य बड़े अक्षर से शुरू करें, 'to + V1' लगाएं और अंत में पूर्ण विराम (.) दें।"
        ),
        DailyPracticeQuestion(
            id = "dp_write_2",
            kind = PracticeQuestionKind.WRITING,
            weakTopicTag = "tenses",
            promptEn = "Write 1 sentence in Past Simple telling something good you did yesterday.",
            promptHi = "कल आपने जो कोई अच्छा काम किया, उस पर Past Simple (V2) में 1 वाक्य लिखें।",
            sampleResponse = "Yesterday, I helped my friend revise English grammar.",
            explanationEn = "Use Past Simple (V2 like 'helped', 'learned', 'read') with 'yesterday'.",
            explanationHi = "'yesterday' के साथ हमेशा क्रिया का दूसरा रूप (V2) लगाएं।"
        ),

        // Speaking (evaluated by CHENI speaking/grammar analyzer!)
        DailyPracticeQuestion(
            id = "dp_speak_1",
            kind = PracticeQuestionKind.SPEAKING,
            weakTopicTag = "speaking",
            promptEn = "Speak or type aloud: Introduce yourself politely in 1–2 sentences (no private phone/address!).",
            promptHi = "बोलें या लिखें: 1-2 विनम्र वाक्यों में अपना परिचय दें (बिना फोन नंबर या पते के)।",
            sampleResponse = "Hello! I enjoy learning new words and practicing spoken English daily.",
            explanationEn = "Speak steadily, pronounce V/W clearly, and avoid rushing.",
            explanationHi = "आराम से स्पष्ट आवाज़ में बोलें—जल्दबाज़ी न करें।"
        ),
        DailyPracticeQuestion(
            id = "dp_speak_2",
            kind = PracticeQuestionKind.SPEAKING,
            weakTopicTag = "speaking",
            promptEn = "Speak or type aloud: Politely ask a shopkeeper for the price of a notebook.",
            promptHi = "बोलें या लिखें: दुकानदार से विनम्रता से कॉपी की कीमत पूछें।",
            sampleResponse = "Excuse me, could you tell me how much this notebook costs?",
            explanationEn = "Start politely with 'Excuse me' or 'Could you tell me...'.",
            explanationHi = "विनम्र शुरुआत के लिए 'Excuse me' या 'Could you tell me...' का उपयोग करें।"
        )
    )

    /**
     * Generates 10 fresh, non-repeating questions for a given day, prioritizing any weak areas
     * and guaranteeing a balanced mix of Grammar, Vocabulary, Sentence Formation, Translation,
     * Conversation, Reading, Writing, and Speaking.
     */
    fun generateTodaysTenQuestions(
        epochDay: Long,
        weakTags: List<String> = emptyList()
    ): List<DailyPracticeQuestion> {
        val byKind = questionPool.groupBy { it.kind }
        val daySeed = epochDay.coerceAtLeast(0L).toInt()

        // Ensure at least 1 fresh question from each of the 8 required skills
        val coreEight = PracticeQuestionKind.entries.mapIndexedNotNull { idx, kind ->
            val bucket = byKind[kind].orEmpty()
            if (bucket.isEmpty()) return@mapIndexedNotNull null
            val sortedBucket = if (weakTags.isNotEmpty()) {
                bucket.sortedByDescending { it.weakTopicTag in weakTags }
            } else bucket
            sortedBucket[(daySeed + idx) % sortedBucket.size]
        }.toMutableList()

        val pickedIds = coreEight.map { it.id }.toMutableSet()
        // Fill remaining 2 slots up to 10 without repeating, prioritizing weakTags first
        val remainingCandidates = questionPool
            .filter { it.id !in pickedIds }
            .sortedByDescending { it.weakTopicTag in weakTags }

        val rotatedOffset = if (remainingCandidates.isNotEmpty()) daySeed % remainingCandidates.size else 0
        for (i in remainingCandidates.indices) {
            if (coreEight.size >= 10) break
            val candidate = remainingCandidates[(rotatedOffset + i) % remainingCandidates.size]
            if (pickedIds.add(candidate.id)) {
                coreEight.add(candidate)
            }
        }
        return coreEight
    }
}
