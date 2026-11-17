package com.example.data.ai

import com.example.model.GrammarMistakeFeedback
import com.example.model.GrammarTopic
import com.example.model.StudentLevel
import com.example.model.TeacherStage
import kotlinx.coroutines.delay

/**
 * Sample / offline pedagogical implementation used when Firebase AI Logic is not enabled.
 * All outputs are clearly flagged with `isSampleMode = true` so the UI informs the student.
 */
class SampleAiRepository : AiRepository {
    override val isSampleImplementation: Boolean = true

    private val compliments = listOf(
        "Excellent! You are doing amazing — CHENI is so proud of you!",
        "Very Good! Keep learning every single day—you are unstoppable!",
        "Fantastic! Your English is improving step by step, keep shining!",
        "Great Effort! Never give up—mistakes are proof that you are learning!",
        "Bravo! Your confidence is glowing—keep speaking fearlessly!",
        "Well Done! Consistency turns beginners into fluent speakers!"
    )

    private val bilingualCompliments = listOf(
        "Excellent! You are doing amazing—CHENI is proud of your dedication!" to "शाबाश! आप कमाल कर रहे हैं—CHENI को आपकी मेहनत पर गर्व है!",
        "Mistakes are proof that you are learning—CHENI believes in you! Keep speaking boldly!" to "गलतियाँ इस बात का सबूत हैं कि आप सीख रहे हैं—CHENI आपके साथ है, निडर होकर बोलते रहें!",
        "Fantastic progress! CHENI sees every word bringing fluency closer." to "शानदार प्रगति! CHENI देख रही है कि आपका हर नया शब्द आपको धाराप्रवाह बोलने के करीब लाता है।",
        "You have true courage and curiosity—CHENI admires your learning spirit!" to "आपके पास सच्चा साहस और जिज्ञासा है—CHENI आपकी सीखने की भावना की सराहना करती है!",
        "Great effort today! CHENI knows small daily steps create a massive glow up in English." to "आज का प्रयास बेहतरीन रहा! CHENI जानती है कि रोज़ के छोटे कदम अंग्रेज़ी में बड़ा निखार लाते हैं।"
    )

    override fun motivationalCompliment(context: String): String {
        val index = ((System.currentTimeMillis() / 1000L + context.hashCode()) % compliments.size)
            .toInt()
            .let { if (it < 0) -it else it }
        return compliments[index]
    }

    override fun performanceCompliment(
        scoreRatio: Float,
        isCorrect: Boolean,
        context: String
    ): String {
        return when {
            !isCorrect || scoreRatio < 0.5f ->
                "Good effort trying! Review the corrected rule carefully—CHENI will guide you step by step."
            scoreRatio >= 0.85f ->
                "Excellent! Your grammar is improving and you are becoming more confident!"
            else ->
                "Nice progress! Keep practicing $context daily—you are steadily getting sharper!"
        }
    }

    fun bilingualComplimentPair(seed: Int = 0): Pair<String, String> {
        val index = ((System.currentTimeMillis() / 1000L + seed) % bilingualCompliments.size)
            .toInt()
            .let { if (it < 0) -it else it }
        return bilingualCompliments[index]
    }

