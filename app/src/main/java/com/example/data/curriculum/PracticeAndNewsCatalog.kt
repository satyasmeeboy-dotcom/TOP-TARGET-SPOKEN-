package com.example.data.curriculum

import com.example.model.DifficultWord
import com.example.model.GrammarMcq
import com.example.model.ListeningLesson
import com.example.model.NewsArticle
import com.example.model.ReadingPassage
import com.example.model.SpeakingScenario
import com.example.model.StudentLevel
import com.example.model.WritingPrompt

object PracticeAndNewsCatalog {
    val speakingScenarios = listOf(
        SpeakingScenario(
            id = "sp_intro",
            titleEn = "Self Introduction",
            titleHi = "आत्म-परिचय (Self Introduction)",
            teacherPromptEn = "Hello! Please introduce yourself in 3 to 4 simple English sentences. Remember: do not share private phone numbers or addresses—just your nickname, hobbies, and learning goal!",
            teacherPromptHi = "नमस्ते! कृपया 3-4 सरल अंग्रेज़ी वाक्यों में अपना परिचय दें। ध्यान रखें: निजी नंबर या पता न बताएं—बस अपना उपनाम, शौक और लक्ष्य बताएं!",
            sampleLines = listOf(
                "Hello everyone! You can call me Aarav.",
                "I enjoy reading books and learning new English words.",
                "My goal is to speak English confidently every day."
            ),
            keyPhrases = listOf("You can call me...", "I enjoy...", "My goal is to..."),
            level = StudentLevel.BEGINNER
        ),
        SpeakingScenario(
            id = "sp_daily",
            titleEn = "Daily Routine Conversation",
            titleHi = "दैनिक दिनचर्या (Daily Conversation)",
            teacherPromptEn = "Tell me what you did this morning from waking up until breakfast.",
            teacherPromptHi = "मुझे बताएं कि आज सुबह उठने से लेकर नाश्ते तक आपने क्या किया।",
            sampleLines = listOf(
                "I woke up at 6:30 AM and drank a glass of water.",
                "Then I revised ten vocabulary words before breakfast."
            ),
            keyPhrases = listOf("I woke up at...", "After that, I...", "Usually I..."),
            level = StudentLevel.BEGINNER
        ),
        SpeakingScenario(
            id = "sp_school",
            titleEn = "School Classroom Talk",
            titleHi = "स्कूल बातचीत (School)",
            teacherPromptEn = "Imagine asking your school teacher politely to explain a question again.",
            teacherPromptHi = "कल्पना करें कि आप स्कूल शिक्षक से विनम्रता से प्रश्न दोबारा समझाने का अनुरोध कर रहे हैं।",
            sampleLines = listOf(
                "Excuse me, Ma'am. Could you please explain this rule one more time?",
                "Thank you! Now I understand it clearly."
            ),
            keyPhrases = listOf("Excuse me...", "Could you please explain...", "May I ask a question?"),
            level = StudentLevel.BEGINNER
        ),
        SpeakingScenario(
            id = "sp_college",
            titleEn = "College Group Discussion",
            titleHi = "कॉलेज चर्चा (College)",
            teacherPromptEn = "Share your opinion politely in a college seminar about why daily reading matters.",
            teacherPromptHi = "कॉलेज सेमिनार में विनम्रता से बताएं कि रोज़ पढ़ना क्यों महत्वपूर्ण है।",
            sampleLines = listOf(
                "In my opinion, daily reading improves both vocabulary and thinking clarity.",
                "I agree with that point, and I would like to add one example."
            ),
            keyPhrases = listOf("In my opinion...", "I would like to add...", "That is a great point..."),
            level = StudentLevel.INTERMEDIATE
        ),
        SpeakingScenario(
            id = "sp_interview",
            titleEn = "Job Interview Practice",
            titleHi = "इंटरव्यू अभ्यास (Interview)",
            teacherPromptEn = "Answer this classic interview question: 'What are your greatest strengths and how do you handle mistakes?'",
            teacherPromptHi = "इंटरव्यू के प्रश्न का उत्तर दें: आपकी सबसे बड़ी ताकत क्या है और आप गलतियों से कैसे सीखते हैं?",
            sampleLines = listOf(
                "My greatest strength is consistency and willingness to learn.",
                "Whenever I make a mistake, I analyze it and correct it right away."
            ),
            keyPhrases = listOf("My greatest strength is...", "I stay calm under pressure...", "I learn quickly from feedback."),
            level = StudentLevel.INTERMEDIATE
        ),
        SpeakingScenario(
            id = "sp_market",
            titleEn = "Market Shopping Conversation",
            titleHi = "बाज़ार बातचीत (Market)",
            teacherPromptEn = "Practice asking a shopkeeper for prices, fresh items, and a polite discount.",
            teacherPromptHi = "दुकानदार से कीमत पूछने और विनम्रता से बात करने का अभ्यास करें।",
            sampleLines = listOf(
                "Excuse me, how much does one kilogram of apples cost?",
                "Could you please pack fresh ones for me? Thank you!"
            ),
            keyPhrases = listOf("How much does this cost?", "Could you show me...", "Please give me the bill."),
            level = StudentLevel.BEGINNER
        ),
        SpeakingScenario(
            id = "sp_travel",
            titleEn = "Travelling & Directions",
            titleHi = "यात्रा व रास्ता पूछना (Travelling)",
            teacherPromptEn = "Ask for directions to the nearest railway station or metro gate politely.",
            teacherPromptHi = "पास के रेलवे स्टेशन या मेट्रो गेट का रास्ता विनम्रता से पूछें।",
            sampleLines = listOf(
                "Excuse me, could you tell me the way to the nearest metro station?",
                "How long does it take to walk there?"
            ),
            keyPhrases = listOf("Could you tell me the way to...", "Is it far from here?", "Go straight and turn left."),
            level = StudentLevel.ELEMENTARY
        ),
        SpeakingScenario(
            id = "sp_public",
            titleEn = "Public Speaking Speech",
            titleHi = "मंच भाषण (Public Speaking)",
            teacherPromptEn = "Deliver a 3-sentence inspiring speech opening on 'Consistency Beats Fear'.",
            teacherPromptHi = "'निरंतरता डर को हराती है' विषय पर मंच से 3 प्रेरक वाक्य बोलें।",
            sampleLines = listOf(
                "Good morning respected teachers and dear friends!",
                "Confidence is not born overnight; it is built one small sentence every day."
            ),
            keyPhrases = listOf("Good morning respected...", "Today I want to share...", "Thank you for listening!"),
            level = StudentLevel.ADVANCED
        ),
        SpeakingScenario(
            id = "sp_reallife",
            titleEn = "Real-Life Restaurant & Doctor",
            titleHi = "वास्तविक जीवन (Real-Life Situations)",
            teacherPromptEn = "Order a healthy meal politely or explain a mild headache to a doctor in clear English.",
            teacherPromptHi = "रेस्तरां में खाना ऑर्डर करें या डॉक्टर को हल्के सिरदर्द के बारे में बताएं।",
            sampleLines = listOf(
                "I have had a mild headache since yesterday evening.",
                "Could I please have a glass of warm water with less spice in the food?"
            ),
            keyPhrases = listOf("I would like to order...", "I have been feeling...", "Thank you for your help."),
            level = StudentLevel.ELEMENTARY
        )
    )

