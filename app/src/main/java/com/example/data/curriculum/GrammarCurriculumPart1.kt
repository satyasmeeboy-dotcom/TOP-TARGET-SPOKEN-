package com.example.data.curriculum

import com.example.model.GrammarExample
import com.example.model.GrammarExercise
import com.example.model.GrammarMcq
import com.example.model.GrammarTopic
import com.example.model.StudentLevel

object GrammarCurriculumPart1 {
    val topics = listOf(
        GrammarTopic(
            id = "parts_of_speech",
            order = 1,
            titleEn = "Parts of Speech",
            titleHi = "शब्द भेद (Parts of Speech)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Every English word belongs to one of 8 families based on how it works in a sentence.",
            explanationHi = "अंग्रेज़ी में हर शब्द का एक रोल होता है। कुल 8 Parts of Speech होते हैं: Noun (संज्ञा), Pronoun (सर्वनाम), Verb (क्रिया), Adjective (विशेषण), Adverb (क्रिया विशेषण), Preposition (संबंधबोधक), Conjunction (समुच्चयबोधक), और Interjection (विस्मयादिबोधक)।",
            keyRules = listOf(
                "Identify what job a word does in the sentence before naming its part of speech.",
                "The same word can act as a Noun or Verb depending on context (e.g., 'water the plants' vs 'drink water')."
            ),
            examples = listOf(
                GrammarExample("Rohan reads a good book daily.", "रोहन रोज़ एक अच्छी किताब पढ़ता है।", "Noun + Verb + Adj + Adverb"),
                GrammarExample("Wow! She spoke very confidently.", "वाह! उसने बहुत आत्मविश्वास से बोला।", "Interjection + Pronoun + Verb + Adverb")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Please give me fresh water.", "कृपया मुझे ताज़ा पानी दें।", "Daily Request"),
                GrammarExample("The bus arrived quickly.", "बस जल्दी आ गई।", "Commute")
            ),
            exercises = listOf(
                GrammarExercise(
                    prompt = "Write one sentence containing a Noun, a Verb, and an Adjective.",
                    hintHi = "एक वाक्य लिखें जिसमें नाम (Noun), काम (Verb) और विशेषता (Adjective) हो।",
                    expectedKeywords = listOf("good", "big", "small", "happy", "new", "hot", "cold", "smart"),
                    sampleAnswer = "My smart friend speaks English."
                )
            ),
            mcqQuestions = listOf(
                GrammarMcq("pos_1", "In 'She runs fast', what is 'fast'?", listOf("Noun", "Adverb", "Preposition", "Pronoun"), 1, "'Fast' describes how she runs (modifies a verb), so it is an Adverb.", "'fast' क्रिया 'runs' की विशेषता बताता है, इसलिए यह Adverb है।"),
                GrammarMcq("pos_2", "Which word is an Interjection?", listOf("Quickly", "Under", "Hurrah!", "Because"), 2, "Hurrah! expresses sudden joy.", "'Hurrah!' अचानक खुशी प्रकट करता है।"),
                GrammarMcq("pos_3", "In 'Give me a pen', 'pen' is a:", listOf("Noun", "Verb", "Adjective", "Conjunction"), 0, "'Pen' names an object, so it is a Noun.", "'Pen' एक वस्तु का नाम है, इसलिए यह Noun है।")
            ),
            homeworkEn = "Pick any 5 objects in your room and write one sentence describing each with an adjective.",
            homeworkHi = "अपने कमरे की 5 चीज़ें चुनें और हर एक के लिए एक विशेषण (Adjective) जोड़कर वाक्य लिखें।",
            revisionPoints = listOf("8 Parts of Speech build every English sentence.", "Context decides whether a word is Noun, Verb, or Adjective.")
        ),
        GrammarTopic(
            id = "noun",
            order = 2,
            titleEn = "Noun",
            titleHi = "संज्ञा (Noun)",
            level = StudentLevel.BEGINNER,
            summaryEn = "A noun is the name of a person, place, thing, quality, or idea.",
            explanationHi = "किसी व्यक्ति, स्थान, वस्तु, गुण या विचार के नाम को Noun (संज्ञा) कहते हैं। मुख्य प्रकार: Proper Noun (व्यक्तिवाचक - India, Delhi), Common Noun (जातिवाचक - boy, city), Collective Noun (समूहवाचक - team, class), Abstract Noun (भाववाचक - honesty, happiness)।",
            keyRules = listOf(
                "Always capitalize Proper Nouns (India, Monday, English).",
                "Uncountable nouns like 'information', 'advice', 'furniture', 'water' do not take '-s' or 'a/an'."
            ),
            examples = listOf(
                GrammarExample("Honesty is the best policy.", "ईमानदारी सबसे अच्छी नीति है।", "Abstract Noun"),
                GrammarExample("Our cricket team won in Mumbai.", "हमारी क्रिकेट टीम मुंबई में जीती।", "Collective + Proper Noun")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("I need some advice about my study.", "मुझे अपनी पढ़ाई के बारे में सलाह चाहिए।", "Uncountable Noun"),
                GrammarExample("The teacher checked our homework.", "शिक्षक ने हमारा गृहकार्य जांचा।", "Common Noun")
            ),
            exercises = listOf(
                GrammarExercise("Write a sentence using the uncountable noun 'advice' or 'information' correctly.", "Uncountable noun 'advice' या 'information' का सही उपयोग करें (बिना 's' के)।", listOf("advice", "information"), "She gave me useful advice.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("noun_1", "Which sentence is correct?", listOf("He gave me many advices.", "He gave me some advice.", "He gave me an advice.", "He gave me two advices."), 1, "'Advice' is uncountable; use 'some advice' or 'a piece of advice'.", "'Advice' को गिना नहीं जा सकता (Uncountable), इसलिए 'some advice' सही है।"),
                GrammarMcq("noun_2", "'Team' and 'Family' are examples of:", listOf("Proper Noun", "Collective Noun", "Abstract Noun", "Material Noun"), 1, "They refer to groups acting as one unit.", "ये समूह के नाम हैं, इसलिए Collective Noun हैं।"),
                GrammarMcq("noun_3", "Which word is a Proper Noun?", listOf("river", "city", "Ganga", "book"), 2, "Ganga is the specific name of a river.", "गंगा एक विशेष नदी का नाम है।")
            ),
            homeworkEn = "List 5 Countable Nouns and 5 Uncountable Nouns from your kitchen.",
            homeworkHi = "अपनी रसोई से 5 Countable और 5 Uncountable Nouns की सूची बनाएं।",
            revisionPoints = listOf("Proper nouns start with a capital letter.", "Never write 'advices' or 'informations'.")
        ),
        GrammarTopic(
            id = "pronoun",
            order = 3,
            titleEn = "Pronoun",
            titleHi = "सर्वनाम (Pronoun)",
            level = StudentLevel.BEGINNER,
            summaryEn = "A pronoun replaces a noun so we do not repeat names again and again.",
            explanationHi = "संज्ञा (Noun) के स्थान पर प्रयोग होने वाले शब्द को Pronoun कहते हैं, ताकि नाम बार-बार न दोहराना पड़े। जैसे: I, we, you, he, she, it, they, me, him, her, my, mine, myself.",
            keyRules = listOf(
                "Subject pronouns (I, he, she, they) do the action; Object pronouns (me, him, her, them) receive it.",
                "Use 'between you and me' (never 'between you and I')."
            ),
            examples = listOf(
                GrammarExample("Priya is smart. She learns fast.", "प्रिया होशियार है। वह जल्दी सीखती है।", "Subject Pronoun"),
                GrammarExample("This seat is for you and me.", "यह सीट तुम्हारे और मेरे लिए है।", "Object Pronoun")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Everyone did their best today.", "आज हर किसी ने अपना सर्वश्रेष्ठ किया।", "Indefinite Pronoun"),
                GrammarExample("I taught myself new English words.", "मैंने खुद को नए अंग्रेज़ी शब्द सिखाए।", "Reflexive Pronoun")
            ),
            exercises = listOf(
                GrammarExercise("Rewrite: 'Rahul likes Rahul's teacher because Rahul's teacher helps Rahul.' using pronouns.", "नाम की जगह he / his / him का प्रयोग करके वाक्य दोबारा लिखें।", listOf("he", "his", "him"), "Rahul likes his teacher because she helps him.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("pro_1", "Choose the correct option: 'Please share this between you and ___.'", listOf("I", "me", "my", "mine"), 1, "After prepositions like 'between', use the object pronoun 'me'.", "'between' के बाद हमेशा object pronoun 'me' आता है।"),
                GrammarMcq("pro_2", "'This book is ___.'", listOf("my", "me", "mine", "I"), 2, "'Mine' is a possessive pronoun used without a noun after it.", "वाक्य के अंत में बिना संज्ञा के 'mine' आता है।"),
                GrammarMcq("pro_3", "Which pronoun replaces 'my friends and I'?", listOf("They", "We", "Us", "Them"), 1, "'My friends and I' as a subject becomes 'We'.", "जब आप खुद भी शामिल हों, तो 'We' प्रयोग होता है।")
            ),
            homeworkEn = "Write 4 sentences using 'he', 'she', 'they', and 'ourselves'.",
            homeworkHi = "'he', 'she', 'they', और 'ourselves' का प्रयोग करके 4 वाक्य लिखें।",
            revisionPoints = listOf("Subject = I/He/She/We/They", "Object after verb/preposition = me/him/her/us/them")
        ),
        GrammarTopic(
            id = "verb",
            order = 4,
            titleEn = "Verb",
            titleHi = "क्रिया (Verb)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Verbs show action, state, or possession — no complete English sentence exists without a verb.",
            explanationHi = "जिस शब्द से किसी काम का होना या करना पाया जाए, उसे Verb (क्रिया) कहते हैं। बिना Verb के कोई भी पूर्ण वाक्य नहीं बन सकता। मुख्य रूप: V1 (Base - go), V2 (Past - went), V3 (Past Participle - gone), V4 (-ing - going)।",
            keyRules = listOf(
                "Learn irregular verb forms (go-went-gone, write-wrote-written, speak-spoke-spoken).",
                "Helping verbs (is, am, are, was, were, do, does, did, have, has) support the main verb."
            ),
            examples = listOf(
                GrammarExample("Students practice English every morning.", "छात्र हर सुबह अंग्रेज़ी का अभ्यास करते हैं।", "Action Verb"),
                GrammarExample("She has written a neat letter.", "उसने एक साफ पत्र लिखा है।", "Helping + Main Verb")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("I woke up early and drank warm water.", "मैं जल्दी उठा और गुनगुना पानी पिया।", "Past Verbs V2"),
                GrammarExample("Are you listening carefully?", "क्या आप ध्यान से सुन रहे हैं?", "Helping + V4")
            ),
            exercises = listOf(
                GrammarExercise("Write a sentence using the past form (V2) of 'speak' or 'write'.", "'speak' (spoke) या 'write' (wrote) के V2 रूप से एक वाक्य लिखें।", listOf("spoke", "wrote"), "I spoke English confidently today.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("verb_1", "What is the V3 (Past Participle) of 'speak'?", listOf("speaked", "spoke", "spoken", "speaking"), 2, "Speak -> Spoke -> Spoken.", "Speak के तीन रूप: speak - spoke - spoken."),
                GrammarMcq("verb_2", "In 'She is a doctor', what is the verb?", listOf("She", "is", "a", "doctor"), 1, "'is' is a state-of-being linking verb.", "'is' यहाँ होने (state) को दर्शाने वाली क्रिया है।"),
                GrammarMcq("verb_3", "Choose the right verb: 'Did you ___ your lunch?'", listOf("ate", "eaten", "eat", "eating"), 2, "After 'did', always use base verb V1 ('eat').", "'Did' के बाद हमेशा क्रिया का पहला रूप (V1) 'eat' आता है।")
            ),
            homeworkEn = "Memorize V1-V2-V3 of 10 daily verbs (go, come, eat, speak, write, read, see, take, give, make).",
            homeworkHi = "रोज़मर्रा की 10 क्रियाओं के V1-V2-V3 रूप याद करें और लिखें।",
            revisionPoints = listOf("After did / didn't / does / doesn't, always use V1.", "After has / have / had, always use V3.")
        ),
        GrammarTopic(
            id = "adjective",
            order = 5,
            titleEn = "Adjective",
            titleHi = "विशेषण (Adjective)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Adjectives describe or modify nouns and pronouns by telling quality, quantity, or comparison.",
            explanationHi = "जो शब्द Noun या Pronoun की विशेषता (गुण, दोष, संख्या, आकार या रंग) बताते हैं, उन्हें Adjective कहते हैं। तुलना के लिए 3 Degrees होती हैं: Positive (tall), Comparative (taller), Superlative (tallest)।",
            keyRules = listOf(
                "Use '-er / more' + 'than' to compare two things (better than, more useful than).",
                "Use 'the' + '-est / most' for superlatives (the best, the most interesting)."
            ),
            examples = listOf(
                GrammarExample("This is a very useful lesson.", "यह एक बहुत उपयोगी पाठ है।", "Descriptive Adjective"),
                GrammarExample("English is easier than you think.", "अंग्रेज़ी आपके सोचने से ज़्यादा आसान है।", "Comparative")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Today is hotter than yesterday.", "आज कल से ज़्यादा गर्मी है।", "Weather"),
                GrammarExample("She is the most hardworking student.", "वह सबसे मेहनती छात्रा है।", "Superlative")
            ),
            exercises = listOf(
                GrammarExercise("Write a comparison sentence using 'better than' or 'more confident than'.", "'better than' या 'more confident than' का उपयोग कर एक वाक्य लिखें।", listOf("better than", "more confident than"), "My speaking is better than last month.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("adj_1", "Choose the correct comparative: 'Gold is ___ than silver.'", listOf("expensive", "more expensive", "most expensive", "expensiver"), 1, "Long adjectives take 'more' for comparative degree.", "बड़े शब्दों के लिए 'more + adjective' का प्रयोग होता है।"),
                GrammarMcq("adj_2", "'He is ___ best player in the school.'", listOf("a", "an", "the", "no article"), 2, "Always use 'the' before superlative adjectives like 'best'.", "Superlative degree (best) से पहले हमेशा 'the' आता है।"),
                GrammarMcq("adj_3", "Identify the adjective: 'The green board is clean.'", listOf("board", "is", "green", "the"), 2, "'Green' and 'clean' describe the noun 'board'.", "'Green' बोर्ड का रंग (विशेषता) बताता है।")
            ),
            homeworkEn = "Write Positive, Comparative, and Superlative forms of: good, bad, happy, smart, easy.",
            homeworkHi = "good, bad, happy, smart, easy की तीनों Degrees लिखें।",
            revisionPoints = listOf("Never write 'more better' — 'better' is already comparative.", "Superlative takes 'the' before it.")
        ),
        GrammarTopic(
            id = "adverb",
            order = 6,
            titleEn = "Adverb",
            titleHi = "क्रिया विशेषण (Adverb)",
            level = StudentLevel.ELEMENTARY,
            summaryEn = "Adverbs describe how, when, where, or how often an action happens.",
            explanationHi = "जो शब्द Verb (क्रिया), Adjective या किसी अन्य Adverb की विशेषता बताते हैं—जैसे काम कैसे (carefully), कब (yesterday), कहाँ (here) या कितनी बार (always) हुआ—उन्हें Adverb कहते हैं।",
            keyRules = listOf(
                "Many adverbs of manner end in '-ly' (slowly, clearly, politely).",
                "Frequency adverbs (always, usually, often, never) usually go before the main verb but after 'be' (is/am/are)."
            ),
            examples = listOf(
                GrammarExample("Please speak clearly and slowly.", "कृपया स्पष्ट और धीरे बोलें।", "Adverb of Manner"),
                GrammarExample("I always revise my vocabulary at night.", "मैं हमेशा रात में अपनी शब्दावली दोहराता हूँ।", "Adverb of Frequency")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("She answered the question politely.", "उसने विनम्रता से प्रश्न का उत्तर दिया।", "Classroom"),
                GrammarExample("We will meet tomorrow outside.", "हम कल बाहर मिलेंगे।", "Time & Place")
            ),
            exercises = listOf(
                GrammarExercise("Write a sentence using 'always', 'daily', or 'carefully'.", "'always', 'daily' या 'carefully' शब्द से एक वाक्य बनाएं।", listOf("always", "daily", "carefully"), "I always listen carefully in class.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("adv_1", "Which sentence is correct?", listOf("He drives careful.", "He drives carefully.", "He careful drives.", "He drives care."), 1, "We need an adverb ('carefully') to describe how he drives.", "क्रिया 'drives' की विशेषता बताने के लिए adverb 'carefully' चाहिए।"),
                GrammarMcq("adv_2", "Where does 'never' fit best?", listOf("I never am late.", "I am never late.", "Never I am late.", "I am late never."), 1, "Frequency adverbs go after 'am/is/are'.", "am/is/are के बाद 'never' आता है।"),
                GrammarMcq("adv_3", "'Yesterday' is an adverb of:", listOf("Place", "Manner", "Time", "Degree"), 2, "'Yesterday' answers 'when?'.", "'Yesterday' समय (कब?) बताता है।")
            ),
            homeworkEn = "Change these adjectives to adverbs and use them in sentences: polite, loud, neat, brave.",
            homeworkHi = "polite, loud, neat, brave को Adverb में बदलकर वाक्य बनाएं।",
            revisionPoints = listOf("Adjectives describe nouns; Adverbs describe verbs/adjectives.", "'Good' is an adjective, 'Well' is its adverb.")
        ),
        GrammarTopic(
            id = "preposition",
            order = 7,
            titleEn = "Preposition",
            titleHi = "संबंधबोधक (Preposition)",
            level = StudentLevel.ELEMENTARY,
            summaryEn = "Prepositions show position, time, and direction (in, on, at, by, for, since, between, among).",
            explanationHi = "Preposition संज्ञा या सर्वनाम से पहले आकर वाक्य के दूसरे शब्दों से उसका संबंध (समय, स्थान या दिशा) बताते हैं। जैसे: At (निश्चित समय/छोटे स्थान), On (दिन/तारीख/सतह), In (महीना/साल/बड़ा स्थान/अंदर)।",
            keyRules = listOf(
                "Time: AT 7 PM, ON Monday / 15th August, IN May / 2026 / the morning.",
                "Never add 'about' after 'discuss' (say 'discuss the topic', not 'discuss about the topic')."
            ),
            examples = listOf(
                GrammarExample("The class starts at 8 AM on Monday.", "कक्षा सोमवार को सुबह 8 बजे शुरू होती है।", "Time Prepositions"),
                GrammarExample("The notebook is on the table in my room.", "कॉपियाँ मेरे कमरे में मेज़ पर हैं।", "Place Prepositions")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("I have been learning English for two months.", "मैं दो महीनों से अंग्रेज़ी सीख रहा हूँ।", "For vs Since"),
                GrammarExample("Let us discuss the lesson.", "आइए पाठ पर चर्चा करें।", "No extra 'about'")
            ),
            exercises = listOf(
                GrammarExercise("Write a sentence using 'on Monday' or 'at 9 o'clock' or 'since morning'.", "'on Monday', 'at 9 o'clock' या 'since morning' से एक वाक्य लिखें।", listOf("on monday", "at ", "since"), "I wake up at 6 AM every day.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("prep_1", "We have a test ___ Friday.", listOf("in", "at", "on", "to"), 2, "Use 'on' before days of the week.", "सप्ताह के दिनों (Friday) से पहले 'on' आता है।"),
                GrammarMcq("prep_2", "She has lived here ___ 2022.", listOf("for", "since", "from", "by"), 1, "Use 'since' for a starting point in time (2022).", "निश्चित शुरुआती समय (2022) के लिए 'since' लगता है।"),
                GrammarMcq("prep_3", "Choose the correct sentence:", listOf("Let's discuss about grammar.", "Let's discuss grammar.", "Let's discuss on grammar.", "Let's discuss for grammar."), 1, "'Discuss' directly takes its object without 'about'.", "'Discuss' के साथ 'about' नहीं लगता।")
            ),
            homeworkEn = "Write 5 sentences using IN, ON, AT, FOR, and SINCE.",
            homeworkHi = "IN, ON, AT, FOR और SINCE का उपयोग करते हुए 5 वाक्य लिखें।",
            revisionPoints = listOf("AT = exact clock time, ON = days/dates, IN = months/years/seasons.", "FOR = duration (2 hours), SINCE = start point (morning, 2020).")
        ),
        GrammarTopic(
            id = "conjunction",
            order = 8,
            titleEn = "Conjunction",
            titleHi = "समुच्चयबोधक (Conjunction)",
            level = StudentLevel.ELEMENTARY,
            summaryEn = "Conjunctions connect words, phrases, or clauses smoothly (and, but, because, although, so).",
            explanationHi = "दो शब्दों, वाक्यांशों या वाक्यों को जोड़ने वाले शब्दों को Conjunction कहते हैं। जैसे: and (और), but (लेकिन), or (या), because (क्योंकि), so (इसलिए), although (हालाँकि)।",
            keyRules = listOf(
                "Never use 'Although' and 'but' together in the same sentence — use 'Although ..., ...' or 'Although ..., yet ...'.",
                "'Because' shows reason; 'So' shows result."
            ),
            examples = listOf(
                GrammarExample("I practiced hard, so I spoke well.", "मैंने कड़ी मेहनत की, इसलिए मैंने अच्छा बोला।", "Result"),
                GrammarExample("Although it rained, we attended the class.", "हालाँकि बारिश हुई, फिर भी हम कक्षा में गए।", "Contrast")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("I like tea and coffee, but I prefer milk.", "मुझे चाय और कॉफी पसंद है, लेकिन मैं दूध पसंद करता हूँ।", "Daily Choice"),
                GrammarExample("Stay focused because practice builds fluency.", "ध्यान केंद्रित रखें क्योंकि अभ्यास से धाराप्रवाह बोलना आता है।", "Reason")
            ),
            exercises = listOf(
                GrammarExercise("Join two ideas using 'because' or 'although'.", "'because' या 'although' से दो वाक्यों को जोड़कर एक वाक्य बनाएं।", listOf("because", "although"), "I study daily because I want to speak fluently.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("conj_1", "Which sentence is grammatically right?", listOf("Although he was tired, but he studied.", "Although he was tired, he studied.", "Although he was tired, so he studied.", "Because he was tired, therefore he studied."), 1, "Do not pair 'Although' with 'but'.", "'Although' के साथ 'but' का प्रयोग नहीं होता।"),
                GrammarMcq("conj_2", "'He did not come ___ he was unwell.'", listOf("so", "but", "because", "and"), 2, "'Because' introduces the reason.", "कारण बताने के लिए 'because' का उपयोग होता है।"),
                GrammarMcq("conj_3", "Choose the best pair: 'Either you apologize ___ improve your habit.'", listOf("and", "nor", "or", "but"), 2, "'Either ... or' is a correlative conjunction pair.", "'Either' की जोड़ी हमेशा 'or' के साथ होती है।")
            ),
            homeworkEn = "Combine 3 pairs of short sentences from your diary using 'and', 'but', and 'because'.",
            homeworkHi = "अपनी डायरी के 3 छोटे वाक्यों को 'and', 'but', 'because' से जोड़ें।",
            revisionPoints = listOf("Although + Subject + Verb, Subject + Verb (no 'but').", "Neither...nor / Either...or / Not only...but also.")
        ),
        GrammarTopic(
            id = "interjection",
            order = 9,
            titleEn = "Interjection",
            titleHi = "विस्मयादिबोधक (Interjection)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Interjections express sudden feelings like joy, surprise, praise, or regret.",
            explanationHi = "वे शब्द जो अचानक खुशी, आश्चर्य, प्रशंसा या दुःख की भावना व्यक्त करते हैं, Interjection कहलाते हैं। इनके बाद अक्सर विस्मयादिबोधक चिह्न (!) लगता है। जैसे: Bravo!, Wow!, Alas!, Oops!, Hurrah!",
            keyRules = listOf(
                "Use an exclamation mark (!) after strong interjections like 'Bravo!' or 'Wow!'.",
                "Use polite conversational interjections in spoken English ('Oh, really!', 'Great!', 'Excuse me!')."
            ),
            examples = listOf(
                GrammarExample("Bravo! Your pronunciation is crystal clear.", "शाबाश! आपका उच्चारण बिल्कुल स्पष्ट है।", "Praise"),
                GrammarExample("Oops! I forgot to add a full stop.", "अरे! मैं पूर्ण विराम लगाना भूल गया।", "Mild Mistake")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Wow! What a clean presentation!", "वाह! कितनी शानदार प्रस्तुति है!", "Appreciation"),
                GrammarExample("Excuse me! Could you repeat that?", "क्षमा करें! क्या आप वह दोहरा सकते हैं?", "Polite Spoken")
            ),
            exercises = listOf(
                GrammarExercise("Write a congratulatory sentence starting with 'Bravo!' or 'Wow!'.", "'Bravo!' या 'Wow!' से शुरू होने वाला प्रशंसा का वाक्य लिखें।", listOf("bravo", "wow", "hurrah", "great"), "Bravo! You answered every question right.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("int_1", "Which interjection fits when praising someone?", listOf("Alas!", "Bravo!", "Ouch!", "Oops!"), 1, "'Bravo!' means well done.", "'Bravo!' का उपयोग शाबाशी देने के लिए होता है।"),
                GrammarMcq("int_2", "Which punctuation usually follows a strong interjection?", listOf("?", "!", ";", ":"), 1, "An exclamation mark (!) follows strong feelings.", "विस्मयादिबोधक शब्दों के बाद '!' लगता है।"),
                GrammarMcq("int_3", "'Oops!' is used when:", listOf("You win a prize", "You make a small slip/mistake", "You feel sad about a tragedy", "You greet a teacher"), 1, "'Oops!' expresses a small accidental mistake.", "छोटी गलती होने पर 'Oops!' बोलते हैं।")
            ),
            homeworkEn = "Practice saying 4 polite expressions aloud: 'Wow!', 'Really?', 'Excuse me!', 'Well done!'.",
            homeworkHi = "'Wow!', 'Really?', 'Excuse me!', 'Well done!' को ज़ोर से बोलकर अभ्यास करें।",
            revisionPoints = listOf("Interjections add natural emotion to spoken English.", "Follow strong interjections with '!'.")
        )
    )
}