    override suspend fun analyzeGrammar(sentence: String): GrammarMistakeFeedback {
        delay(180) // Smooth natural processing beat
        val trimmed = sentence.trim()
        if (trimmed.isBlank()) {
            return GrammarMistakeFeedback(
                isCorrect = false,
                originalSentence = "",
                correctedSentence = "I practice English every day.",
                highlightedMistake = "(Empty input)",
                mistakeCategory = "Sentence Structure",
                whyIncorrectEn = "Please type or speak at least a short English sentence so we can check it together.",
                grammarRuleHi = "कृपया कम से कम एक छोटा अंग्रेज़ी वाक्य लिखें या बोलें। हर वाक्य में Subject + Verb होना ज़रूरी है।",
                extraExamples = listOf(
                    "I learn new words daily.",
                    "She speaks politely."
                ),
                tryAgainPrompt = "Try writing a simple sentence about your day!",
                compliment = "Ready whenever you are—start with one small sentence!",
                isSampleMode = true
            )
        }

        val lower = trimmed.lowercase()

        // 1. Article mistakes (vowel sound vs consonant sound)
        val aVowelRegex = Regex("\\b(a)\\s+(apple|orange|umbrella|elephant|hour|honest|idea|egg|ice|umbrella)\\b", RegexOption.IGNORE_CASE)
        aVowelRegex.find(trimmed)?.let { match ->
            val word = match.groupValues[2]
            val fixed = trimmed.replaceRange(match.range, "an $word")
            return buildFeedback(
                original = trimmed,
                corrected = polishCapitalAndPunctuation(fixed),
                highlighted = match.value,
                category = "Article Mistake",
                whyEn = "'$word' starts with a vowel sound, so 'a' is incorrect before it.",
                ruleHi = "जब शब्द का उच्चारण स्वर (अ, आ, इ, ई, उ, ए, ओ) से शुरू हो (जैसे honest, hour, apple), तो 'a' नहीं बल्कि 'an' लगाते हैं।",
                examples = listOf(
                    "She waited for an hour.",
                    "He is an honest friend."
                ),
                tryPrompt = "Try rewriting the sentence with 'an $word'."
            )
        }

        val anConsonantRegex = Regex("\\b(an)\\s+(university|boy|girl|book|pen|teacher|car|school|user)\\b", RegexOption.IGNORE_CASE)
        anConsonantRegex.find(trimmed)?.let { match ->
            val word = match.groupValues[2]
            val fixed = trimmed.replaceRange(match.range, "a $word")
            return buildFeedback(
                original = trimmed,
                corrected = polishCapitalAndPunctuation(fixed),
                highlighted = match.value,
                category = "Article Mistake",
                whyEn = "'$word' begins with a consonant sound (e.g. 'yoo' or 'b/g/t'), so use 'a' instead of 'an'.",
                ruleHi = "'$word' का उच्चारण व्यंजन ध्वनि से शुरू होता है (जैसे university = 'यूनिवर्सिटी'), इसलिए 'an' की जगह 'a' लगेगा।",
                examples = listOf(
                    "She studies at a university.",
                    "I bought a new book."
                ),
                tryPrompt = "Rewrite the sentence using 'a $word'."
            )
        }

        // 2. Subject-Verb Agreement
        val svaRules = listOf(
            Regex("\\b(he|she|it)\\s+go\\b", RegexOption.IGNORE_CASE) to ("goes" to "With singular 'he/she/it' in Present Simple, add '-es' to 'go'."),
            Regex("\\b(he|she|it)\\s+have\\b", RegexOption.IGNORE_CASE) to ("has" to "Singular third-person 'he/she/it' takes 'has', not 'have'."),
            Regex("\\b(they|we|you)\\s+is\\b", RegexOption.IGNORE_CASE) to ("are" to "Plural subjects ('they/we/you') always take 'are', never 'is'."),
            Regex("\\b(everyone|everybody|someone|nobody)\\s+are\\b", RegexOption.IGNORE_CASE) to ("is" to "Indefinite pronouns like 'everyone' are grammatically singular and take 'is'."),
            Regex("\\bi\\s+is\\b", RegexOption.IGNORE_CASE) to ("I am" to "'I' always pairs with 'am' in the present tense, never 'is'."),
            Regex("\\b(he|she|it)\\s+don't\\b", RegexOption.IGNORE_CASE) to ("doesn't" to "With 'he/she/it', use 'doesn't' instead of 'don't'.")
        )
        for ((regex, pair) in svaRules) {
            regex.find(trimmed)?.let { match ->
                val replacement = if (pair.first == "I am") "I am" else "${match.groupValues[1]} ${pair.first}"
                val fixed = trimmed.replaceRange(match.range, replacement)
                return buildFeedback(
                    original = trimmed,
                    corrected = polishCapitalAndPunctuation(fixed),
                    highlighted = match.value,
                    category = "Subject-Verb Agreement",
                    whyEn = pair.second,
                    ruleHi = "कर्ता (Subject) एकवचन हो तो एकवचन क्रिया (is, has, goes, doesn't) और बहुवचन हो तो बहुवचन क्रिया (are, have, go) लगाएं। 'I' के साथ हमेशा 'am' आता है।",
                    examples = listOf(
                        "She goes to class on time.",
                        "They are practicing English together."
                    ),
                    tryPrompt = "Now try fixing the verb agreement yourself!"
                )
            }
        }

        // 3. Verb Form mistakes after did / didn't / doesn't / modals
        val verbFormRules = listOf(
            Regex("\\b(did|didn't|did not)\\s+(went|ate|came|spoke|wrote|saw|bought)\\b", RegexOption.IGNORE_CASE) to "Always use the base verb (V1) after 'did' or 'didn't'—never V2.",
            Regex("\\b(can|could|should|must|will|would|may)\\s+to\\s+([a-z]+)\\b", RegexOption.IGNORE_CASE) to "Never place 'to' between a modal verb (can/should/must) and the main verb.",
            Regex("\\b(doesn't|does not)\\s+([a-z]+s)\\b", RegexOption.IGNORE_CASE) to "After 'doesn't', drop the '-s/-es' and use base verb V1."
        )
        for ((regex, why) in verbFormRules) {
            regex.find(trimmed)?.let { match ->
                val v1Map = mapOf(
                    "went" to "go", "ate" to "eat", "came" to "come",
                    "spoke" to "speak", "wrote" to "write", "saw" to "see", "bought" to "buy"
                )
                val fixed = when {
                    match.value.contains(" to ", ignoreCase = true) ->
                        trimmed.replaceRange(match.range, "${match.groupValues[1]} ${match.groupValues[2]}")
                    v1Map.containsKey(match.groupValues[2].lowercase()) ->
                        trimmed.replaceRange(match.range, "${match.groupValues[1]} ${v1Map[match.groupValues[2].lowercase()]}")
                    else -> {
                        val base = match.groupValues[2].removeSuffix("es").removeSuffix("s")
                        trimmed.replaceRange(match.range, "${match.groupValues[1]} $base")
                    }
                }
                return buildFeedback(
                    original = trimmed,
                    corrected = polishCapitalAndPunctuation(fixed),
                    highlighted = match.value,
                    category = "Verb Form Mistake",
                    whyEn = why,
                    ruleHi = "did/didn't/doesn't और Modals (can, should, must) के बाद हमेशा क्रिया का पहला रूप (V1) आता है, कभी V2 या 'to' नहीं लगता।",
                    examples = listOf(
                        "Did you go to school yesterday?",
                        "You must speak clearly."
                    ),
                    tryPrompt = "Try writing your sentence with the base verb (V1)."
                )
            }
        }

        // 4. Tense Mistakes (past time marker + present verb, or am/is/are + V1)
        val bePlusV1 = Regex("\\b(i am|he is|she is|they are|we are)\\s+(go|come|play|eat|read|write|speak|study|learn|work)\\b", RegexOption.IGNORE_CASE)
        bePlusV1.find(trimmed)?.let { match ->
            val subjAndBe = match.groupValues[1]
            val baseVerb = match.groupValues[2]
            val ing = if (baseVerb.endsWith("e", ignoreCase = true) && !baseVerb.endsWith("ee", ignoreCase = true)) {
                baseVerb.dropLast(1) + "ing"
            } else {
                baseVerb + "ing"
            }
            val fixed = trimmed.replaceRange(match.range, "$subjAndBe $ing")
            return buildFeedback(
                original = trimmed,
                corrected = polishCapitalAndPunctuation(fixed),
                highlighted = match.value,
                category = "Tense Mistake",
                whyEn = "After 'am/is/are' for a continuing action, use verb+ing (Present Continuous), or remove 'am/is/are' for Simple Present.",
                ruleHi = "'am/is/are' के साथ चल रहे काम के लिए क्रिया में '-ing' लगाते हैं (जैसे I am going), या आदत बताने के लिए 'am/is/are' हटाते हैं (जैसे I go)।",
                examples = listOf(
                    "I am learning English right now.",
                    "I go to class every morning."
                ),
                tryPrompt = "Try rewriting with '$subjAndBe $ing' or Simple Present!"
            )
        }

        if ((lower.contains("yesterday") || lower.contains("last night") || lower.contains("last week")) &&
            Regex("\\b(go|goes|eat|come|speak|write|am going)\\b").containsMatchIn(lower)
        ) {
            val pastFixed = trimmed
                .replace(Regex("\\bgoes\\b", RegexOption.IGNORE_CASE), "went")
                .replace(Regex("\\bgo\\b", RegexOption.IGNORE_CASE), "went")
                .replace(Regex("\\beat\\b", RegexOption.IGNORE_CASE), "ate")
                .replace(Regex("\\bcome\\b", RegexOption.IGNORE_CASE), "came")
                .replace(Regex("\\bspeak\\b", RegexOption.IGNORE_CASE), "spoke")
                .replace(Regex("\\bwrite\\b", RegexOption.IGNORE_CASE), "wrote")
            return buildFeedback(
                original = trimmed,
                corrected = polishCapitalAndPunctuation(pastFixed),
                highlighted = "past time marker + present verb",
                category = "Tense Mistake",
                whyEn = "Time markers like 'yesterday' or 'last week' refer to completed past time, so the verb must be Past Simple (V2).",
                ruleHi = "जब वाक्य में 'yesterday' या 'last week' आए, तो काम बीत चुका है—इसलिए क्रिया का दूसरा रूप (V2: went, ate, spoke) लगाएं।",
                examples = listOf(
                    "I went to the library yesterday.",
                    "We spoke English in class last week."
                ),
                tryPrompt = "Rewrite your sentence using the V2 past form!"
            )
        }

        // 5. Preposition Mistakes
        val prepRules = listOf(
            Regex("\\bin\\s+(monday|tuesday|wednesday|thursday|friday|saturday|sunday)\\b", RegexOption.IGNORE_CASE) to ("on" to "Use 'on' before specific days of the week, not 'in'."),
            Regex("\\bdiscuss\\s+about\\b", RegexOption.IGNORE_CASE) to ("discuss" to "'Discuss' already means 'talk about', so adding 'about' is redundant."),
            Regex("\\blisten\\s+(music|teacher|me|him|her)\\b", RegexOption.IGNORE_CASE) to ("listen to" to "'Listen' always takes the preposition 'to' before what you hear."),
            Regex("\\bgood\\s+in\\s+([a-z]+)\\b", RegexOption.IGNORE_CASE) to ("good at" to "Use 'good at' (not 'good in') for subjects or skills like English."),
            Regex("\\bafraid\\s+from\\b", RegexOption.IGNORE_CASE) to ("afraid of" to "'Afraid' always pairs with the preposition 'of', never 'from'."),
            Regex("\\breach\\s+to\\s+home\\b", RegexOption.IGNORE_CASE) to ("reach home" to "'Reach' does not take 'to' before 'home'.")
        )
        for ((regex, pair) in prepRules) {
            regex.find(trimmed)?.let { match ->
                val fixed = when {
                    pair.first == "on" -> trimmed.replaceRange(match.range, "on ${match.groupValues[1]}")
                    pair.first == "discuss" -> trimmed.replaceRange(match.range, "discuss")
                    pair.first == "good at" -> trimmed.replaceRange(match.range, "good at ${match.groupValues[1]}")
                    pair.first == "afraid of" -> trimmed.replaceRange(match.range, "afraid of")
                    pair.first == "reach home" -> trimmed.replaceRange(match.range, "reach home")
                    else -> trimmed.replaceRange(match.range, "listen to ${match.groupValues[1]}")
                }
                return buildFeedback(
                    original = trimmed,
                    corrected = polishCapitalAndPunctuation(fixed),
                    highlighted = match.value,
                    category = "Preposition Mistake",
                    whyEn = pair.second,
                    ruleHi = "सही Preposition का ध्यान रखें: दिनों के साथ 'on', 'listen' के बाद 'to', कौशल के लिए 'good at', 'afraid of', और 'discuss' के साथ 'about' नहीं लगता।",
                    examples = listOf(
                        "She is very good at speaking English.",
                        "Please listen to your teacher carefully."
                    ),
                    tryPrompt = "Fix the preposition and try the sentence again!"
                )
            }
        }

        // 6. Vocabulary Usage Mistakes
        val vocabFixes = mapOf(
            "more better" to ("much better" to "'Better' is already comparative, so never put 'more' before it."),
            "return back" to ("return" to "'Return' already means 'come/give back', so omit 'back'."),
            "repeat again" to ("repeat" to "'Repeat' already means 'say again', so 'again' is unnecessary."),
            "cousin brother" to ("cousin" to "In standard English, use 'cousin' alone without 'brother' or 'sister'.")
        )
        for ((bad, info) in vocabFixes) {
            if (lower.contains(bad)) {
                val fixed = trimmed.replace(Regex(bad, RegexOption.IGNORE_CASE), info.first)
                return buildFeedback(
                    original = trimmed,
                    corrected = polishCapitalAndPunctuation(fixed),
                    highlighted = bad,
                    category = "Vocabulary Usage Mistake",
                    whyEn = info.second,
                    ruleHi = "कुछ शब्दों में अर्थ पहले से शामिल होता है—जैसे 'better' के साथ 'more' और 'repeat' के साथ 'again' नहीं लगाते।",
                    examples = listOf(
                        "Your pronunciation is much better now.",
                        "Could you please repeat that sentence?"
                    ),
                    tryPrompt = "Try writing the cleaner expression!"
                )
            }
        }

        // 7. Common Spelling Mistakes
        val spellingMap = mapOf(
            "grammer" to "grammar",
            "recieve" to "receive",
            "beleive" to "believe",
            "seperate" to "separate",
            "tommorow" to "tomorrow",
            "writting" to "writing",
            "enviroment" to "environment",
            "definately" to "definitely",
            "untill" to "until",
            "langauge" to "language"
        )
        for ((wrong, right) in spellingMap) {
            if (Regex("\\b$wrong\\b", RegexOption.IGNORE_CASE).containsMatchIn(trimmed)) {
                val fixed = trimmed.replace(Regex("\\b$wrong\\b", RegexOption.IGNORE_CASE), right)
                return buildFeedback(
                    original = trimmed,
                    corrected = polishCapitalAndPunctuation(fixed),
                    highlighted = wrong,
                    category = "Spelling Mistake",
                    whyEn = "'$wrong' is misspelled; the standard English spelling is '$right'.",
                    ruleHi = "सही वर्तनी (Spelling) '$right' है। कठिन शब्दों को टुकड़ों में तोड़कर याद करें।",
                    examples = listOf(
                        "Practice $right every day.",
                        "Paying attention to spelling builds polished writing."
                    ),
                    tryPrompt = "Type the sentence once more with '$right' spelled right!"
                )
            }
        }

        // 8. Word Order / Sentence Structure / Capitalization & Punctuation
        val advMisorder = Regex("^(always|usually|often|never)\\s+(i|he|she|we|they|you)\\s+([a-z]+)\\b", RegexOption.IGNORE_CASE)
        advMisorder.find(trimmed)?.let { match ->
            val adv = match.groupValues[1].lowercase()
            val subj = match.groupValues[2]
            val verb = match.groupValues[3]
            val fixed = trimmed.replaceRange(match.range, "$subj $adv $verb")
            return buildFeedback(
                original = trimmed,
                corrected = polishCapitalAndPunctuation(fixed),
                highlighted = match.value,
                category = "Word Order Mistake",
                whyEn = "Frequency adverbs like '$adv' usually sit BETWEEN the subject ('$subj') and the main verb ('$verb').",
                ruleHi = "'$adv' जैसे शब्द सामान्यतः Subject ('$subj') और मुख्य क्रिया ('$verb') के बीच आते हैं (जैसे I always practice)।",
                examples = listOf(
                    "I always revise my notes at night.",
                    "She usually speaks calmly."
                ),
                tryPrompt = "Try placing '$adv' after '$subj'!"
            )
        }

        val questionMisorder = Regex("^(where|what|when|why|how)\\s+(you|he|she|they|we)\\s+(are|is|am)\\b", RegexOption.IGNORE_CASE)
        questionMisorder.find(trimmed)?.let { match ->
            val wh = match.groupValues[1]
            val subj = match.groupValues[2]
            val aux = match.groupValues[3]
            val fixed = trimmed.replaceRange(match.range, "$wh $aux $subj")
            val finalQ = polishCapitalAndPunctuation(fixed).let { if (it.endsWith(".")) it.dropLast(1) + "?" else if (!it.endsWith("?")) "$it?" else it }
            return buildFeedback(
                original = trimmed,
                corrected = finalQ,
                highlighted = match.value,
                category = "Word Order Mistake",
                whyEn = "In English questions, the helping verb ('$aux') must come BEFORE the subject ('$subj').",
                ruleHi = "अंग्रेज़ी के प्रश्नवाचक वाक्यों में सहायक क्रिया ('$aux') हमेशा Subject ('$subj') से पहले आती है।",
                examples = listOf(
                    "Where are you going?",
                    "Why is she smiling?"
                ),
                tryPrompt = "Try forming another question with helping verb first!"
            )
        }

        val polished = polishCapitalAndPunctuation(trimmed)
        if (polished != trimmed) {
            return buildFeedback(
                original = trimmed,
                corrected = polished,
                highlighted = "Capitalization / Ending Punctuation",
                category = "Sentence Structure",
                whyEn = "Every English sentence should begin with a capital letter and end with a full stop (.), question mark (?), or exclamation mark (!).",
                ruleHi = "हर अंग्रेज़ी वाक्य बड़े अक्षर (Capital letter) से शुरू होना चाहिए और अंत में पूर्ण विराम (.) या प्रश्न चिह्न (?) लगना चाहिए।",
                examples = listOf(
                    "English becomes easy with daily practice.",
                    "Are you ready for today's lesson?"
                ),
                tryPrompt = "Rewrite your sentence with a capital start and ending punctuation!"
            )
        }

        return GrammarMistakeFeedback(
            isCorrect = true,
            originalSentence = trimmed,
            correctedSentence = polished,
            highlightedMistake = "None — Clean Sentence!",
            mistakeCategory = "Verified Correct",
            whyIncorrectEn = "Great structure! Your subject, verb form, articles, and punctuation align cleanly.",
            grammarRuleHi = "बहुत बढ़िया! आपका वाक्य व्याकरण और संरचना की दृष्टि से बिल्कुल सही और स्पष्ट है।",
            extraExamples = listOf(
                "Keep building longer sentences step by step.",
                "Try adding a descriptive adverb like 'confidently' next!"
            ),
            tryAgainPrompt = "Want to challenge yourself with a longer sentence?",
            compliment = motivationalCompliment("grammar"),
            isSampleMode = true
        )
    }