    val listeningLessons = listOf(
        ListeningLesson(
            id = "list_1",
            titleEn = "Morning Habit of Fluent Speakers",
            titleHi = "धाराप्रवाह वक्ताओं की सुबह की आदत",
            category = "Beginner Listening",
            level = StudentLevel.BEGINNER,
            audioScriptEn = "Every morning at seven o'clock, Meera stands in front of a mirror and speaks five simple English sentences. At first, she felt shy and made small grammar errors. After thirty days of patient practice, she realized that speaking aloud trains the tongue and removes fear.",
            hindiSummary = "हर सुबह 7 बजे मीरा शीशे के सामने 5 सरल वाक्य बोलती है। 30 दिनों के अभ्यास से उसका डर दूर हो गया।",
            comprehensionQuiz = listOf(
                GrammarMcq("lq_1", "At what time does Meera practice?", listOf("6:00 AM", "7:00 AM", "8:00 PM", "9:00 AM"), 1, "The audio clearly says 'at seven o'clock' every morning.", "ऑडियो में सुबह 7 बजे बताया गया है।"),
                GrammarMcq("lq_2", "How many sentences does she speak daily?", listOf("Two", "Five", "Ten", "Twenty"), 1, "She speaks five simple English sentences.", "वह रोज़ पाँच सरल वाक्य बोलती है।")
            )
        ),
        ListeningLesson(
            id = "list_2",
            titleEn = "At the Bookshop (Conversation)",
            titleHi = "किताबों की दुकान पर बातचीत",
            category = "English Conversations",
            level = StudentLevel.ELEMENTARY,
            audioScriptEn = "Customer: Good afternoon! Do you have an English grammar workbook for beginners? Shopkeeper: Yes, of course! We have a green bilingual workbook with Hindi explanations. Customer: Wonderful! Could you please show it to me? Shopkeeper: Certainly, here it is on the top shelf.",
            hindiSummary = "ग्राहक दुकानदार से शुरुआती छात्रों के लिए हिंदी व्याख्या वाली अंग्रेज़ी व्याकरण किताब मांगता है।",
            comprehensionQuiz = listOf(
                GrammarMcq("lq_3", "What color is the workbook?", listOf("Blue", "Green", "Yellow", "Red"), 1, "The shopkeeper mentions a green bilingual workbook.", "दुकानदार ने हरे रंग की किताब बताई।"),
                GrammarMcq("lq_4", "Where is the workbook placed?", listOf("Under the desk", "On the top shelf", "Outside", "In a bag"), 1, "He says 'here it is on the top shelf'.", "किताब ऊपर वाली शेल्फ पर रखी है।")
            )
        ),
        ListeningLesson(
            id = "list_3",
            titleEn = "The Wise Bamboo Tree (Audio Story)",
            titleHi = "समझदार बांस का पेड़ (ऑडियो कहानी)",
            category = "Audio Stories",
            level = StudentLevel.INTERMEDIATE,
            audioScriptEn = "A young oak tree laughed at a slender bamboo plant because the bamboo bent whenever the wind blew. One night, a fierce storm arrived. The rigid oak refused to bend and snapped, while the flexible bamboo bowed gently and stood tall again at sunrise. True strength lies in adaptability.",
            hindiSummary = "तेज़ तूफान में अकड़ने वाला बलूत का पेड़ टूट गया, पर झुकने वाला बांस सुबह फिर खड़ा हो गया।",
            comprehensionQuiz = listOf(
                GrammarMcq("lq_5", "Why did the bamboo survive the storm?", listOf("It was taller", "It was flexible and bent", "It had bigger leaves", "There was no wind"), 1, "Flexibility allowed the bamboo to bend without breaking.", "लचीला होने के कारण बांस बिना टूटे झुक गया।")
            )
        ),
        ListeningLesson(
            id = "list_4",
            titleEn = "Pronunciation Drill: V vs W & S vs Sh",
            titleHi = "उच्चारण अभ्यास (Pronunciation Practice)",
            category = "Pronunciation Practice",
            level = StudentLevel.ADVANCED,
            audioScriptEn = "Listen closely to these pairs: Very well, warm water, ship and sip, sheet and seat. Notice how 'V' touches your upper teeth to your lower lip, while 'W' rounds both lips gently. Practice slowly for crystal clear speech!",
            hindiSummary = "V (दांत-होंठ) और W (गोल होंठ) तथा S और Sh के स्पष्ट उच्चारण का अभ्यास।",
            comprehensionQuiz = listOf(
                GrammarMcq("lq_6", "How do you pronounce the 'W' sound?", listOf("Touch teeth to lip", "Round both lips gently", "Close your nose", "Whisper loudly"), 1, "W requires rounding both lips like saying 'oo'.", "'W' बोलते समय दोनों होंठ गोल होते हैं।")
            )
        )
    )

