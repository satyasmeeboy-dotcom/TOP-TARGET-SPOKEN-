package com.example.data.curriculum

import com.example.model.DailyPhraseOrIdiom
import com.example.model.DailyWord
import com.example.model.StudentLevel

object WordsCatalog {
    val genZWordsPool = listOf(
        DailyWord(
            id = "gz_1", word = "Slay", isGenZ = true, pronunciation = "स्ले (slay)",
            hindiMeaning = "शानदार प्रदर्शन करना / कमाल कर देना",
            exampleSentence = "You totally slayed your English presentation today!",
            hindiTranslation = "आज आपने अपनी अंग्रेज़ी प्रस्तुति में कमाल कर दिया!",
            realLifeUsage = "Used to praise a friend's confident talk, outfit, or success."
        ),
        DailyWord(
            id = "gz_2", word = "Vibe", isGenZ = true, pronunciation = "वाइब (vibe)",
            hindiMeaning = "माहौल / सकारात्मक ऊर्जा या एहसास",
            exampleSentence = "This classroom has a very friendly and positive vibe.",
            hindiTranslation = "इस कक्षा का माहौल बहुत दोस्ताना और सकारात्मक है।",
            realLifeUsage = "Describing how a place, music, or conversation feels."
        ),
        DailyWord(
            id = "gz_3", word = "No Cap", isGenZ = true, pronunciation = "नो कैप (no cap)",
            hindiMeaning = "सच में / बिना झूठ बोले",
            exampleSentence = "Practicing ten minutes daily really works, no cap!",
            hindiTranslation = "रोज़ दस मिनट अभ्यास सच में काम करता है, पक्का!",
            realLifeUsage = "Informal chats when emphasizing honesty."
        ),
        DailyWord(
            id = "gz_4", word = "Glow Up", isGenZ = true, pronunciation = "ग्लो अप (glow up)",
            hindiMeaning = "शानदार सुधार या निखार आना",
            exampleSentence = "Her spoken English had a huge glow up this semester.",
            hindiTranslation = "इस सेमेस्टर उसकी अंग्रेज़ी बोलने में जबरदस्त निखार आया है।",
            realLifeUsage = "Celebrating visible growth in confidence, skill, or style."
        ),
        DailyWord(
            id = "gz_5", word = "Lowkey", isGenZ = true, pronunciation = "लो-की (low-kee)",
            hindiMeaning = "थोड़ा-सा / चुपचाप या हल्के तौर पर",
            exampleSentence = "I was lowkey nervous before my first interview.",
            hindiTranslation = "मैं अपने पहले इंटरव्यू से पहले थोड़ा-सा घबराया हुआ था।",
            realLifeUsage = "Sharing subtle feelings honestly without exaggerating."
        ),
        DailyWord(
            id = "gz_6", word = "Highkey", isGenZ = true, pronunciation = "हाई-की (high-kee)",
            hindiMeaning = "खुलकर / बहुत ज़्यादा",
            exampleSentence = "I am highkey excited to speak English on stage.",
            hindiTranslation = "मैं मंच पर अंग्रेज़ी बोलने के लिए बहुत ज़्यादा उत्साहित हूँ।",
            realLifeUsage = "Expressing strong enthusiasm openly."
        ),
        DailyWord(
            id = "gz_7", word = "Main Character Energy", isGenZ = true, pronunciation = "मेन कैरेक्टर एनर्जी",
            hindiMeaning = "आत्मविश्वास से भरा लीडर जैसा अंदाज़",
            exampleSentence = "Walk into the exam hall with main character energy!",
            hindiTranslation = "पूरे आत्मविश्वास के साथ परीक्षा हॉल में कदम रखें!",
            realLifeUsage = "Motivating someone to feel confident and fearless."
        ),
        DailyWord(
            id = "gz_8", word = "Bet", isGenZ = true, pronunciation = "बेट (bet)",
            hindiMeaning = "पक्का! / बिल्कुल ठीक!",
            exampleSentence = "\"Shall we practice speaking at 6 PM?\" \"Bet!\"",
            hindiTranslation = "\"क्या हम शाम 6 बजे बोलने का अभ्यास करें?\" \"पक्का!\"",
            realLifeUsage = "Quick friendly agreement among classmates."
        ),
        DailyWord(
            id = "gz_9", word = "Flex", isGenZ = true, pronunciation = "फ्लेक्स (flex)",
            hindiMeaning = "अपनी उपलब्धि या हुनर दिखाना",
            exampleSentence = "Speaking clearly without notes is a real flex.",
            hindiTranslation = "बिना कागज़ देखे स्पष्ट बोलना सच में एक बड़ी उपलब्धि है।",
            realLifeUsage = "Talking about skills or achievements playfully."
        ),
        DailyWord(
            id = "gz_10", word = "GOAT", isGenZ = true, pronunciation = "गोट (G.O.A.T.)",
            hindiMeaning = "अब तक का सर्वश्रेष्ठ (Greatest Of All Time)",
            exampleSentence = "A patient teacher who never gives up on students is the GOAT.",
            hindiTranslation = "एक धैर्यवान शिक्षक जो छात्रों का साथ कभी न छोड़े, सर्वश्रेष्ठ होता है।",
            realLifeUsage = "Admiring top performers in sports, study, or mentorship."
        ),
        DailyWord(
            id = "gz_11", word = "Sus", isGenZ = true, pronunciation = "सस (sus)",
            hindiMeaning = "संदिग्ध / कुछ गड़बड़ लगना",
            exampleSentence = "That grammar shortcut sounds a little sus; let's check the rule.",
            hindiTranslation = "वह व्याकरण शॉर्टकट थोड़ा संदिग्ध लगता है; चलिए नियम जांचते हैं।",
            realLifeUsage = "Short for 'suspicious' in casual talk."
        ),
        DailyWord(
            id = "gz_12", word = "Ghosting", isGenZ = true, pronunciation = "घोस्टिंग (ghost-ing)",
            hindiMeaning = "बिना बताए बातचीत बंद कर देना",
            exampleSentence = "Stop ghosting your daily study plan—stay consistent!",
            hindiTranslation = "अपनी दैनिक पढ़ाई की योजना को नजरअंदाज़ करना बंद करें—नियमित रहें!",
            realLifeUsage = "Ignoring messages or skipping routines suddenly."
        ),
        DailyWord(
            id = "gz_13", word = "W (Win)", isGenZ = true, pronunciation = "डब्ल्यू (win)",
            hindiMeaning = "बड़ी जीत या सफलता",
            exampleSentence = "Scoring 10 out of 10 in today's quiz is a massive W!",
            hindiTranslation = "आज के क्विज़ में 10 में से 10 अंक लाना एक बड़ी जीत है!",
            realLifeUsage = "Celebrating small or big daily victories."
        ),
        DailyWord(
            id = "gz_14", word = "Clapback", isGenZ = true, pronunciation = "क्लैप-बैक",
            hindiMeaning = "चतुराई से तुरंत जवाब देना",
            exampleSentence = "Her polite and smart clapback silenced the doubters.",
            hindiTranslation = "उसके विनम्र और समझदार जवाब ने संदेह करने वालों को चुप करा दिया।",
            realLifeUsage = "Giving a sharp, witty reply."
        ),
        DailyWord(
            id = "gz_15", word = "Rizz", isGenZ = true, pronunciation = "रिज़ (rizz)",
            hindiMeaning = "आकर्षक बातचीत और करिश्मा",
            exampleSentence = "Good communication skills give you natural speaking rizz.",
            hindiTranslation = "अच्छा संवाद कौशल आपकी बातचीत को आकर्षक बनाता है।",
            realLifeUsage = "Complimenting someone's charm and smooth communication."
        ),
        DailyWord(
            id = "gz_16", word = "Bussin", isGenZ = true, pronunciation = "बसिन (bussin)",
            hindiMeaning = "बेहद स्वादिष्ट या बहुत बढ़िया",
            exampleSentence = "Homemade masala tea during study breaks is bussin!",
            hindiTranslation = "पढ़ाई के ब्रेक में घर की बनी मसाला चाय बहुत बढ़िया होती है!",
            realLifeUsage = "Usually describing awesome food or treats."
        ),
        DailyWord(
            id = "gz_17", word = "Touch Grass", isGenZ = true, pronunciation = "टच ग्रास",
            hindiMeaning = "स्क्रीन छोड़कर बाहर ताज़ी हवा में टहलना",
            exampleSentence = "After two hours of study, go touch grass and refresh your mind.",
            hindiTranslation = "दो घंटे पढ़ाई के बाद बाहर जाकर थोड़ा टहलें और मन ताज़ा करें।",
            realLifeUsage = "Friendly reminder to take a real-world break."
        ),
        DailyWord(
            id = "gz_18", word = "Hits Different", isGenZ = true, pronunciation = "हिट्स डिफरेंट",
            hindiMeaning = "खास और अनोखा एहसास देना",
            exampleSentence = "Understanding an English movie without subtitles hits different!",
            hindiTranslation = "बिना सबटाइटल्स के अंग्रेज़ी फिल्म समझना एक खास खुशी देता है!",
            realLifeUsage = "When an achievement or moment feels uniquely rewarding."
        ),
        DailyWord(
            id = "gz_19", word = "Lock In", isGenZ = true, pronunciation = "लॉक इन",
            hindiMeaning = "पूरी तरह ध्यान केंद्रित करना",
            exampleSentence = "Let's lock in for the next 15 minutes and master tenses.",
            hindiTranslation = "आइए अगले 15 मिनट पूरी तरह ध्यान लगाकर काल सीखें।",
            realLifeUsage = "Getting into deep focus mode before exams or tasks."
        ),
        DailyWord(
            id = "gz_20", word = "Aura", isGenZ = true, pronunciation = "ऑरा (aura)",
            hindiMeaning = "व्यक्तित्व का प्रभाव / तेज़",
            exampleSentence = "Speaking calmly during an interview boosts your aura.",
            hindiTranslation = "इंटरव्यू में शांत रहकर बोलने से आपका प्रभाव बढ़ता है।",
            realLifeUsage = "Describing confident, cool presence."
        )
    )

