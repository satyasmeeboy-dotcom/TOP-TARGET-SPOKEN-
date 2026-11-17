package com.example.data.curriculum

import com.example.model.GrammarExample
import com.example.model.GrammarExercise
import com.example.model.GrammarMcq
import com.example.model.GrammarTopic
import com.example.model.StudentLevel

object GrammarCurriculumPart2 {
    val topics = listOf(
        GrammarTopic(
            id = "articles",
            order = 10,
            titleEn = "Articles (A, An, The)",
            titleHi = "आर्टिकल्स (A, An, The)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Master when to use 'a', 'an' (based on vowel SOUND, not letter), and 'the' (specific).",
            explanationHi = "A और An का उपयोग एकवचन (Singular) गिनने योग्य संज्ञा के साथ होता है। ध्यान रखें: स्वर (अ, आ, इ, ई, उ, ऊ, ए, ऐ, ओ, औ) की ध्वनि से पहले 'An' (जैसे an hour, an honest boy) और व्यंजन की ध्वनि से पहले 'A' (जैसे a university, a book) लगता है। 'The' किसी विशेष चीज़, नदी, प्रसिद्ध पुस्तक या Superlative से पहले आता है।",
            keyRules = listOf(
                "Sound decides A vs An: 'an honest man' (silent h -> o sound), 'a university' (yu sound).",
                "Use 'the' when both speaker and listener know which exact thing is meant."
            ),
            examples = listOf(
                GrammarExample("She is an honest student at a university.", "वह एक विश्वविद्यालय में एक ईमानदार छात्रा है।", "Sound Rule"),
                GrammarExample("The sun rises in the east.", "सूर्य पूर्व में उगता है।", "Unique Things")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Wait for an hour, please.", "कृपया एक घंटे प्रतीक्षा करें।", "Silent 'h'"),
                GrammarExample("Close the door behind you.", "अपने पीछे का दरवाज़ा बंद कर दें।", "Specific Noun")
            ),
            exercises = listOf(
                GrammarExercise("Write a sentence using 'an hour' or 'a university' or 'the sun'.", "'an hour', 'a university' या 'the sun' से एक सही वाक्य लिखें।", listOf("an hour", "a university", "the sun", "an honest"), "I practice English for an hour daily.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("art_1", "He is ___ honest officer.", listOf("a", "an", "the", "no article"), 1, "'Honest' begins with a vowel sound ('o'), so use 'an'.", "'Honest' में 'h' silent है, उच्चारण स्वर 'ऑ' से शुरू होता है, इसलिए 'an' आएगा।"),
                GrammarMcq("art_2", "My sister studies at ___ university.", listOf("a", "an", "the", "no article"), 0, "'University' starts with a 'yoo' consonant sound, so use 'a'.", "'University' का उच्चारण 'य' (व्यंजन) से शुरू होता है, इसलिए 'a' लगेगा।"),
                GrammarMcq("art_3", "___ Ganga is a sacred river.", listOf("A", "An", "The", "No article"), 2, "Use 'The' before names of rivers.", "नदियों के नाम से पहले 'The' आता है।")
            ),
            homeworkEn = "Fill 10 nouns around you with 'a', 'an', or 'the' and check their starting sound.",
            homeworkHi = "अपने आस-पास की 10 चीज़ों के साथ ध्वनि के आधार पर a, an या the लगाएं।",
            revisionPoints = listOf("Check SOUND, not spelling, for A vs An.", "Do not use 'a/an' with plural or uncountable nouns.")
        ),
        GrammarTopic(
            id = "tenses",
            order = 11,
            titleEn = "All 12 Tenses",
            titleHi = "सभी 12 काल (All 12 Tenses)",
            level = StudentLevel.ELEMENTARY,
            summaryEn = "Understand Present, Past, and Future across Indefinite, Continuous, Perfect, and Perfect Continuous.",
            explanationHi = "Tense समय और कार्य की स्थिति बताते हैं। 3 मुख्य काल (Present, Past, Future) और हर एक के 4 रूप होते हैं:\n1. Simple/Indefinite: आदत या सामान्य तथ्य (I speak / I spoke / I will speak)\n2. Continuous: काम जारी है (am/is/are + V-ing | was/were + V-ing | will be + V-ing)\n3. Perfect: काम पूरा हो चुका है (has/have + V3 | had + V3 | will have + V3)\n4. Perfect Continuous: समय से जारी काम (has/have been + V-ing + since/for)।",
            keyRules = listOf(
                "Simple Present: With He/She/It, add -s/-es to the verb (She speaks; NOT She speak).",
                "Simple Past: Use V2 for affirmative ('I went yesterday'), and 'did not + V1' for negative ('I did not go').",
                "Present Perfect Continuous uses 'since' (point of time) and 'for' (period of time)."
            ),
            examples = listOf(
                GrammarExample("Present Simple: She practices English every day.", "वह रोज़ अंग्रेज़ी का अभ्यास करती है।", "Daily Habit"),
                GrammarExample("Past Simple: We visited Jaipur last Sunday.", "हम पिछले रविवार जयपुर गए थे।", "Completed Past"),
                GrammarExample("Present Perfect: I have finished my homework.", "मैंने अपना गृहकार्य पूरा कर लिया है।", "Recent Completion"),
                GrammarExample("Future Continuous: Tomorrow at 5 PM, I will be speaking on stage.", "कल शाम 5 बजे, मैं मंच पर बोल रहा हूँगा।", "Future In-Progress")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("It has been raining since morning.", "सुबह से बारिश हो रही है।", "Present Perfect Continuous"),
                GrammarExample("The train had left before we reached the station.", "हमारे स्टेशन पहुँचने से पहले ट्रेन जा चुकी थी।", "Past Perfect")
            ),
            exercises = listOf(
                GrammarExercise("Convert 'I go to school' into Simple Past and Present Continuous.", "'I go to school' को Simple Past और Present Continuous में बदलकर लिखें।", listOf("went", "am going"), "I went to school yesterday. / I am going to school now.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("ten_1", "She ___ to the market yesterday.", listOf("go", "goes", "went", "gone"), 2, "'Yesterday' indicates Simple Past (V2 -> 'went').", "'Yesterday' बीते समय का संकेत है, इसलिए V2 'went' सही है।"),
                GrammarMcq("ten_2", "They ___ English for three months.", listOf("are learning", "have been learning", "learns", "was learning"), 1, "Action started in the past and continuing with 'for three months' uses Present Perfect Continuous.", "'for three months' के साथ 'have been learning' सही है।"),
                GrammarMcq("ten_3", "He ___ not go to office on Sundays.", listOf("do", "does", "is", "has"), 1, "With singular third person 'He' in Simple Present negative, use 'does not + V1'.", "'He' के साथ Simple Present के नकारात्मक वाक्य में 'does not' आता है।"),
                GrammarMcq("ten_4", "By next year, I ___ completed this course.", listOf("will have", "will be", "have", "am"), 0, "'By next year' signals Future Perfect ('will have + V3').", "भविष्य के निश्चित समय तक कार्य पूर्ण होने पर 'will have + V3' लगता है।")
            ),
            homeworkEn = "Take the sentence 'I learn English' and write it in all 12 Tenses in your notebook.",
            homeworkHi = "'I learn English' वाक्य को सभी 12 Tenses में लिखने का अभ्यास करें।",
            revisionPoints = listOf("He/She/It + V1(s/es) in Present Simple.", "Yesterday/Last week = V2 (Past Simple).", "Since/For + ongoing action = has/have been + V-ing.")
        ),
        GrammarTopic(
            id = "modals",
            order = 12,
            titleEn = "Modals",
            titleHi = "मोडल्स (Can, Could, Should, Must, May)",
            level = StudentLevel.ELEMENTARY,
            summaryEn = "Modal verbs express ability, permission, polite request, advice, and obligation.",
            explanationHi = "Modals सहायक क्रियाएं हैं जो क्षमता (can/could), अनुमति या संभावना (may/might), सलाह (should) और अनिवार्यता (must) बताती हैं। नियम: हर Modal के ठीक बाद हमेशा V1 (Base Verb) आता है।",
            keyRules = listOf(
                "Always use V1 after modals: 'You should study' (NEVER 'You should to study' or 'should studied').",
                "Use 'Could you please...' or 'May I...' for polite spoken English."
            ),
            examples = listOf(
                GrammarExample("You can speak fluent English with daily practice.", "आप रोज़ अभ्यास से धाराप्रवाह अंग्रेज़ी बोल सकते हैं।", "Ability"),
                GrammarExample("Could you please speak a bit slowly?", "क्या आप कृपया थोड़ा धीरे बोल सकते हैं?", "Polite Request")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("We must respect everyone's privacy.", "हमें सभी की गोपनीयता का सम्मान करना ही चाहिए।", "Strong Duty"),
                GrammarExample("It might rain this evening.", "आज शाम बारिश हो सकती है।", "Possibility")
            ),
            exercises = listOf(
                GrammarExercise("Write a polite request using 'Could you' or 'May I'.", "'Could you' या 'May I' से एक विनम्र अनुरोध लिखें।", listOf("could you", "may i"), "Could you please check my sentence?")
            ),
            mcqQuestions = listOf(
                GrammarMcq("mod_1", "Which sentence is correct?", listOf("You must to follow rules.", "You must following rules.", "You must follow rules.", "You must followed rules."), 2, "Never place 'to' or '-ed/-ing' directly after 'must'.", "'must' के बाद सीधे V1 'follow' आता है।"),
                GrammarMcq("mod_2", "___ I come in, Sir?", listOf("May", "Must", "Should", "Would"), 0, "'May I...' is standard polite permission.", "अनुमति मांगने के लिए 'May I' सबसे विनम्र रूप है।"),
                GrammarMcq("mod_3", "You look tired. You ___ take some rest.", listOf("should", "can't", "needn't", "might not"), 0, "'Should' gives helpful advice.", "सलाह देने के लिए 'should' का प्रयोग होता है।")
            ),
            homeworkEn = "Write 5 sentences using Can, Could, Should, Must, and May for daily situations.",
            homeworkHi = "Can, Could, Should, Must और May से रोज़मर्रा के 5 वाक्य लिखें।",
            revisionPoints = listOf("Modal + V1 (no 'to', no '-s', no '-ing').", "'Could' and 'Would' make requests sound polite.")
        ),
        GrammarTopic(
            id = "voice",
            order = 13,
            titleEn = "Active & Passive Voice",
            titleHi = "कर्तृवाच्य और कर्मवाच्य (Active & Passive Voice)",
            level = StudentLevel.INTERMEDIATE,
            summaryEn = "Active focuses on who does the action; Passive focuses on what receives the action.",
            explanationHi = "Active Voice में कर्ता (Subject) मुख्य होता है (Teacher teaches English)। Passive Voice में कर्म (Object) मुख्य होता है और हमेशा 'be + V3' का उपयोग होता है (English is taught by the teacher)।",
            keyRules = listOf(
                "Passive structure always contains a form of 'be' (is/am/are/was/were/be/been/being) + V3.",
                "Subject becomes object with 'by', and object moves to the front."
            ),
            examples = listOf(
                GrammarExample("Active: Students speak English here. -> Passive: English is spoken here.", "यहाँ अंग्रेज़ी बोली जाती है।", "Present Simple Passive"),
                GrammarExample("Active: She wrote a letter. -> Passive: A letter was written by her.", "उसके द्वारा एक पत्र लिखा गया।", "Past Simple Passive")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("The classroom is cleaned every morning.", "कक्षा हर सुबह साफ की जाती है।", "Process"),
                GrammarExample("Your data is stored safely on your device.", "आपका डेटा आपके डिवाइस पर सुरक्षित रखा जाता है।", "Privacy Notice")
            ),
            exercises = listOf(
                GrammarExercise("Change to Passive: 'Rohan helps Priya.'", "'Rohan helps Priya.' को Passive Voice में बदलें (Priya is ...).", listOf("is helped by"), "Priya is helped by Rohan.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("voi_1", "Passive of 'He writes a poem' is:", listOf("A poem is wrote by him.", "A poem is written by him.", "A poem was written by him.", "A poem written by him."), 1, "Present Simple passive uses is/am/are + V3 ('written').", "Present Simple Passive में 'is + V3 (written)' आता है।"),
                GrammarMcq("voi_2", "Passive of 'They built this school' is:", listOf("This school is built by them.", "This school was built by them.", "This school were built by them.", "This school built by them."), 1, "Past Simple singular takes 'was + V3'.", "Past Simple एकवचन में 'was + built' लगेगा।"),
                GrammarMcq("voi_3", "Every Passive sentence must use which verb form?", listOf("V1", "V2", "V3 (Past Participle)", "V-ing"), 2, "Passive always uses V3 after a 'be' verb.", "Passive Voice में हमेशा V3 का प्रयोग होता है।")
            ),
            homeworkEn = "Convert 4 active sentences into passive voice focusing on 'is/was + V3'.",
            homeworkHi = "4 Active वाक्यों को 'is/was + V3' का उपयोग कर Passive में बदलें।",
            revisionPoints = listOf("Passive = Object + helping verb + V3 + (by + Subject).", "News headlines and rules often use Passive Voice.")
        ),
        GrammarTopic(
            id = "narration",
            order = 14,
            titleEn = "Direct & Indirect Speech",
            titleHi = "प्रत्यक्ष और अप्रत्यक्ष कथन (Direct & Indirect Speech)",
            level = StudentLevel.INTERMEDIATE,
            summaryEn = "Report what someone said accurately by adjusting quotation marks, pronouns, and tense.",
            explanationHi = "किसी की कही हुई बात को ज्यों का त्यों कहना Direct Speech (\"...\") है, और उसे अपने शब्दों में बताना Indirect Speech है। जब Reporting Verb Past में हो ('He said'), तो अंदर का Tense भी Past में बदल जाता है (am/is -> was, will -> would, can -> could)।",
            keyRules = listOf(
                "Remove quotation marks and use 'that' for statements, 'if/whether' for yes-no questions.",
                "Universal truths do NOT change tense ('The teacher said that the earth revolves around the sun')."
            ),
            examples = listOf(
                GrammarExample("Direct: He said, \"I am happy.\" -> Indirect: He said that he was happy.", "उसने कहा कि वह खुश था।", "Statement"),
                GrammarExample("Direct: She asked, \"Are you ready?\" -> Indirect: She asked if I was ready.", "उसने पूछा कि क्या मैं तैयार था।", "Question")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("My teacher told me that practice makes progress.", "मेरे शिक्षक ने मुझे बताया कि अभ्यास से प्रगति होती है।", "Universal Truth"),
                GrammarExample("She said she would call tomorrow.", "उसने कहा कि वह अगले दिन फोन करेगी।", "Conversation")
            ),
            exercises = listOf(
                GrammarExercise("Convert to Indirect: 'She said, \"I can speak English.\"'", "इसे Indirect में बदलें: She said that she ...", listOf("could speak"), "She said that she could speak English.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("nar_1", "Indirect of 'Ravi said, \"I am studying.\"' is:", listOf("Ravi said that he is studying.", "Ravi said that he was studying.", "Ravi said that I was studying.", "Ravi says that he was studying."), 1, "'am studying' shifts to 'was studying' and 'I' becomes 'he'.", "'said' के कारण 'am' -> 'was' और 'I' -> 'he' हो जाता है।"),
                GrammarMcq("nar_2", "In Indirect questions, 'said to' changes to:", listOf("told", "asked", "said", "ordered"), 1, "Questions use 'asked' or 'enquired'.", "प्रश्नवाचक वाक्यों में 'asked' आता है।"),
                GrammarMcq("nar_3", "'tomorrow' in Direct Speech changes to:", listOf("yesterday", "the next day", "today", "last day"), 1, "'Tomorrow' becomes 'the next day' or 'the following day'.", "'tomorrow' बदलकर 'the next day' हो जाता है।")
            ),
            homeworkEn = "Report 3 things your friend or teacher said today using Indirect Speech.",
            homeworkHi = "आज आपके दोस्त या शिक्षक द्वारा कही गई 3 बातें Indirect Speech में लिखें।",
            revisionPoints = listOf("said + that + past tense shift.", "Universal truths keep Present Simple.")
        ),
        GrammarTopic(
            id = "subject_verb_agreement",
            order = 15,
            titleEn = "Subject-Verb Agreement",
            titleHi = "कर्ता-क्रिया मेल (Subject-Verb Agreement)",
            level = StudentLevel.INTERMEDIATE,
            summaryEn = "A singular subject must take a singular verb, and a plural subject must take a plural verb.",
            explanationHi = "वाक्य का Subject जैसा हो (एकवचन या बहुवचन), Verb भी वैसी ही होनी चाहिए। एकवचन Subject (He, She, Riya, Everyone, Each) के साथ एकवचन क्रिया (is, was, has, goes) लगती है; बहुवचन Subject (They, We, Boys) के साथ बहुवचन क्रिया (are, were, have, go) लगती है।",
            keyRules = listOf(
                "Each, Everyone, Everybody, Nobody, Someone ALWAYS take singular verbs (Everyone IS ready).",
                "With 'Either...or' and 'Neither...nor', the verb agrees with the nearest subject."
            ),
            examples = listOf(
                GrammarExample("Each of the students has a notebook.", "प्रत्येक छात्र के पास एक कॉपी है।", "Each = Singular"),
                GrammarExample("Neither the teacher nor the students were late.", "न शिक्षक और न ही छात्र देर से आए।", "Nearest Subject")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("Everyone wants to speak confidently.", "हर कोई आत्मविश्वास से बोलना चाहता है।", "Indefinite Singular"),
                GrammarExample("Ten kilometers is a long walk.", "दस किलोमीटर एक लंबी पैदल दूरी है।", "Unit Distance")
            ),
            exercises = listOf(
                GrammarExercise("Write a correct sentence starting with 'Everyone' or 'Each of my friends'.", "'Everyone' या 'Each of my friends' से सही क्रिया वाला वाक्य लिखें।", listOf("everyone is", "everyone wants", "everyone has", "each of my friends is", "each of my friends has"), "Everyone in our class speaks politely.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("sva_1", "Everyone ___ invited to the seminar.", listOf("are", "were", "is", "have"), 2, "'Everyone' is always singular, so use 'is'.", "'Everyone' एकवचन है, इसलिए 'is' सही है।"),
                GrammarMcq("sva_2", "Neither Maya nor her brothers ___ coming.", listOf("is", "was", "are", "has"), 2, "The nearer subject 'brothers' is plural, so use 'are'.", "'brothers' बहुवचन और क्रिया के पास है, इसलिए 'are' आएगा।"),
                GrammarMcq("sva_3", "The quality of these books ___ very good.", listOf("are", "were", "is", "have"), 2, "The true subject is 'quality' (singular), not 'books'.", "असली Subject 'quality' (एकवचन) है, इसलिए 'is' सही है।")
            ),
            homeworkEn = "Check 5 sentences you wrote earlier and verify every Subject matches its Verb.",
            homeworkHi = "अपने लिखे हुए 5 वाक्यों की जांच करें कि कर्ता और क्रिया सही मेल खाते हैं या नहीं।",
            revisionPoints = listOf("Each/Every/Anyone/Nobody -> singular verb.", "Ignore prepositional phrases ('of these books') when finding the main subject.")
        ),
        GrammarTopic(
            id = "conditionals",
            order = 16,
            titleEn = "Conditional Sentences",
            titleHi = "शर्त वाले वाक्य (Conditional Sentences)",
            level = StudentLevel.ADVANCED,
            summaryEn = "Express real possibilities, imaginary situations, and past regrets with Type 0, 1, 2, and 3.",
            explanationHi = "शर्त (If) वाले वाक्य 4 प्रकार के होते हैं:\n• Type 0 (सत्य): If you heat water, it boils.\n• Type 1 (संभव): If you practice, you will improve.\n• Type 2 (काल्पनिक): If I were a bird, I would fly.\n• Type 3 (बीता पछतावा): If you had studied, you would have passed.",
            keyRules = listOf(
                "Never put 'will' inside the 'If' clause (say 'If it rains, I will stay', NOT 'If it will rain').",
                "In Type 2 imaginary sentences, use 'were' for all subjects ('If I were you...')."
            ),
            examples = listOf(
                GrammarExample("If you speak daily, your confidence will grow.", "यदि आप रोज़ बोलेंगे, तो आपका आत्मविश्वास बढ़ेगा।", "Type 1"),
                GrammarExample("If I were fluent, I would lead the debate.", "यदि मैं धाराप्रवाह होता, तो बहस का नेतृत्व करता।", "Type 2")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("If I had woken up earlier, I wouldn't have missed the bus.", "यदि मैं पहले उठ जाता, तो मेरी बस नहीं छूटती।", "Type 3"),
                GrammarExample("If you mix red and white, you get pink.", "यदि आप लाल और सफेद मिलाते हैं, तो गुलाबी बनता है।", "Type 0")
            ),
            exercises = listOf(
                GrammarExercise("Complete: 'If I practice English every day, ...'", "'If I practice English every day,' को Type 1 में पूरा करें।", listOf("will"), "If I practice English every day, I will speak fluently.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("cond_1", "If it ___ tomorrow, we will cancel the picnic.", listOf("will rain", "rains", "rained", "rain"), 1, "Type 1 uses Simple Present ('rains') in the If-clause.", "If वाले हिस्से में 'will' नहीं आता, Simple Present ('rains') लगता है।"),
                GrammarMcq("cond_2", "If I ___ you, I would accept the offer.", listOf("am", "was", "were", "will be"), 2, "Imaginary Type 2 uses 'If I were you'.", "काल्पनिक स्थिति में 'If I were you' का प्रयोग होता है।"),
                GrammarMcq("cond_3", "If she had worked hard, she ___ passed.", listOf("will have", "would have", "would", "had"), 1, "Type 3 pairs 'had + V3' with 'would have + V3'.", "Type 3 में 'would have + V3' आता है।")
            ),
            homeworkEn = "Write one example each for Type 1 (Real), Type 2 (Dream), and Type 3 (Past regret).",
            homeworkHi = "Type 1, Type 2 और Type 3 का एक-एक वाक्य लिखें।",
            revisionPoints = listOf("No 'will' in the If-clause.", "Type 2: If + V2/were -> would + V1.")
        ),
        GrammarTopic(
            id = "question_tags",
            order = 17,
            titleEn = "Question Tags",
            titleHi = "लघु प्रश्न (Question Tags)",
            level = StudentLevel.INTERMEDIATE,
            summaryEn = "Short questions added at the end of statements to confirm agreement in spoken English.",
            explanationHi = "बोलचाल की अंग्रेज़ी में बात की पुष्टि करने के लिए वाक्य के अंत में जो छोटा सवाल जोड़ते हैं, उसे Question Tag कहते हैं। नियम: यदि वाक्य सकारात्मक (+) है तो Tag नकारात्मक (-) होगा, और यदि वाक्य नकारात्मक (-) है तो Tag सकारात्मक (+) होगा।",
            keyRules = listOf(
                "Positive statement -> Negative tag ('You like English, don't you?').",
                "Negative statement -> Positive tag ('He isn't late, is he?').",
                "Special case: 'I am right, aren't I?' (never 'amn't I')."
            ),
            examples = listOf(
                GrammarExample("You practice daily, don't you?", "आप रोज़ अभ्यास करते हैं, है न?", "Positive -> Negative"),
                GrammarExample("She didn't forget, did she?", "वह भूली नहीं, क्या वह भूली?", "Negative -> Positive")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("It's a pleasant morning, isn't it?", "सुबह कितनी सुहावनी है, है न?", "Small Talk"),
                GrammarExample("Let's start the lesson, shall we?", "चलिए पाठ शुरू करें, ठीक है?", "Suggestion Tag")
            ),
            exercises = listOf(
                GrammarExercise("Add a Question Tag to: 'She speaks well, ___?'", "'She speaks well' के लिए सही Question Tag लिखें।", listOf("doesn't she"), "She speaks well, doesn't she?")
            ),
            mcqQuestions = listOf(
                GrammarMcq("qt_1", "I am your friend, ___?", listOf("amn't I", "isn't I", "aren't I", "am I"), 2, "The negative question tag for 'I am' is always 'aren't I?'.", "'I am' का नकारात्मक Question Tag हमेशा 'aren't I?' होता है।"),
                GrammarMcq("qt_2", "They have finished, ___?", listOf("haven't they", "didn't they", "don't they", "aren't they"), 0, "Repeat the helping verb 'have' in negative form -> 'haven't they?'.", "वाक्य की सहायक क्रिया 'have' के आधार पर 'haven't they?' बनेगा।"),
                GrammarMcq("qt_3", "Nobody called you, ___?", listOf("didn't they", "did they", "did nobody", "wasn't it"), 1, "'Nobody' is already negative, so the tag is positive ('did they?').", "'Nobody' नकारात्मक शब्द है, इसलिए Tag सकारात्मक 'did they?' होगा।")
            ),
            homeworkEn = "Speak 5 daily statements with Question Tags to sound natural in conversation.",
            homeworkHi = "बातचीत में प्राकृतिक लगने के लिए 5 वाक्यों के साथ Question Tags बोलकर अभ्यास करें।",
            revisionPoints = listOf("Positive <-> Negative flip.", "Always use a pronoun in the tag (never a noun name).")
        ),
        GrammarTopic(
            id = "sentence_structure",
            order = 18,
            titleEn = "Sentence Structure",
            titleHi = "वाक्य संरचना (Sentence Structure)",
            level = StudentLevel.BEGINNER,
            summaryEn = "Build clear English sentences using Subject + Verb + Object (SVO) instead of Hindi's SOV order.",
            explanationHi = "हिंदी में क्रिया अंत में आती है (मैं अंग्रेज़ी सीखता हूँ - SOV), लेकिन अंग्रेज़ी में क्रिया बीच में आती है: Subject + Verb + Object (I learn English - SVO)। सही क्रम सीख लेने से बोलने की आधी गलतियाँ तुरंत खत्म हो जाती हैं।",
            keyRules = listOf(
                "Basic order: Subject + Verb + Object + Place + Time (I read books at home every evening).",
                "For Questions: Helping Verb + Subject + Main Verb ('Do you speak English?', NOT 'You speak English?')."
            ),
            examples = listOf(
                GrammarExample("I (S) speak (V) English (O) fluently (Manner).", "मैं धाराप्रवाह अंग्रेज़ी बोलता हूँ।", "SVO Order"),
                GrammarExample("Why did you choose this lesson?", "आपने यह पाठ क्यों चुना?", "Wh- Question Order")
            ),
            dailyLifeExamples = listOf(
                GrammarExample("My mother cooks delicious food at home.", "मेरी माँ घर पर स्वादिष्ट खाना बनाती हैं।", "SVO + Place"),
                GrammarExample("Do you need help with pronunciation?", "क्या आपको उच्चारण में मदद चाहिए?", "Yes/No Question")
            ),
            exercises = listOf(
                GrammarExercise("Arrange into proper SVO order: 'English / daily / we / practice'", "शब्दों को सही अंग्रेज़ी क्रम (Subject + Verb + Object + Time) में लगाएं।", listOf("we practice english daily"), "We practice English daily.")
            ),
            mcqQuestions = listOf(
                GrammarMcq("ss_1", "Which sentence has the correct English word order?", listOf("I daily English speak.", "English I speak daily.", "I speak English daily.", "Speak I English daily."), 2, "English follows Subject + Verb + Object + Time.", "अंग्रेज़ी का सही क्रम: Subject + Verb + Object + Time।"),
                GrammarMcq("ss_2", "Choose the proper question structure:", listOf("Where you are going?", "Where are you going?", "Going where you are?", "Where going are you?"), 1, "Place the helping verb ('are') before the subject ('you') in questions.", "प्रश्न में सहायक क्रिया ('are') Subject ('you') से पहले आती है।"),
                GrammarMcq("ss_3", "Every complete sentence needs at least:", listOf("An adjective and adverb", "A subject and a verb", "A preposition and noun", "Two clauses"), 1, "A Subject + Verb forms a complete thought.", "एक पूर्ण वाक्य के लिए कम से कम Subject और Verb ज़रूरी हैं।")
            ),
            homeworkEn = "Translate 5 Hindi sentences into English carefully using the SVO formula.",
            homeworkHi = "5 हिंदी वाक्यों का SVO सूत्र (Subject + Verb + Object) से अंग्रेज़ी में अनुवाद करें।",
            revisionPoints = listOf("Hindi = SOV; English = SVO.", "Time usually comes at the end of an English sentence.")
        )
    )
}
