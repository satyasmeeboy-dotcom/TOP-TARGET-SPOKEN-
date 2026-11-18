package com.example.data.curriculum

import com.example.model.DailyRoutineItem
import com.example.model.RoutinePeriod

object DailyRoutineCatalog {
    val items: List<DailyRoutineItem> = listOf(
        // Morning
        DailyRoutineItem(
            id = "rt_m1",
            period = RoutinePeriod.MORNING,
            timeLabel = "6:00 AM",
            titleEn = "Wake Up & Fresh Start",
            titleHi = "सुबह उठना और पानी पीना",
            sentenceEn = "I wake up early at 6:00 AM and drink a glass of warm water.",
            sentenceHi = "मैं सुबह 6:00 बजे जल्दी उठता/उठती हूँ और एक गिलास गुनगुना पानी पीता/पीती हूँ।",
            pastTenseVariantEn = "This morning, I woke up early at 6:00 AM and drank a glass of warm water.",
            pastTenseVariantHi = "आज सुबह मैं 6:00 बजे जल्दी उठा/उठी और मैंने एक गिलास गुनगुना पानी पिया।",
            grammarTipEn = "Daily habit uses Simple Present V1 ('wake up', 'drink') after 'I'.",
            grammarTipHi = "रोज़ की आदत के लिए 'I' के साथ क्रिया का पहला रूप (V1: wake up, drink) लगाते हैं।"
        ),
        DailyRoutineItem(
            id = "rt_m2",
            period = RoutinePeriod.MORNING,
            timeLabel = "6:30 AM",
            titleEn = "Brush & Get Ready",
            titleHi = "ब्रश करना और तैयार होना",
            sentenceEn = "Then I brush my teeth, take a bath, and get ready for the day.",
            sentenceHi = "फिर मैं अपने दाँत ब्रश करता/करती हूँ, नहाता/नहाती हूँ और दिन के लिए तैयार होता/होती हूँ।",
            pastTenseVariantEn = "Then I brushed my teeth, took a bath, and got ready for the day.",
            pastTenseVariantHi = "फिर मैंने ब्रश किया, नहाया और मैं दिन के लिए तैयार हुआ/हुई।",
            grammarTipEn = "Use 'Then' to smoothly connect your next morning action.",
            grammarTipHi = "अगले काम को जोड़ने के लिए 'Then' (फिर) का प्रयोग करें।"
        ),
        DailyRoutineItem(
            id = "rt_m3",
            period = RoutinePeriod.MORNING,
            timeLabel = "7:30 AM",
            titleEn = "Healthy Breakfast",
            titleHi = "पौष्टिक नाश्ता करना",
            sentenceEn = "I eat a healthy breakfast with my family at 7:30 AM.",
            sentenceHi = "मैं सुबह 7:30 बजे अपने परिवार के साथ पौष्टिक नाश्ता करता/करती हूँ।",
            pastTenseVariantEn = "I ate a healthy breakfast with my family at 7:30 AM.",
            pastTenseVariantHi = "मैंने सुबह 7:30 बजे अपने परिवार के साथ पौष्टिक नाश्ता किया।",
            grammarTipEn = "Always use 'at' before exact clock times like 'at 7:30 AM'.",
            grammarTipHi = "घड़ी के निश्चित समय (7:30 AM) से पहले हमेशा 'at' लगाते हैं।"
        ),
        DailyRoutineItem(
            id = "rt_m4",
            period = RoutinePeriod.MORNING,
            timeLabel = "8:15 AM",
            titleEn = "Morning English Practice",
            titleHi = "सुबह का अंग्रेज़ी अभ्यास",
            sentenceEn = "I practice speaking five English sentences aloud every morning.",
            sentenceHi = "मैं हर सुबह पाँच अंग्रेज़ी वाक्य जोर से बोलने का अभ्यास करता/करती हूँ।",
            pastTenseVariantEn = "I practiced speaking five English sentences aloud this morning.",
            pastTenseVariantHi = "मैंने आज सुबह पाँच अंग्रेज़ी वाक्य जोर से बोलने का अभ्यास किया।",
            grammarTipEn = "'practice' + verb-ing ('practice speaking') is natural spoken English.",
            grammarTipHi = "'practice' के बाद कार्य में '-ing' (practice speaking) लगाना स्वाभाविक है।"
        ),

        // Afternoon
        DailyRoutineItem(
            id = "rt_a1",
            period = RoutinePeriod.AFTERNOON,
            timeLabel = "12:30 PM",
            titleEn = "Classes or Daily Work",
            titleHi = "कक्षा या दैनिक कार्य",
            sentenceEn = "In the afternoon, I attend my classes and take neat notes.",
            sentenceHi = "दोपहर में मैं अपनी कक्षाओं में शामिल होता/होती हूँ और साफ नोट्स लिखता/लिखती हूँ।",
            pastTenseVariantEn = "In the afternoon, I attended my classes and took neat notes.",
            pastTenseVariantHi = "दोपहर में मैंने अपनी कक्षाएं लीं और साफ नोट्स लिखे।",
            grammarTipEn = "Say 'In the afternoon' (with 'in' + 'the'), never 'at afternoon'.",
            grammarTipHi = "दोपहर के लिए हमेशा 'In the afternoon' बोलें, 'at afternoon' गलत है।"
        ),
        DailyRoutineItem(
            id = "rt_a2",
            period = RoutinePeriod.AFTERNOON,
            timeLabel = "1:30 PM",
            titleEn = "Lunch Break",
            titleHi = "दोपहर का भोजन",
            sentenceEn = "I have my lunch around 1:30 PM and wash my hands beforehand.",
            sentenceHi = "मैं लगभग 1:30 बजे दोपहर का भोजन करता/करती हूँ और पहले हाथ धोता/धोती हूँ।",
            pastTenseVariantEn = "I had my lunch around 1:30 PM and rested for a few minutes.",
            pastTenseVariantHi = "मैंने लगभग 1:30 बजे दोपहर का भोजन किया और कुछ मिनट आराम किया।",
            grammarTipEn = "'have lunch' is a common natural alternative to 'eat lunch'.",
            grammarTipHi = "'eat lunch' की जगह 'have lunch' बोलना अंग्रेज़ी में बहुत प्रचलित है।"
        ),
        DailyRoutineItem(
            id = "rt_a3",
            period = RoutinePeriod.AFTERNOON,
            timeLabel = "3:00 PM",
            titleEn = "Short Rest & Hydration",
            titleHi = "थोड़ा आराम और पानी पीना",
            sentenceEn = "After lunch, I take a short rest and drink plenty of water.",
            sentenceHi = "भोजन के बाद मैं थोड़ा आराम करता/करती हूँ और पर्याप्त पानी पीता/पीती हूँ।",
            pastTenseVariantEn = "After lunch, I took a short rest and drank plenty of water.",
            pastTenseVariantHi = "भोजन के बाद मैंने थोड़ा आराम किया और खूब पानी पिया।",
            grammarTipEn = "Past form of 'take' is 'took', and past form of 'drink' is 'drank'.",
            grammarTipHi = "'take' का Past रूप 'took' और 'drink' का 'drank' होता है।"
        ),
        DailyRoutineItem(
            id = "rt_a4",
            period = RoutinePeriod.AFTERNOON,
            timeLabel = "4:15 PM",
            titleEn = "Revise Daily Words",
            titleHi = "दैनिक शब्द दोहराना",
            sentenceEn = "I review ten new vocabulary words before going outside.",
            sentenceHi = "बाहर जाने से पहले मैं दस नए शब्दावली शब्द दोहराता/दोहराती हूँ।",
            pastTenseVariantEn = "I reviewed ten new vocabulary words before going outside.",
            pastTenseVariantHi = "बाहर जाने से पहले मैंने दस नए शब्द दोहराए।",
            grammarTipEn = "After 'before' or 'after', use verb-ing ('before going').",
            grammarTipHi = "'before' या 'after' के तुरंत बाद क्रिया में '-ing' (before going) आता है।"
        ),

        // Evening
        DailyRoutineItem(
            id = "rt_e1",
            period = RoutinePeriod.EVENING,
            timeLabel = "5:30 PM",
            titleEn = "Evening Walk or Exercise",
            titleHi = "शाम की सैर या व्यायाम",
            sentenceEn = "In the evening, I go for a brisk walk to stay active.",
            sentenceHi = "शाम को मैं सक्रिय रहने के लिए तेज़ टहलने जाता/जाती हूँ।",
            pastTenseVariantEn = "Yesterday evening, I went for a brisk walk in the park.",
            pastTenseVariantHi = "कल शाम मैं पार्क में तेज़ टहलने गया/गई।",
            grammarTipEn = "Never say 'go to walk'; say 'go for a walk'.",
            grammarTipHi = "'go to walk' नहीं, बल्कि हमेशा 'go for a walk' बोलें।"
        ),
        DailyRoutineItem(
            id = "rt_e2",
            period = RoutinePeriod.EVENING,
            timeLabel = "6:30 PM",
            titleEn = "Help at Home & Tea Time",
            titleHi = "घर में मदद और चाय का समय",
            sentenceEn = "I help my parents with small chores and chat with them politely.",
            sentenceHi = "मैं छोटे कामों में माता-पिता की मदद करता/करती हूँ और उनसे प्रेम से बात करता/करती हूँ।",
            pastTenseVariantEn = "I helped my parents with chores and chatted with them warmly.",
            pastTenseVariantHi = "मैंने कामों में माता-पिता की मदद की और उनसे बातचीत की।",
            grammarTipEn = "Plural 'parents' takes V1 'help' with no '-s' in Present Simple.",
            grammarTipHi = "'I' और बहुवचन कर्ता के साथ क्रिया में '-s' नहीं लगाते ('I help')।"
        ),
        DailyRoutineItem(
            id = "rt_e3",
            period = RoutinePeriod.EVENING,
            timeLabel = "7:15 PM",
            titleEn = "Homework & Grammar Quiz",
            titleHi = "गृहकार्य और व्याकरण अभ्यास",
            sentenceEn = "I complete my homework and solve one grammar quiz carefully.",
            sentenceHi = "मैं अपना गृहकार्य पूरा करता/करती हूँ और ध्यान से एक व्याकरण क्विज़ हल करता/करती हूँ।",
            pastTenseVariantEn = "I completed my homework and solved one grammar quiz carefully.",
            pastTenseVariantHi = "मैंने अपना गृहकार्य पूरा किया और एक व्याकरण क्विज़ हल किया।",
            grammarTipEn = "Adverbs like 'carefully' usually go at the end of a simple sentence.",
            grammarTipHi = "'carefully' जैसे क्रिया-विशेषण (Adverb) सामान्यतः वाक्य के अंत में आते हैं।"
        ),
        DailyRoutineItem(
            id = "rt_e4",
            period = RoutinePeriod.EVENING,
            timeLabel = "7:45 PM",
            titleEn = "Listen to English News or Story",
            titleHi = "अंग्रेज़ी कहानी या समाचार सुनना",
            sentenceEn = "I listen to a short English story to sharpen my pronunciation.",
            sentenceHi = "अपना उच्चारण निखारने के लिए मैं एक छोटी अंग्रेज़ी कहानी सुनता/सुनती हूँ।",
            pastTenseVariantEn = "I listened to a short English story to sharpen my pronunciation.",
            pastTenseVariantHi = "मैंने अपना उच्चारण बेहतर करने के लिए एक छोटी अंग्रेज़ी कहानी सुनी।",
            grammarTipEn = "Always follow 'listen' with 'to' ('listen to a story').",
            grammarTipHi = "'listen' के साथ हमेशा 'to' लगाते हैं ('listen to a story')।"
        ),

        // Night
        DailyRoutineItem(
            id = "rt_n1",
            period = RoutinePeriod.NIGHT,
            timeLabel = "8:30 PM",
            titleEn = "Family Dinner",
            titleHi = "परिवार के साथ रात का भोजन",
            sentenceEn = "We eat dinner together at 8:30 PM without looking at our phones.",
            sentenceHi = "हम रात 8:30 बजे बिना फोन देखे साथ में रात का भोजन करते हैं।",
            pastTenseVariantEn = "We ate dinner together at 8:30 PM and shared good stories.",
            pastTenseVariantHi = "हमने रात 8:30 बजे साथ में भोजन किया और अच्छी बातें साझा कीं।",
            grammarTipEn = "Note that 'without' is followed by verb-ing ('without looking').",
            grammarTipHi = "'without' के बाद हमेशा '-ing' वाली क्रिया ('without looking') आती है।"
        ),
        DailyRoutineItem(
            id = "rt_n2",
            period = RoutinePeriod.NIGHT,
            timeLabel = "9:15 PM",
            titleEn = "Daily English Diary",
            titleHi = "दैनिक अंग्रेज़ी डायरी लिखना",
            sentenceEn = "Before bed, I write three short lines about what I learned today.",
            sentenceHi = "सोने से पहले मैं आज जो सीखा, उस पर तीन छोटी पंक्तियाँ लिखता/लिखती हूँ।",
            pastTenseVariantEn = "Last night, I wrote three short lines about what I learned.",
            pastTenseVariantHi = "कल रात मैंने जो सीखा था, उस पर तीन छोटी पंक्तियाँ लिखीं।",
            grammarTipEn = "Use 'Last night' (not 'Yesterday night') for yesterday's night.",
            grammarTipHi = "बीती रात के लिए 'Yesterday night' नहीं बल्कि 'Last night' बोलें।"
        ),
        DailyRoutineItem(
            id = "rt_n3",
            period = RoutinePeriod.NIGHT,
            timeLabel = "9:45 PM",
            titleEn = "Prepare for Tomorrow",
            titleHi = "कल की तैयारी करना",
            sentenceEn = "I pack my bag and plan my study target for tomorrow morning.",
            sentenceHi = "मैं अपना बैग तैयार करता/करती हूँ और कल सुबह के अध्ययन का लक्ष्य तय करता/करती हूँ।",
            pastTenseVariantEn = "I packed my bag and planned my study target for this morning.",
            pastTenseVariantHi = "मैंने अपना बैग तैयार किया और आज सुबह के अध्ययन का लक्ष्य बनाया।",
            grammarTipEn = "Keep spellings sharp: 'tomorrow' has one 'm' and double 'r'.",
            grammarTipHi = "वर्तनी ध्यान रखें: 'tomorrow' में एक 'm' और दो 'r' होते हैं।"
        ),
        DailyRoutineItem(
            id = "rt_n4",
            period = RoutinePeriod.NIGHT,
            timeLabel = "10:15 PM",
            titleEn = "Peaceful Sleep",
            titleHi = "शांतिपूर्ण नींद",
            sentenceEn = "Finally, I switch off the lights and go to sleep at 10:15 PM.",
            sentenceHi = "अंत में, मैं लाइट बंद करता/करती हूँ और रात 10:15 बजे सो जाता/जाती हूँ।",
            pastTenseVariantEn = "Finally, I switched off the lights and went to sleep at 10:15 PM.",
            pastTenseVariantHi = "अंत में मैंने लाइट बंद की और 10:15 बजे सो गया/गई।",
            grammarTipEn = "Say 'at night' (not 'in night') and 'go to sleep' / 'go to bed'.",
            grammarTipHi = "रात के लिए 'in night' नहीं, बल्कि 'at night' का प्रयोग करें।"
        )
    )

    val starterTemplates: List<Pair<String, String>> = listOf(
        "Morning Routine" to "I wake up at 6 AM, brush my teeth, and practice spoken English.",
        "Study Routine" to "In the afternoon, I attend my classes and revise ten new vocabulary words.",
        "Night Routine" to "Before bed, I write three sentences in my diary and go to sleep at 10 PM."
    )
}