    val vocabularyPool = listOf(
        DailyWord(
            id = "voc_1", word = "Confident", isGenZ = false, pronunciation = "कॉन्फिडेंट (kon-fi-duhnt)",
            hindiMeaning = "आत्मविश्वासी",
            exampleSentence = "She felt confident while introducing herself.",
            hindiTranslation = "अपना परिचय देते समय उसने आत्मविश्वास महसूस किया।",
            realLifeUsage = "Interviews, stage speaking, and daily introductions.",
            synonyms = listOf("Self-assured", "Bold", "Fearless"),
            antonyms = listOf("Nervous", "Timid"),
            level = StudentLevel.BEGINNER
        ),
        DailyWord(
            id = "voc_2", word = "Polite", isGenZ = false, pronunciation = "पोलाइट (puh-lyt)",
            hindiMeaning = "विनम्र / शिष्ट",
            exampleSentence = "Always use polite words like 'please' and 'thank you'.",
            hindiTranslation = "हमेशा 'please' और 'thank you' जैसे विनम्र शब्दों का प्रयोग करें।",
            realLifeUsage = "Customer service, school, and elders.",
            synonyms = listOf("Courteous", "Respectful", "Gentle"),
            antonyms = listOf("Rude", "Impolite"),
            level = StudentLevel.BEGINNER
        ),
        DailyWord(
            id = "voc_3", word = "Consistent", isGenZ = false, pronunciation = "कंसिस्टेंट (kuhn-sis-tuhnt)",
            hindiMeaning = "नियमित / निरंतर",
            exampleSentence = "Consistent practice turns beginners into fluent speakers.",
            hindiTranslation = "नियमित अभ्यास शुरुआती लोगों को धाराप्रवाह वक्ता बना देता है।",
            realLifeUsage = "Study habits and skill building.",
            synonyms = listOf("Steady", "Regular", "Dedicated"),
            antonyms = listOf("Irregular", "Inconsistent"),
            level = StudentLevel.ELEMENTARY
        ),
        DailyWord(
            id = "voc_4", word = "Fluent", isGenZ = false, pronunciation = "फ्लुएंट (floo-uhnt)",
            hindiMeaning = "धाराप्रवाह (बिना अटके बोलने वाला)",
            exampleSentence = "My goal is to become fluent in spoken English.",
            hindiTranslation = "मेरा लक्ष्य धाराप्रवाह अंग्रेज़ी बोलना सीखना है।",
            realLifeUsage = "Describing smooth language speaking.",
            synonyms = listOf("Articulate", "Smooth", "Eloquent"),
            antonyms = listOf("Hesitant", "Halting"),
            level = StudentLevel.BEGINNER
        ),
        DailyWord(
            id = "voc_5", word = "Encourage", isGenZ = false, pronunciation = "एनकरेज (en-kur-ij)",
            hindiMeaning = "प्रोत्साहित करना / हिम्मत बढ़ाना",
            exampleSentence = "Good teachers encourage students to ask questions.",
            hindiTranslation = "अच्छे शिक्षक छात्रों को प्रश्न पूछने के लिए प्रोत्साहित करते हैं।",
            realLifeUsage = "Motivating friends, classmates, or juniors.",
            synonyms = listOf("Motivate", "Inspire", "Support"),
            antonyms = listOf("Discourage", "Demotivate"),
            level = StudentLevel.ELEMENTARY
        ),
        DailyWord(
            id = "voc_6", word = "Gratitude", isGenZ = false, pronunciation = "ग्रैटिट्यूड (grat-i-tyood)",
            hindiMeaning = "कृतज्ञता / आभार",
            exampleSentence = "Expressing gratitude makes relationships stronger.",
            hindiTranslation = "आभार व्यक्त करने से रिश्ते मजबूत होते हैं।",
            realLifeUsage = "Thanking mentors, parents, and colleagues.",
            synonyms = listOf("Thankfulness", "Appreciation"),
            antonyms = listOf("Ingratitude"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_7", word = "Hesitate", isGenZ = false, pronunciation = "हेज़िटेट (hez-i-tayt)",
            hindiMeaning = "हिचकिचाना / संकोच करना",
            exampleSentence = "Do not hesitate to speak even if you make small mistakes.",
            hindiTranslation = "छोटी गलतियाँ होने पर भी बोलने में हिचकिचाएं नहीं।",
            realLifeUsage = "Overcoming fear of speaking in public.",
            synonyms = listOf("Pause", "Waver", "Falter"),
            antonyms = listOf("Proceed", "Continue"),
            level = StudentLevel.ELEMENTARY
        ),
        DailyWord(
            id = "voc_8", word = "Curious", isGenZ = false, pronunciation = "क्यूरियस (kyoor-ee-uhs)",
            hindiMeaning = "जिज्ञासु / जानने को उत्सुक",
            exampleSentence = "Curious learners discover new words every day.",
            hindiTranslation = "जिज्ञासु विद्यार्थी हर दिन नए शब्द खोजते हैं।",
            realLifeUsage = "Learning, science, and classroom discussions.",
            synonyms = listOf("Inquisitive", "Eager", "Interested"),
            antonyms = listOf("Indifferent", "Uninterested"),
            level = StudentLevel.ELEMENTARY
        ),
        DailyWord(
            id = "voc_9", word = "Resilient", isGenZ = false, pronunciation = "रेज़िलिएंट (ri-zil-yuhnt)",
            hindiMeaning = "कठिनाइयों से जल्दी उबरने वाला / लचीला",
            exampleSentence = "Resilient students treat mistakes as stepping stones.",
            hindiTranslation = "मजबूत इरादे वाले छात्र गलतियों को सफलता की सीढ़ी मानते हैं।",
            realLifeUsage = "Essays, interviews, and personal growth stories.",
            synonyms = listOf("Tough", "Adaptable", "Strong"),
            antonyms = listOf("Fragile", "Weak"),
            level = StudentLevel.ADVANCED
        ),
        DailyWord(
            id = "voc_10", word = "Articulate", isGenZ = false, pronunciation = "आर्टिकुलेट (aar-tik-yoo-lit)",
            hindiMeaning = "स्पष्ट व प्रभावशाली ढंग से बोलने वाला",
            exampleSentence = "With practice, you will articulate your ideas clearly.",
            hindiTranslation = "अभ्यास से आप अपने विचारों को स्पष्ट रूप से व्यक्त कर पाएंगे।",
            realLifeUsage = "Public speaking and job interviews.",
            synonyms = listOf("Expressive", "Clear", "Eloquent"),
            antonyms = listOf("Unclear", "Mumbled"),
            level = StudentLevel.ADVANCED
        ),
        DailyWord(
            id = "voc_11", word = "Essential", isGenZ = false, pronunciation = "एसेंशियल (uh-sen-shuhl)",
            hindiMeaning = "अत्यंत आवश्यक / ज़रूरी",
            exampleSentence = "Listening carefully is essential for good pronunciation.",
            hindiTranslation = "अच्छे उच्चारण के लिए ध्यान से सुनना अत्यंत आवश्यक है।",
            realLifeUsage = "Daily advice, essays, and instructions.",
            synonyms = listOf("Vital", "Crucial", "Necessary"),
            antonyms = listOf("Optional", "Unnecessary"),
            level = StudentLevel.ELEMENTARY
        ),
        DailyWord(
            id = "voc_12", word = "Achieve", isGenZ = false, pronunciation = "अचीव (uh-cheev)",
            hindiMeaning = "प्राप्त करना / हासिल करना",
            exampleSentence = "Daily effort helps you achieve your target.",
            hindiTranslation = "दैनिक प्रयास आपको अपना लक्ष्य हासिल करने में मदद करता है।",
            realLifeUsage = "Goals, exams, and career planning.",
            synonyms = listOf("Accomplish", "Attain", "Reach"),
            antonyms = listOf("Fail", "Miss"),
            level = StudentLevel.BEGINNER
        ),
        DailyWord(
            id = "voc_13", word = "Improve", isGenZ = false, pronunciation = "इम्प्रूव (im-proov)",
            hindiMeaning = "सुधारना / बेहतर बनाना",
            exampleSentence = "Reading news daily will improve your vocabulary.",
            hindiTranslation = "रोज़ समाचार पढ़ने से आपकी शब्दावली बेहतर होगी।",
            realLifeUsage = "Study feedback and self-growth.",
            synonyms = listOf("Enhance", "Develop", "Upgrade"),
            antonyms = listOf("Worsen", "Decline"),
            level = StudentLevel.BEGINNER
        ),
        DailyWord(
            id = "voc_14", word = "Opportunity", isGenZ = false, pronunciation = "ऑपर्च्युनिटी (op-er-tyoo-ni-tee)",
            hindiMeaning = "अवसर / मौका",
            exampleSentence = "Every conversation is an opportunity to practice.",
            hindiTranslation = "हर बातचीत अभ्यास करने का एक अवसर है।",
            realLifeUsage = "Career, college, and life advice.",
            synonyms = listOf("Chance", "Opening", "Occasion"),
            antonyms = listOf("Obstacle"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_15", word = "Diligent", isGenZ = false, pronunciation = "डिलिजेंट (dil-i-juhnt)",
            hindiMeaning = "मेहनती / परिश्रमी",
            exampleSentence = "A diligent learner revises notes before sleeping.",
            hindiTranslation = "एक मेहनती विद्यार्थी सोने से पहले नोट्स दोहराता है।",
            realLifeUsage = "Academic praise and recommendation letters.",
            synonyms = listOf("Hardworking", "Industrious", "Careful"),
            antonyms = listOf("Lazy", "Careless"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_16", word = "Clarity", isGenZ = false, pronunciation = "क्लैरिटी (klar-i-tee)",
            hindiMeaning = "स्पष्टता / साफ समझ",
            exampleSentence = "Speak with clarity rather than speaking too fast.",
            hindiTranslation = "बहुत तेज़ बोलने के बजाय स्पष्टता के साथ बोलें।",
            realLifeUsage = "Communication coaching.",
            synonyms = listOf("Clearness", "Precision"),
            antonyms = listOf("Confusion", "Ambiguity"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_17", word = "Comprehend", isGenZ = false, pronunciation = "कॉम्प्रिहेंड (kom-pri-hend)",
            hindiMeaning = "गहराई से समझना",
            exampleSentence = "Read slowly so you can comprehend the full passage.",
            hindiTranslation = "धीरे पढ़ें ताकि आप पूरा गद्यांश समझ सकें।",
            realLifeUsage = "Reading comprehension and listening exams.",
            synonyms = listOf("Understand", "Grasp"),
            antonyms = listOf("Misunderstand"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_18", word = "Perspective", isGenZ = false, pronunciation = "पर्सपेक्टिव (per-spek-tiv)",
            hindiMeaning = "दृष्टिकोण / नज़रिए",
            exampleSentence = "Reading articles gives us a broader perspective.",
            hindiTranslation = "लेख पढ़ने से हमें एक व्यापक दृष्टिकोण मिलता है।",
            realLifeUsage = "Debates, essays, and news discussion.",
            synonyms = listOf("Viewpoint", "Outlook", "Angle"),
            antonyms = listOf("Narrow-mindedness"),
            level = StudentLevel.ADVANCED
        ),
        DailyWord(
            id = "voc_19", word = "Courtesy", isGenZ = false, pronunciation = "कर्टसी (kur-tuh-see)",
            hindiMeaning = "शिष्टाचार / विनम्र व्यवहार",
            exampleSentence = "She greeted the guests with warmth and courtesy.",
            hindiTranslation = "उसने मेहमानों का गर्मजोशी और शिष्टाचार से स्वागत किया।",
            realLifeUsage = "Social and workplace etiquette.",
            synonyms = listOf("Politeness", "Civility", "Respect"),
            antonyms = listOf("Rudeness"),
            level = StudentLevel.INTERMEDIATE
        ),
        DailyWord(
            id = "voc_20", word = "Persevere", isGenZ = false, pronunciation = "पर्सिवियर (pur-suh-veer)",
            hindiMeaning = "डटे रहना / हार न मानना",
            exampleSentence = "Persevere through tough grammar topics; mastery comes soon.",
            hindiTranslation = "कठिन व्याकरण विषयों में डटे रहें; महारत जल्द आ जाएगी।",
            realLifeUsage = "Motivational speeches and essays.",
            synonyms = listOf("Persist", "Continue", "Endure"),
            antonyms = listOf("Quit", "Give up"),
            level = StudentLevel.ADVANCED
        )
    )

    fun todaysTenGenZWords(epochDay: Long): List<DailyWord> {
        val start = ((epochDay % genZWordsPool.size).toInt()).coerceAtLeast(0)
        return (0 until 10).map { offset ->
            genZWordsPool[(start + offset) % genZWordsPool.size]
        }
    }

    fun todaysTenVocabWords(epochDay: Long): List<DailyWord> {
        val start = ((epochDay % vocabularyPool.size).toInt()).coerceAtLeast(0)
        return (0 until 10).map { offset ->
            vocabularyPool[(start + offset) % vocabularyPool.size]
        }
    }

    val phrasesAndIdiomsPool = listOf(
        DailyPhraseOrIdiom(
            id = "pi_1", phrase = "Break the ice", isIdiom = true,
            hindiMeaning = "बातचीत शुरू करना / झिझक दूर करना",
            usageTipEn = "Used when starting a friendly talk with someone new.",
            exampleSentence = "A warm smile helps break the ice in a new classroom.",
            hindiTranslation = "एक प्यारी मुस्कान नई कक्षा में बातचीत शुरू करने में मदद करती है।",
            level = StudentLevel.BEGINNER
        ),
        DailyPhraseOrIdiom(
            id = "pi_2", phrase = "A piece of cake", isIdiom = true,
            hindiMeaning = "बहुत आसान काम (बाएं हाथ का खेल)",
            usageTipEn = "Say this when a task feels super easy after practice.",
            exampleSentence = "After daily practice, English speaking feels like a piece of cake!",
            hindiTranslation = "रोज़ अभ्यास के बाद अंग्रेज़ी बोलना बहुत आसान लगता है!",
            level = StudentLevel.BEGINNER
        ),
        DailyPhraseOrIdiom(
            id = "pi_3", phrase = "Hit the books", isIdiom = true,
            hindiMeaning = "मन लगाकर पढ़ाई करना",
            usageTipEn = "Means to start studying seriously.",
            exampleSentence = "Exams are next week, so let's hit the books today.",
            hindiTranslation = "अगले सप्ताह परीक्षा है, इसलिए चलिए आज मन लगाकर पढ़ाई करें।",
            level = StudentLevel.ELEMENTARY
        ),
        DailyPhraseOrIdiom(
            id = "pi_4", phrase = "Keep your chin up", isIdiom = true,
            hindiMeaning = "हिम्मत न हारना / हौसला बनाए रखना",
            usageTipEn = "Encourage a friend who feels disappointed.",
            exampleSentence = "Keep your chin up! Every mistake teaches you something new.",
            hindiTranslation = "हिम्मत मत हारो! हर गलती आपको कुछ नया सिखाती है।",
            level = StudentLevel.BEGINNER
        ),
        DailyPhraseOrIdiom(
            id = "pi_5", phrase = "Go the extra mile", isIdiom = true,
            hindiMeaning = "उम्मीद से बढ़कर मेहनत करना",
            usageTipEn = "Putting in extra effort to achieve greatness.",
            exampleSentence = "Students who go the extra mile speak fluently faster.",
            hindiTranslation = "जो छात्र अतिरिक्त मेहनत करते हैं, वे जल्दी धाराप्रवाह बोलते हैं।",
            level = StudentLevel.INTERMEDIATE
        ),
        DailyPhraseOrIdiom(
            id = "pi_6", phrase = "Once in a blue moon", isIdiom = true,
            hindiMeaning = "ईद का चाँद होना / बहुत कम होना",
            usageTipEn = "Describes something that happens very rarely.",
            exampleSentence = "Do not study only once in a blue moon; practice daily.",
            hindiTranslation = "कभी-कभार ही पढ़ाई न करें; रोज़ अभ्यास करें।",
            level = StudentLevel.ELEMENTARY
        ),
        DailyPhraseOrIdiom(
            id = "pi_7", phrase = "Call it a day", isIdiom = true,
            hindiMeaning = "आज का काम समाप्त करना",
            usageTipEn = "Used when finishing your study or work session.",
            exampleSentence = "We finished ten new words—let's call it a day!",
            hindiTranslation = "हमने दस नए शब्द पूरे कर लिए—चलिए आज की पढ़ाई यहीं समाप्त करें!",
            level = StudentLevel.ELEMENTARY
        ),
        DailyPhraseOrIdiom(
            id = "pi_8", phrase = "By all means", isIdiom = false,
            hindiMeaning = "बेशक / ज़रूर / खुशी से",
            usageTipEn = "Polite phrase to say 'yes, definitely!'",
            exampleSentence = "\"May I ask one more question?\" \"By all means!\"",
            hindiTranslation = "\"क्या मैं एक और प्रश्न पूछ सकता हूँ?\" \"ज़रूर, खुशी से!\"",
            level = StudentLevel.INTERMEDIATE
        ),
        DailyPhraseOrIdiom(
            id = "pi_9", phrase = "Get the hang of it", isIdiom = true,
            hindiMeaning = "किसी काम का तरीका समझ में आ जाना",
            usageTipEn = "When something tricky suddenly starts making sense.",
            exampleSentence = "Tenses seemed hard at first, but now I am getting the hang of it.",
            hindiTranslation = "पहले काल कठिन लगते थे, लेकिन अब मुझे वे समझ आने लगे हैं।",
            level = StudentLevel.INTERMEDIATE
        ),
        DailyPhraseOrIdiom(
            id = "pi_10", phrase = "Look on the bright side", isIdiom = false,
            hindiMeaning = "सकारात्मक पहलू देखना",
            usageTipEn = "Finding hope and positivity in any situation.",
            exampleSentence = "Look on the bright side: you already know more words than yesterday!",
            hindiTranslation = "सकारात्मक पहलू देखें: आप कल से ज़्यादा शब्द पहले ही सीख चुके हैं!",
            level = StudentLevel.BEGINNER
        ),
        DailyPhraseOrIdiom(
            id = "pi_11", phrase = "Blessing in disguise", isIdiom = true,
            hindiMeaning = "अप्रत्यक्ष वरदान (मुश्किल में छिपा फायदा)",
            usageTipEn = "Something that seemed bad at first but turned out good.",
            exampleSentence = "Making small errors in practice is a blessing in disguise because you learn the rule forever.",
            hindiTranslation = "अभ्यास में छोटी गलतियाँ एक वरदान हैं क्योंकि उनसे नियम हमेशा के लिए याद हो जाता है।",
            level = StudentLevel.ADVANCED
        ),
        DailyPhraseOrIdiom(
            id = "pi_12", phrase = "Better late than never", isIdiom = false,
            hindiMeaning = "देर आए दुरुस्त आए",
            usageTipEn = "It is always good to start learning, even if you started later.",
            exampleSentence = "You started learning spoken English today—better late than never!",
            hindiTranslation = "आपने आज अंग्रेज़ी बोलना शुरू किया—देर आए दुरुस्त आए!",
            level = StudentLevel.BEGINNER
        ),
        DailyPhraseOrIdiom(
            id = "pi_13", phrase = "On cloud nine", isIdiom = true,
            hindiMeaning = "सातवें आसमान पर (बेहद खुश)",
            usageTipEn = "Expressing pure joy and excitement.",
            exampleSentence = "Riya was on cloud nine after earning her Beginner Certificate.",
            hindiTranslation = "प्रारंभिक प्रमाणपत्र पाकर रिया बेहद खुश थी।",
            level = StudentLevel.ELEMENTARY
        ),
        DailyPhraseOrIdiom(
            id = "pi_14", phrase = "Speak of the devil", isIdiom = true,
            hindiMeaning = "शैतान का नाम लिया और शैतान हाज़िर",
            usageTipEn = "Friendly phrase when someone arrives just as you mention them.",
            exampleSentence = "Speak of the devil! We were just talking about your speech.",
            hindiTranslation = "लो, अभी हम आपके भाषण की ही बात कर रहे थे और आप आ गए!",
            level = StudentLevel.INTERMEDIATE
        ),
        DailyPhraseOrIdiom(
            id = "pi_15", phrase = "At the end of the day", isIdiom = false,
            hindiMeaning = "आख़िरकार / सबसे महत्वपूर्ण बात यह है कि",
            usageTipEn = "Used to summarize the most important truth.",
            exampleSentence = "At the end of the day, confidence matters more than perfection.",
            hindiTranslation = "आख़िरकार, पूर्णता से ज़्यादा आत्मविश्वास मायने रखता है।",
            level = StudentLevel.INTERMEDIATE
        ),
        DailyPhraseOrIdiom(
            id = "pi_16", phrase = "Under the weather", isIdiom = true,
            hindiMeaning = "तबीयत ठीक न लगना / थोड़ा अस्वस्थ होना",
            usageTipEn = "Polite way to say you feel mildly sick or tired.",
            exampleSentence = "I felt a little under the weather yesterday, but today I am fine.",
            hindiTranslation = "कल मेरी तबीयत थोड़ी ठीक नहीं थी, पर आज मैं ठीक हूँ।",
            level = StudentLevel.ELEMENTARY
        )
    )

    fun todaysPhrasesAndIdioms(epochDay: Long): List<DailyPhraseOrIdiom> {
        val start = ((epochDay % phrasesAndIdiomsPool.size).toInt()).coerceAtLeast(0)
        return (0 until 10).map { offset ->
            phrasesAndIdiomsPool[(start + offset) % phrasesAndIdiomsPool.size]
        }
    }
}
