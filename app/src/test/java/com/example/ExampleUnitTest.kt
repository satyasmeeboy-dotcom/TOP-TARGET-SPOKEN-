package com.example

import com.example.data.ai.SampleAiRepository
import com.example.data.curriculum.GrammarCurriculum
import com.example.data.curriculum.PracticeAndNewsCatalog
import com.example.data.curriculum.WordsCatalog
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun curriculum_hasAll18GrammarTopicsWithHindiAndMcqs() {
        val topics = GrammarCurriculum.allTopics
        assertEquals(18, topics.size)
        topics.forEach { topic ->
            assertTrue(topic.explanationHi.isNotBlank())
            assertTrue(topic.examples.isNotEmpty())
            assertTrue(topic.dailyLifeExamples.isNotEmpty())
            assertTrue(topic.mcqQuestions.isNotEmpty())
            assertTrue(topic.homeworkEn.isNotBlank())
        }
    }

    @Test
    fun dailyWords_returnsTenUniqueGenZVocabAndPhrasesIdioms() {
        val genZ = WordsCatalog.todaysTenGenZWords(20400L)
        val vocab = WordsCatalog.todaysTenVocabWords(20400L)
        val phrases = WordsCatalog.todaysPhrasesAndIdioms(20400L)
        assertEquals(10, genZ.map { it.id }.distinct().size)
        assertEquals(10, vocab.map { it.id }.distinct().size)
        assertEquals(10, phrases.map { it.id }.distinct().size)
        assertTrue(phrases.any { it.isIdiom })
        assertEquals(9, PracticeAndNewsCatalog.speakingScenarios.size)
        assertEquals(7, PracticeAndNewsCatalog.writingPrompts.size)

        val ai = SampleAiRepository()
        val pair = ai.bilingualComplimentPair(2)
        assertTrue(pair.first.isNotBlank() && pair.second.isNotBlank())
    }

    @Test
    fun sampleAiRepository_detectsAndExplainsGrammarMistakes() = runTest {
        val ai = SampleAiRepository()

        val articleFix = ai.analyzeGrammar("She ate a apple.")
        assertFalse(articleFix.isCorrect)
        assertTrue(articleFix.correctedSentence.contains("an apple", ignoreCase = true))
        assertTrue(articleFix.grammarRuleHi.isNotBlank())
        assertEquals(2, articleFix.extraExamples.size)

        val svaFix = ai.analyzeGrammar("Everyone are happy today.")
        assertFalse(svaFix.isCorrect)
        assertTrue(svaFix.correctedSentence.contains("Everyone is", ignoreCase = true))

        val tenseFix = ai.analyzeGrammar("I go to market yesterday.")
        assertFalse(tenseFix.isCorrect)
        assertTrue(tenseFix.correctedSentence.contains("went", ignoreCase = true))

        val clean = ai.analyzeGrammar("I practice English every morning.")
        assertTrue(clean.isCorrect)
        assertTrue(clean.isSampleMode)
    }

    @Test
    fun sampleAiRepository_usesCheniOnlyInEnglishAcrossAllTexts() = runTest {
        val ai = SampleAiRepository()
        val topic = GrammarCurriculum.allTopics.first()
        val reply = ai.teachNextTurn(
            studentReply = "Hi",
            currentTopic = topic,
            currentStage = com.example.model.TeacherStage.WELCOME,
            studentLevel = com.example.model.StudentLevel.BEGINNER,
            weakCategories = emptyList(),
            nickname = "Aarav"
        )
        assertTrue(reply.englishText.contains("CHENI"))
        assertTrue(reply.hindiText.contains("CHENI"))
        assertFalse(reply.hindiText.contains("चेनी"))

        val firstPair = ai.bilingualComplimentPair(0)
        assertTrue(firstPair.first.contains("CHENI") && firstPair.second.contains("CHENI"))
        assertFalse(firstPair.second.contains("चेनी"))
    }

    @Test
    fun dailyRoutineCatalog_coversAllPeriodsWithBilingualAndPastVariants() {
        val items = com.example.data.curriculum.DailyRoutineCatalog.items
        assertEquals(16, items.size)
        assertEquals(16, items.map { it.id }.distinct().size)
        com.example.model.RoutinePeriod.entries.forEach { period ->
            assertTrue(items.any { it.period == period })
        }
        items.forEach { item ->
            assertTrue(item.sentenceEn.isNotBlank())
            assertTrue(item.sentenceHi.isNotBlank())
            assertTrue(item.pastTenseVariantEn.isNotBlank())
            assertTrue(item.pastTenseVariantHi.isNotBlank())
            assertFalse(item.sentenceHi.contains("चेनी"))
        }
        assertTrue(GrammarCurriculum.dailyStudyPlanSpecs.any { it.key == "daily_routine" })
    }

    @Test
    fun dailyPracticeCatalog_generatesTenUniqueQuestionsCoveringAllEightSkills() {
        val day1 = com.example.data.curriculum.DailyPracticeCatalog.generateTodaysTenQuestions(20400L, listOf("preposition"))
        val day2 = com.example.data.curriculum.DailyPracticeCatalog.generateTodaysTenQuestions(20401L, emptyList())
        assertEquals(10, day1.size)
        assertEquals(10, day1.map { it.id }.distinct().size)
        assertEquals(10, day2.size)
        assertEquals(10, day2.map { it.id }.distinct().size)

        val kindsCovered = day1.map { it.kind }.toSet()
        assertEquals(com.example.model.PracticeQuestionKind.entries.toSet(), kindsCovered)
        assertTrue(day1.map { it.id } != day2.map { it.id })
    }

    @Test
    fun dailyNewsArticles_allIncludeFivePracticeQuestions() {
        val articles = PracticeAndNewsCatalog.dailyNewsArticles
        assertTrue(articles.isNotEmpty())
        articles.forEach { article ->
            assertEquals(5, article.quiz.size)
            assertTrue(article.difficultWords.isNotEmpty())
            assertTrue(article.bodyHi.isNotBlank())
        }
    }

    @Test
    fun sampleAiRepository_handlesPrepositionWordOrderAndHonestCompliments() = runTest {
        val ai = SampleAiRepository()

        val prepFix = ai.analyzeGrammar("She is good in English.")
        assertFalse(prepFix.isCorrect)
        assertEquals("Preposition Mistake", prepFix.mistakeCategory)
        assertTrue(prepFix.correctedSentence.contains("good at", ignoreCase = true))

        val orderFix = ai.analyzeGrammar("Always I practice English.")
        assertFalse(orderFix.isCorrect)
        assertEquals("Word Order Mistake", orderFix.mistakeCategory)
        assertTrue(orderFix.correctedSentence.contains("I always practice", ignoreCase = true))

        val tenseBeV1Fix = ai.analyzeGrammar("I am go to school.")
        assertFalse(tenseBeV1Fix.isCorrect)
        assertEquals("Tense Mistake", tenseBeV1Fix.mistakeCategory)
        assertTrue(tenseBeV1Fix.correctedSentence.contains("am going", ignoreCase = true))

        val lowCompliment = ai.performanceCompliment(scoreRatio = 0.2f, isCorrect = false, context = "grammar")
        assertFalse(lowCompliment.contains("Excellent"))
        assertTrue(lowCompliment.contains("CHENI"))

        val highCompliment = ai.performanceCompliment(scoreRatio = 0.95f, isCorrect = true, context = "grammar")
        assertTrue(highCompliment.contains("Excellent"))
    }
}