    override suspend fun evaluateWriting(promptTitle: String, text: String): GrammarMistakeFeedback {
        return analyzeGrammar(text)
    }

    override suspend fun evaluateSpeaking(
        scenarioTitle: String,
        spokenText: String
    ): SpeakingEvaluation {
        val feedback = analyzeGrammar(spokenText)
        val wordCount = spokenText.trim().split(Regex("\\s+")).filter { it.isNotBlank() }.size
        val fluency = when {
            wordCount >= 8 && feedback.isCorrect -> 92
            wordCount >= 5 && feedback.isCorrect -> 85
            wordCount >= 5 -> 74
            wordCount >= 2 -> 66
            else -> 50
        }
        val pronunciationTip = if (spokenText.contains("v", ignoreCase = true) || spokenText.contains("w", ignoreCase = true)) {
            "Tip: Keep 'V' sharp (upper teeth on lower lip) and 'W' soft (rounded lips)."
        } else {
            "Tip: Pause slightly at commas and stress key action verbs for natural rhythm."
        }
        val grammarNote = if (feedback.isCorrect) {
            "Clean spoken grammar in '$scenarioTitle'!"
        } else {
            "Detected ${feedback.mistakeCategory}: say '${feedback.correctedSentence}'."
        }
        val encouragement = if (feedback.isCorrect) {
            performanceCompliment(fluency / 100f, isCorrect = true, context = "speaking")
        } else {
            "Nice try speaking aloud! Notice the grammar adjustment above—try saying '${feedback.correctedSentence}' once slowly."
        }
        return SpeakingEvaluation(
            grammarNote = grammarNote,
            pronunciationTip = pronunciationTip,
            fluencyScore = fluency,
            sentenceFormationFeedback = feedback,
            encouragement = encouragement,
            isSampleMode = true
        )
    }