    val readingPassages = listOf(
        ReadingPassage(
            id = "read_1",
            titleEn = "The Two Pots of Water",
            titleHi = "पानी के दो घड़े (नैतिक कहानी)",
            category = "Moral Stories",
            level = StudentLevel.BEGINNER,
            contentEn = "A farmer carried two clay pots every morning from the river. One pot was flawless and held all its water. The other pot had a tiny crack and arrived half empty. The cracked pot felt ashamed and apologized. The wise farmer smiled and pointed at the path: 'Look at the bright green flowers growing on your side! Because of your crack, I planted seeds along your side, and you watered them every day.'",
            contentHi = "एक किसान रोज़ सुबह नदी से मिट्टी के दो घड़े भरकर लाता था। एक घड़ा बिल्कुल सही था, जबकि दूसरे में एक छोटी दरार थी जिससे वह आधा खाली पहुँचता था। दरार वाले घड़े ने शर्मिंदा होकर माफी मांगी। समझदार किसान मुस्कुराया और रास्ते की ओर इशारा किया: 'अपनी तरफ उगे सुंदर हरे फूल देखो! तुम्हारी दरार के कारण मैंने तुम्हारी तरफ बीज बोए और तुमने रोज़ उन्हें सींचा।'",
            difficultWords = listOf(
                DifficultWord("Flawless", "Without any defect or mistake", "त्रुटिहीन / बिल्कुल सही"),
                DifficultWord("Ashamed", "Feeling embarrassed or guilty", "शर्मिंदा"),
                DifficultWord("Pointed", "Showed direction with finger", "इशारा किया")
            ),
            quiz = listOf(
                GrammarMcq("rq_1", "Why did the farmer smile at the cracked pot?", listOf("He wanted to throw it away", "Its dripping water grew lovely flowers", "It was heavier", "It never leaked"), 1, "The leaking water nourished flowers along the path.", "उससे टपकते पानी से रास्ते में सुंदर फूल खिल गए थे।")
            )
        ),
        ReadingPassage(
            id = "read_2",
            titleEn = "Why Thinking in English Helps",
            titleHi = "अंग्रेज़ी में सोचना क्यों मदद करता है",
            category = "Short Articles",
            level = StudentLevel.INTERMEDIATE,
            contentEn = "Many learners translate every single word from Hindi into English in their mind before speaking. This slows down conversation and causes hesitation. Instead, try naming small objects around you directly in English—like 'clean desk', 'warm tea', or 'bright morning'. When your brain links words directly to images, fluency becomes effortless.",
            contentHi = "कई विद्यार्थी बोलने से पहले मन में हर शब्द का हिंदी से अंग्रेज़ी अनुवाद करते हैं। इससे बातचीत धीमी हो जाती है और झिझक होती है। इसके बजाय अपने आस-पास की चीज़ों को सीधे अंग्रेज़ी में पहचानें—जैसे 'clean desk', 'warm tea'। जब दिमाग शब्दों को सीधे चित्रों से जोड़ता है, तो बोलना आसान हो जाता है।",
            difficultWords = listOf(
                DifficultWord("Translate", "Convert from one language to another", "अनुवाद करना"),
                DifficultWord("Hesitation", "Pausing nervously before acting", "झिझक / संकोच"),
                DifficultWord("Effortless", "Requiring very little struggle", "सहज / बिना कठिनाई के")
            ),
            quiz = listOf(
                GrammarMcq("rq_2", "What slows down speaking according to the article?", listOf("Reading aloud", "Word-by-word mental translation", "Learning synonyms", "Listening to stories"), 1, "Translating every single word slows conversation.", "हर शब्द का मन में अनुवाद करने से गति धीमी होती है।")
            )
        )
    )