    override suspend fun teachNextTurn(
        studentReply: String,
        currentTopic: GrammarTopic,
        currentStage: TeacherStage,
        studentLevel: StudentLevel,
        weakCategories: List<String>,
        nickname: String
    ): TeacherReply {
        val nameCall = if (nickname.isNotBlank()) nickname else "dear student"
        val cleanReply = studentReply.trim()
        val lowerReply = cleanReply.lowercase()
        val weakHint = if (weakCategories.isNotEmpty()) {
            " (I also remember your recent focus area: ${weakCategories.first()}.)"
        } else ""

        // Attentive teacher: if student asks a question at any point, answer patiently before continuing
        val looksLikeStudentQuestion = cleanReply.endsWith("?") ||
            lowerReply.startsWith("why ") ||
            lowerReply.startsWith("what is ") ||
            lowerReply.contains("meaning") ||
            lowerReply.contains("explain again")
        if (looksLikeStudentQuestion && currentStage != TeacherStage.ASK_QUESTION && currentStage != TeacherStage.LISTEN_CORRECT) {
            val ex = currentTopic.examples.firstOrNull()
            return TeacherReply(
                englishText = "Great question, $nameCall! In '${currentTopic.titleEn}', remember: ${currentTopic.keyRules.firstOrNull() ?: currentTopic.summaryEn} For example: '${ex?.english.orEmpty()}'. Shall we continue to the next step?",
                hindiText = "बहुत अच्छा प्रश्न पूछा, $nameCall! '${currentTopic.titleHi}' में मुख्य नियम यह है: ${currentTopic.explanationHi} उदाहरण: '${ex?.hindi.orEmpty()}'। क्या हम आगे बढ़ें?",
                nextStage = currentStage,
                whiteboardRuleEn = "Q&A Help: ${currentTopic.keyRules.firstOrNull() ?: currentTopic.summaryEn}",
                whiteboardRuleHi = currentTopic.explanationHi,
                whiteboardExamples = currentTopic.examples.map { "${it.english} (${it.hindi})" },
                awardStars = 1
            )
        }

        // Attentive teacher: if student writes a sentence with a clear grammar error in any stage, gently fix it without embarrassing them
        val quickCheck = if (cleanReply.split(Regex("\\s+")).size >= 3 &&
            currentStage !in listOf(TeacherStage.ASK_QUESTION, TeacherStage.LISTEN_CORRECT) &&
            !lowerReply.startsWith("yes") &&
            !lowerReply.startsWith("please ") &&
            !lowerReply.startsWith("show me") &&
            !lowerReply.startsWith("give me") &&
            !lowerReply.startsWith("thank you")
        ) {
            analyzeGrammar(cleanReply).takeIf { !it.isCorrect }
        } else null

        if (quickCheck != null) {
            return TeacherReply(
                englishText = "I heard you carefully, $nameCall! Small friendly fix: instead of '${quickCheck.originalSentence}', say '${quickCheck.correctedSentence}'. ${quickCheck.whyIncorrectEn} Want to try repeating the corrected sentence?",
                hindiText = "मैंने आपकी बात ध्यान से सुनी, $nameCall! एक छोटा-सा सुधार: '${quickCheck.correctedSentence}' बोलें। ${quickCheck.grammarRuleHi} क्या आप इसे दोहराकर देखना चाहेंगे?",
                nextStage = TeacherStage.LISTEN_CORRECT,
                whiteboardRuleEn = "Gentle Correction: ${quickCheck.correctedSentence}",
                whiteboardRuleHi = quickCheck.grammarRuleHi,
                whiteboardExamples = quickCheck.extraExamples,
                feedback = quickCheck,
                awardStars = 1
            )
        }

        return when (currentStage) {
            TeacherStage.WELCOME -> TeacherReply(
                englishText = "Namaste and welcome, $nameCall! I am CHENI, your AI teacher and friend. Today we are studying '${currentTopic.titleEn}' at your ${studentLevel.labelEn} pace.$weakHint Are you ready to begin?",
                hindiText = "नमस्ते और स्वागत है, $nameCall! मैं CHENI हूँ, आपकी AI शिक्षक और दोस्त। आज हम '${currentTopic.titleHi}' सीखेंगे। क्या आप तैयार हैं?",
                nextStage = TeacherStage.INTRODUCE,
                whiteboardRuleEn = "Today's Lesson: ${currentTopic.titleEn}",
                whiteboardRuleHi = currentTopic.titleHi,
                whiteboardExamples = listOf(currentTopic.summaryEn),
                awardStars = 1
            )
            TeacherStage.INTRODUCE -> TeacherReply(
                englishText = "Wonderful! ${currentTopic.summaryEn} Let's break down the golden rule calmly—never rush. Should I explain the key rule with Hindi help now?",
                hindiText = "बहुत बढ़िया! ${currentTopic.explanationHi}",
                nextStage = TeacherStage.EXPLAIN,
                whiteboardRuleEn = currentTopic.keyRules.firstOrNull() ?: currentTopic.summaryEn,
                whiteboardRuleHi = currentTopic.explanationHi,
                whiteboardExamples = currentTopic.keyRules,
                awardStars = 1
            )
            TeacherStage.EXPLAIN -> {
                val exList = currentTopic.examples.map { "${it.english} (${it.hindi})" }
                TeacherReply(
                    englishText = "Look at our digital whiteboard for real daily-life examples. Read one example aloud, and let me know when you want your practice question!",
                    hindiText = "हमारे डिजिटल व्हाइटबोर्ड पर रोज़मर्रा के उदाहरण देखें और साथ में बोलकर अभ्यास करें!",
                    nextStage = TeacherStage.EXAMPLES,
                    whiteboardRuleEn = "Daily-Life Examples — ${currentTopic.titleEn}",
                    whiteboardRuleHi = "दैनिक जीवन के उदाहरण",
                    whiteboardExamples = exList + currentTopic.dailyLifeExamples.map { it.english },
                    awardStars = 1
                )
            }
            TeacherStage.EXAMPLES -> {
                val ex = currentTopic.exercises.firstOrNull()
                TeacherReply(
                    englishText = "Now it's your turn! ${ex?.prompt ?: "Write or speak one sentence using ${currentTopic.titleEn}."} Take your time—I am listening carefully.",
                    hindiText = "अब आपकी बारी है! ${ex?.hintHi ?: "इस विषय का उपयोग करके एक वाक्य लिखें या माइक से बोलें।"} जल्दी न करें, मैं ध्यान से सुन रही हूँ।",
                    nextStage = TeacherStage.ASK_QUESTION,
                    whiteboardRuleEn = "Your Turn: ${ex?.prompt ?: "Form 1 sentence"}",
                    whiteboardRuleHi = ex?.hintHi ?: "अपना वाक्य बनाएं",
                    whiteboardExamples = listOf("Sample pattern: ${ex?.sampleAnswer ?: currentTopic.examples.firstOrNull()?.english.orEmpty()}"),
                    awardStars = 1
                )
            }
            TeacherStage.ASK_QUESTION, TeacherStage.LISTEN_CORRECT -> {
                val analysis = analyzeGrammar(cleanReply)
                if (analysis.isCorrect) {
                    TeacherReply(
                        englishText = "${performanceCompliment(1.0f, isCorrect = true, context = currentTopic.titleEn)} Your answer '${analysis.correctedSentence}' is accurate! Let me give you a small homework task to lock this in.",
                        hindiText = "शाबाश! आपका वाक्य '${analysis.correctedSentence}' बिल्कुल सही है! अब अभ्यास के लिए एक छोटा गृहकार्य नोट करें।",
                        nextStage = TeacherStage.HOMEWORK,
                        whiteboardRuleEn = "Correct! ${analysis.correctedSentence}",
                        whiteboardRuleHi = analysis.grammarRuleHi,
                        whiteboardExamples = listOf("Homework: ${currentTopic.homeworkEn}"),
                        feedback = analysis,
                        awardStars = 3
                    )
                } else {
                    TeacherReply(
                        englishText = "Thank you for trying, $nameCall! Your answer needs a small correction before we celebrate it: '${analysis.highlightedMistake}' should be '${analysis.correctedSentence}'. ${analysis.whyIncorrectEn} Try typing or speaking the corrected line once!",
                        hindiText = "कोशिश करने के लिए धन्यवाद, $nameCall! इसमें थोड़ा सुधार ज़रूरी है—सही वाक्य: '${analysis.correctedSentence}'। नियम: ${analysis.grammarRuleHi} कृपया एक बार फिर प्रयास करें!",
                        nextStage = TeacherStage.LISTEN_CORRECT,
                        whiteboardRuleEn = "Correction: ${analysis.correctedSentence}",
                        whiteboardRuleHi = analysis.grammarRuleHi,
                        whiteboardExamples = analysis.extraExamples,
                        feedback = analysis,
                        awardStars = 1
                    )
                }
            }
            TeacherStage.HOMEWORK -> TeacherReply(
                englishText = "Fantastic session today, $nameCall! Your homework: ${currentTopic.homeworkEn} Keep practicing—you are becoming more confident every day!",
                hindiText = "आज का सत्र शानदार रहा! गृहकार्य: ${currentTopic.homeworkHi} रोज़ थोड़ा अभ्यास ज़रूर करें!",
                nextStage = TeacherStage.WELCOME,
                whiteboardRuleEn = "Homework Assigned!",
                whiteboardRuleHi = currentTopic.homeworkHi,
                whiteboardExamples = currentTopic.revisionPoints,
                awardStars = 3
            )
        }
    }

    private fun buildFeedback(
        original: String,
        corrected: String,
        highlighted: String,
        category: String,
        whyEn: String,
        ruleHi: String,
        examples: List<String>,
        tryPrompt: String
    ): GrammarMistakeFeedback {
        return GrammarMistakeFeedback(
            isCorrect = false,
            originalSentence = original,
            correctedSentence = corrected,
            highlightedMistake = highlighted,
            mistakeCategory = category,
            whyIncorrectEn = whyEn,
            grammarRuleHi = ruleHi,
            extraExamples = examples,
            tryAgainPrompt = tryPrompt,
            compliment = "Good effort trying! Let's fix this mistake together step by step.",
            isSampleMode = true
        )
    }

    private fun polishCapitalAndPunctuation(text: String): String {
        val clean = text.trim().replace(Regex("\\s+"), " ")
        if (clean.isEmpty()) return clean
        val cap = clean.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        return if (cap.last() in listOf('.', '!', '?')) cap else "$cap."
    }
}