    val writingPrompts = listOf(
        WritingPrompt("wr_1", "Sentence Formation", "5 Daily Action Sentences", "5 दैनिक क्रिया वाक्य", "Write 3-5 clear sentences about your daily habits using Simple Present Tense.", "Simple Present Tense का उपयोग करके अपनी रोज़ की आदतों पर 3-5 वाक्य लिखें।", "I wake up at 6 AM. I brush my teeth and practice English."),
        WritingPrompt("wr_2", "Paragraph Writing", "My Favourite Teacher", "मेरे पसंदीदा शिक्षक", "Write a short paragraph (4-6 lines) describing a teacher who inspired you.", "एक ऐसे शिक्षक पर छोटा पैराग्राफ लिखें जिन्होंने आपको प्रेरित किया।", "My favourite teacher is very patient and explains every rule clearly."),
        WritingPrompt("wr_3", "Essay Writing", "Importance of Education", "शिक्षा का महत्व", "Write a structured mini-essay with an introduction, main benefit, and conclusion.", "परिचय, मुख्य लाभ और निष्कर्ष के साथ एक छोटा निबंध लिखें।", "Education empowers every person to think clearly and build a bright future."),
        WritingPrompt("wr_4", "Letter Writing", "Letter to a Friend", "मित्र को पत्र", "Write a friendly letter inviting your friend to practice English speaking together.", "अपने मित्र को साथ में अंग्रेज़ी बोलने के अभ्यास के लिए आमंत्रित करते हुए पत्र लिखें।", "Dear Friend,\nI hope you are doing well. Let us practice speaking English for 15 minutes daily."),
        WritingPrompt("wr_5", "Application Writing", "Leave Application to Principal", "प्रधानाचार्य को अवकाश प्रार्थना पत्र", "Write a formal leave application for 2 days of fever.", "बुखार के कारण 2 दिन के अवकाश हेतु औपचारिक प्रार्थना पत्र लिखें।", "To the Principal,\nRespected Sir/Ma'am,\nI kindly request two days of leave because I have mild fever."),
        WritingPrompt("wr_6", "Story Writing", "Honesty Pays Off", "ईमानदारी का फल", "Write a 4-line moral story about returning a lost wallet.", "खोया हुआ बटुआ लौटाने पर 4 पंक्तियों की नैतिक कहानी लिखें।", "Yesterday, Aman found a wallet near the park gate and returned it honestly."),
        WritingPrompt("wr_7", "Daily Diary", "Today's Learning Diary", "आज की डायरी", "Write 3 sentences about what new thing you learned today.", "आज आपने क्या नया सीखा, इस पर 3 वाक्य लिखें।", "Today I learned ten new words and practiced speaking without fear.")
    )

    val dailyNewsArticles = listOf(
        NewsArticle(
            id = "news_1",
            dateLabel = "Today's Edition • Education",
            headlineEn = "Community Libraries Launch Free English Speaking Clubs Across Towns",
            headlineHi = "कस्बों में सामुदायिक पुस्तकालयों ने शुरू किए मुफ़्त अंग्रेज़ी बोलचाल क्लब",
            bodyEn = "Public libraries in several Indian towns have started free weekend English speaking clubs for students. Volunteers guide learners through simple conversations, storytelling, and newspaper reading. Organizers say regular speaking builds confidence without expensive coaching.",
            bodyHi = "कई भारतीय कस्बों के सार्वजनिक पुस्तकालयों ने छात्रों के लिए मुफ़्त सप्ताहांत अंग्रेज़ी बोलचाल क्लब शुरू किए हैं। स्वयंसेवक छात्रों को सरल बातचीत, कहानी सुनाने और अखबार पढ़ने में मार्गदर्शन करते हैं। आयोजकों का कहना है कि नियमित बोलने से बिना महंगी कोचिंग के आत्मविश्वास बढ़ता है।",
            difficultWords = listOf(
                DifficultWord("Volunteers", "People who help freely without pay", "स्वयंसेवक"),
                DifficultWord("Organizers", "People who plan and arrange an event", "आयोजक"),
                DifficultWord("Confidence", "Belief in your own ability", "आत्मविश्वास")
            ),
            grammarNoteEn = "Notice 'have started' (Present Perfect) shows a recent action with impact right now.",
            grammarNoteHi = "ध्यान दें: 'have started' (Present Perfect) बताता है कि काम हाल ही में शुरू हुआ है और अभी प्रभावी है।",
            quiz = listOf(
                GrammarMcq("nq_1", "1. Who guides learners in these library clubs?", listOf("Paid actors", "Volunteers", "Tourists", "Drivers"), 1, "Volunteers guide learners freely.", "स्वयंसेवक (Volunteers) छात्रों का मार्गदर्शन करते हैं।"),
                GrammarMcq("nq_1_2", "2. When are these speaking clubs held?", listOf("Only at midnight", "On weekends", "Once a year", "During exams only"), 1, "The article says 'free weekend English speaking clubs'.", "लेख में सप्ताहांत (weekends) बताया गया है।"),
                GrammarMcq("nq_1_3", "3. What activities happen in the club?", listOf("Conversations, storytelling & newspaper reading", "Only silent math", "Video games", "Cooking"), 0, "Learners practice simple conversations, storytelling, and newspaper reading.", "बातचीत, कहानी सुनाना और अखबार पढ़ना शामिल है।"),
                GrammarMcq("nq_1_4", "4. What is the Hindi meaning of 'Confidence'?", listOf("डर", "आत्मविश्वास", "आलस्य", "क्रोध"), 1, "'Confidence' means belief in your own ability (आत्मविश्वास).", "'Confidence' का अर्थ आत्मविश्वास होता है।"),
                GrammarMcq("nq_1_5", "5. Which tense is 'have started' in the first sentence?", listOf("Simple Future", "Present Perfect", "Past Continuous", "Imperative"), 1, "'have + V3 (started)' is Present Perfect Tense.", "'have started' Present Perfect Tense है।")
            )
        ),
        NewsArticle(
            id = "news_2",
            dateLabel = "Today's Edition • Science & Green Tech",
            headlineEn = "Solar-Powered Study Lamps Brighten Rural Evening Classrooms",
            headlineHi = "सौर ऊर्जा से चलने वाले लैंप ने ग्रामीण शाम की कक्षाओं को रोशन किया",
            bodyEn = "Students in over two hundred villages now study comfortably after sunset thanks to portable solar lamps. Teachers report higher attendance and better reading accuracy. Clean solar energy protects the environment while supporting education.",
            bodyHi = "पोर्टेबल सोलर लैंप की मदद से अब दो सौ से अधिक गाँवों के छात्र सूर्यास्त के बाद भी आराम से पढ़ते हैं। शिक्षकों ने अधिक उपस्थिति और बेहतर पढ़ने की सटीकता दर्ज की है। स्वच्छ सौर ऊर्जा शिक्षा के साथ पर्यावरण की भी रक्षा करती है।",
            difficultWords = listOf(
                DifficultWord("Portable", "Easy to carry anywhere", "आसानी से ले जाने योग्य"),
                DifficultWord("Attendance", "Number of students present", "उपस्थिति"),
                DifficultWord("Environment", "The natural world around us", "पर्यावरण")
            ),
            grammarNoteEn = "'Teachers report...' uses Simple Present Tense because it states an ongoing general observation.",
            grammarNoteHi = "'Teachers report...' में Simple Present Tense का उपयोग वर्तमान सामान्य स्थिति बताने के लिए हुआ है।",
            quiz = listOf(
                GrammarMcq("nq_2", "1. What positive change did teachers notice?", listOf("Higher attendance", "Longer holidays", "Fewer books", "Late buses"), 0, "Teachers reported higher attendance and better reading accuracy.", "शिक्षकों ने अधिक उपस्थिति और बेहतर पठन देखा।"),
                GrammarMcq("nq_2_2", "2. How many villages benefit from these solar lamps?", listOf("Over two hundred", "Only five", "Ten cities", "None"), 0, "The report mentions 'over two hundred villages'.", "रिपोर्ट में दो सौ से अधिक गाँवों का उल्लेख है।"),
                GrammarMcq("nq_2_3", "3. What does 'Portable' mean?", listOf("Very heavy", "Easy to carry anywhere", "Broken", "Expensive"), 1, "'Portable' means easy to carry or move around.", "'Portable' का अर्थ आसानी से ले जाने योग्य है।"),
                GrammarMcq("nq_2_4", "4. Which two things does clean solar energy help?", listOf("Traffic and noise", "Environment and education", "Rain and snow", "Plastic and smoke"), 1, "It protects the environment while supporting education.", "यह पर्यावरण की रक्षा और शिक्षा में मदद करती है।"),
                GrammarMcq("nq_2_5", "5. Identify the verb tense in 'Clean solar energy protects...':", listOf("Simple Past", "Simple Present", "Future Continuous", "Present Perfect"), 1, "Singular subject 'solar energy' + 'protects' (V1+s) is Simple Present.", "'protects' Simple Present Tense दर्शाता है।")
            )
        ),
        NewsArticle(
            id = "news_3",
            dateLabel = "Archive • Digital Literacy",
            headlineEn = "Offline Learning Apps Help Students Study Without Continuous Internet",
            headlineHi = "ऑफ़लाइन लर्निंग ऐप्स बिना लगातार इंटरनेट के छात्रों की पढ़ाई में मददगार",
            bodyEn = "Educators praise mobile apps that store grammar lessons, quizzes, and vocabulary directly on the student's phone. Learners in low-network areas can revise anytime without worrying about mobile data costs.",
            bodyHi = "शिक्षक ऐसे मोबाइल ऐप्स की सराहना कर रहे हैं जो व्याकरण पाठ, क्विज़ और शब्दावली सीधे फोन में सेव रखते हैं। कम नेटवर्क वाले क्षेत्रों के छात्र बिना डेटा खर्च की चिंता के कभी भी दोहराव कर सकते हैं।",
            difficultWords = listOf(
                DifficultWord("Praise", "Express strong approval or admiration", "सराहना करना"),
                DifficultWord("Revise", "Review and practice again", "दोहराना")
            ),
            grammarNoteEn = "'can revise' uses the modal verb 'can' + base verb V1 'revise'.",
            grammarNoteHi = "'can revise' में modal 'can' के साथ क्रिया का पहला रूप (V1) 'revise' लगा है।",
            quiz = listOf(
                GrammarMcq("nq_3", "1. Why are offline apps useful in low-network areas?", listOf("They require costly plans", "Students can revise anytime without internet", "They delete lessons", "They only play videos"), 1, "Stored lessons let students study without continuous internet.", "फोन में सेव पाठ से बिना इंटरनेट कभी भी दोहराव संभव है।"),
                GrammarMcq("nq_3_2", "2. What three study items do these apps store locally?", listOf("Games only", "Grammar lessons, quizzes & vocabulary", "Movies", "Shopping bills"), 1, "The paragraph lists grammar lessons, quizzes, and vocabulary.", "इसमें व्याकरण पाठ, क्विज़ और शब्दावली शामिल हैं।"),
                GrammarMcq("nq_3_3", "3. What does 'Praise' mean?", listOf("Scold", "Express approval / admire", "Forget", "Delete"), 1, "'Praise' means to express strong approval or admiration.", "'Praise' का अर्थ सराहना करना होता है।"),
                GrammarMcq("nq_3_4", "4. Which word follows the modal 'can' in 'can revise'?", listOf("revised (V2)", "revising (V4)", "revise (V1 base verb)", "to revise"), 2, "Modal verbs always take base verb V1 ('revise').", "Modal 'can' के साथ हमेशा क्रिया का पहला रूप (V1) आता है।"),
                GrammarMcq("nq_3_5", "5. What worry do offline learners avoid?", listOf("Mobile data costs", "Learning English", "Reading stories", "Attending morning class"), 0, "Learners revise without worrying about mobile data costs.", "छात्र मोबाइल डेटा खर्च की चिंता से बचते हैं।")
            )
        )
    )
}
