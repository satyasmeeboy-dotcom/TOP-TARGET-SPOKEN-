package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.SkillType

enum class GameKind(val label: String) {
    VOCAB_QUIZ("1. Vocabulary Challenge"),
    GRAMMAR_QUIZ("2. Grammar Battle"),
    SENTENCE_BUILD("3. Sentence Making"),
    WORD_GUESS("4. Word Puzzle"),
    TRANSLATION("5. Translation Challenge"),
    FILL_BLANKS("6. Daily Quiz"),
    WORD_MATCHING("7. Word Match"),
    SPELLING("8. Spelling"),
    MEMORY("9. Memory Pairs")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearningGamesView(
    onGameScore: (SkillType, Int, Int, String) -> Unit
) {
    var activeGame by remember { mutableStateOf(GameKind.VOCAB_QUIZ) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("9 Offline English Learning Games", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Play anytime without internet to boost memory, spelling, and fluency!", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(GameKind.entries) { g ->
                    FilterChip(
                        selected = activeGame == g,
                        onClick = { activeGame = g },
                        label = { Text(g.label) }
                    )
                }
            }
        }

        item {
            when (activeGame) {
                GameKind.VOCAB_QUIZ -> QuickMcqGame(
                    title = "Vocabulary Challenge",
                    questions = listOf(
                        Triple("Synonym of 'Diligent'?", listOf("Lazy", "Hardworking", "Angry", "Careless"), 1),
                        Triple("Meaning of 'No Cap' (Gen Z)?", listOf("No hat", "Honestly / For real", "Very sad", "Late"), 1),
                        Triple("Antonym of 'Polite'?", listOf("Gentle", "Kind", "Rude", "Calm"), 2)
                    ),
                    onFinish = { c, t -> onGameScore(SkillType.VOCABULARY, c, t, "vocab_10") }
                )
                GameKind.GRAMMAR_QUIZ -> QuickMcqGame(
                    title = "Grammar Battle",
                    questions = listOf(
                        Triple("Did you ___ the lesson?", listOf("understood", "understand", "understands", "understanding"), 1),
                        Triple("Everyone ___ happy today.", listOf("are", "were", "is", "have"), 2),
                        Triple("If I ___ rich, I would build free schools.", listOf("am", "was", "were", "will be"), 2)
                    ),
                    onFinish = { c, t -> onGameScore(SkillType.GRAMMAR, c, t, "grammar") }
                )
                GameKind.SENTENCE_BUILD -> SentenceFormationGame(onWin = { onGameScore(SkillType.WRITING, 1, 1, "writing") })
                GameKind.WORD_GUESS -> WordGuessingGame(onWin = { onGameScore(SkillType.VOCABULARY, 1, 1, "vocab_10") })
                GameKind.TRANSLATION -> QuickMcqGame(
                    title = "Translation Challenge (Hindi -> English)",
                    questions = listOf(
                        Triple("वह रोज़ अंग्रेज़ी बोलती है।", listOf("She speak English daily.", "She speaks English daily.", "She speaking English daily.", "She spoke English daily."), 1),
                        Triple("कल मैं पुस्तकालय गया था।", listOf("I go to library yesterday.", "I went to the library yesterday.", "I gone to library yesterday.", "I going library yesterday."), 1),
                        Triple("कृपया ध्यान से सुनें।", listOf("Please listen carefully.", "Please listening careful.", "Listen careful please.", "You listen to careful."), 0)
                    ),
                    onFinish = { c, t -> onGameScore(SkillType.GRAMMAR, c, t, "grammar") }
                )
                GameKind.FILL_BLANKS -> QuickMcqGame(
                    title = "Daily Quick Quiz",
                    questions = listOf(
                        Triple("She ___ to school every day.", listOf("go", "goes", "going", "gone"), 1),
                        Triple("Wait for ___ hour please.", listOf("a", "an", "the", "no article"), 1),
                        Triple("We meet ___ Monday morning.", listOf("in", "at", "on", "by"), 2)
                    ),
                    onFinish = { c, t -> onGameScore(SkillType.GRAMMAR, c, t, "grammar") }
                )
                GameKind.WORD_MATCHING -> WordMatchingGame(onWin = { onGameScore(SkillType.VOCABULARY, 4, 4, "vocab_10") })
                GameKind.SPELLING -> SpellingGame(onWin = { c, t -> onGameScore(SkillType.WRITING, c, t, "writing") })
                GameKind.MEMORY -> MemoryPairsGame(onWin = { onGameScore(SkillType.VOCABULARY, 3, 3, "vocab_10") })
            }
        }
    }
}

@Composable
private fun WordMatchingGame(onWin: () -> Unit) {
    val pairs = remember {
        listOf(
            "Polite" to "विनम्र",
            "Fluent" to "धाराप्रवाह",
            "Curious" to "जिज्ञासु",
            "Achieve" to "प्राप्त करना"
        )
    }
    var selectedEnglish by remember { mutableStateOf<String?>(null) }
    val matched = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("Tap an English word, then tap its matching Hindi meaning!") }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Word Matching", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    pairs.forEach { (en, _) ->
                        val isDone = en in matched
                        FilterChip(
                            selected = selectedEnglish == en || isDone,
                            enabled = !isDone,
                            onClick = { selectedEnglish = en },
                            label = { Text(en) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    pairs.reversed().forEach { (en, hi) ->
                        val isDone = en in matched
                        FilterChip(
                            selected = isDone,
                            enabled = !isDone,
                            onClick = {
                                if (selectedEnglish == en) {
                                    matched.add(en)
                                    selectedEnglish = null
                                    status = if (matched.size == pairs.size) {
                                        onWin()
                                        "All 4 matched! Great job!"
                                    } else "Nice match! Keep going!"
                                } else {
                                    status = "Not quite—try another meaning!"
                                }
                            },
                            label = { Text(hi) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            if (matched.size == pairs.size) {
                OutlinedButton(onClick = { matched.clear(); status = "Play again!" }) { Text("Reset Game") }
            }
        }
    }
}

@Composable
private fun QuickMcqGame(
    title: String,
    questions: List<Triple<String, List<String>, Int>>,
    onFinish: (Int, Int) -> Unit
) {
    var index by remember(title) { mutableIntStateOf(0) }
    var score by remember(title) { mutableIntStateOf(0) }
    var feedback by remember(title) { mutableStateOf("") }
    var done by remember(title) { mutableStateOf(false) }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            if (!done) {
                val (prompt, options, correctIdx) = questions[index]
                Text("Q${index + 1}/${questions.size}: $prompt", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                options.forEachIndexed { oIdx, opt ->
                    OutlinedButton(
                        onClick = {
                            if (oIdx == correctIdx) {
                                score++
                                feedback = "Correct!"
                            } else {
                                feedback = "Correct answer: ${options[correctIdx]}"
                            }
                            if (index < questions.lastIndex) {
                                index++
                            } else {
                                done = true
                                onFinish(score, questions.size)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                    ) {
                        Text(opt)
                    }
                }
                if (feedback.isNotBlank()) Text(feedback, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            } else {
                Text("Final Score: $score / ${questions.size}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Button(onClick = { index = 0; score = 0; feedback = ""; done = false }) { Text("Play Again") }
            }
        }
    }
}

@Composable
private fun SpellingGame(onWin: (Int, Int) -> Unit) {
    val words = remember { listOf("CONFIDENT" to "आत्मविश्वासी", "GRAMMAR" to "व्याकरण", "PRACTICE" to "अभ्यास") }
    var idx by remember { mutableIntStateOf(0) }
    var input by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    val (target, hint) = words[idx]
    val scrambled = remember(idx) { target.toList().shuffled().joinToString(" ") }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Spelling Unscramble Game", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Hint ($hint): $scrambled", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Type correct spelling") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                if (input.trim().equals(target, ignoreCase = true)) {
                    msg = "Spot on! '$target' is right!"
                    onWin(1, 1)
                    idx = (idx + 1) % words.size
                    input = ""
                } else {
                    msg = "Try again! Starts with '${target.first()}'."
                }
            }) { Text("Check Spelling") }
            if (msg.isNotBlank()) Text(msg, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun MemoryPairsGame(onWin: () -> Unit) {
    val cards = remember { listOf("Glow Up", "निखार", "Resilient", "मजबूत", "Clarity", "स्पष्टता").shuffled() }
    val expected = mapOf("Glow Up" to "निखार", "निखार" to "Glow Up", "Resilient" to "मजबूत", "मजबूत" to "Resilient", "Clarity" to "स्पष्टता", "स्पष्टता" to "Clarity")
    val flipped = remember { mutableStateListOf<Int>() }
    val solved = remember { mutableStateListOf<Int>() }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Memory Card Pairs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Tap two cards to pair English words with Hindi meanings!", style = MaterialTheme.typography.bodySmall)
            cards.chunked(2).forEachIndexed { rIdx, row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    row.forEachIndexed { cIdx, text ->
                        val cardIdx = rIdx * 2 + cIdx
                        val visible = cardIdx in flipped || cardIdx in solved
                        Surface(
                            color = if (cardIdx in solved) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    if (cardIdx !in solved && cardIdx !in flipped) {
                                        flipped.add(cardIdx)
                                        if (flipped.size == 2) {
                                            val a = flipped[0]; val b = flipped[1]
                                            if (expected[cards[a]] == cards[b]) {
                                                solved.addAll(listOf(a, b))
                                                flipped.clear()
                                                if (solved.size == cards.size) onWin()
                                            }
                                        } else if (flipped.size > 2) {
                                            flipped.clear()
                                            flipped.add(cardIdx)
                                        }
                                    }
                                }
                        ) {
                            Row(Modifier.padding(8.dp)) {
                                Text(if (visible) text else "?", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            if (solved.size == cards.size) {
                Text("All pairs found!", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                OutlinedButton(onClick = { solved.clear(); flipped.clear() }) { Text("Shuffle Again") }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SentenceFormationGame(onWin: () -> Unit) {
    val target = "We practice English every morning"
    val tiles = remember { listOf("morning", "We", "every", "English", "practice") }
    val built = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("") }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Sentence Formation (SVO Builder)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Tap tiles in order to form: 'हम हर सुबह अंग्रेज़ी का अभ्यास करते हैं।'", style = MaterialTheme.typography.bodySmall)
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (built.isEmpty()) "(Tap words below)" else built.joinToString(" "),
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                tiles.forEach { w ->
                    FilterChip(
                        selected = w in built,
                        onClick = { if (w !in built) built.add(w) else built.remove(w) },
                        label = { Text(w) }
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    if (built.joinToString(" ") == target) {
                        status = "Perfect SVO order!"
                        onWin()
                    } else {
                        status = "Hint: Start with 'We practice...'"
                    }
                }) { Text("Check Order") }
                OutlinedButton(onClick = { built.clear(); status = "" }) { Text("Clear") }
            }
            if (status.isNotBlank()) Text(status, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun WordGuessingGame(onWin: () -> Unit) {
    val secret = "FLUENT"
    var guessed by remember { mutableStateOf(setOf('F', 'T')) }
    var letterInput by remember { mutableStateOf("") }
    val masked = secret.map { if (it in guessed) it else '_' }.joinToString(" ")
    val won = secret.all { it in guessed }

    Card(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Word Guessing Game", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Hindi Clue: धाराप्रवाह (बिना अटके बोलने वाला)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Text(masked, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            if (!won) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = letterInput,
                        onValueChange = { letterInput = it.take(1).uppercase() },
                        label = { Text("1 Letter") },
                        singleLine = true,
                        modifier = Modifier.width(110.dp).testTag("word_guess_letter")
                    )
                    Button(onClick = {
                        letterInput.firstOrNull()?.let { ch ->
                            guessed = guessed + ch
                            if (secret.all { it in guessed }) onWin()
                        }
                        letterInput = ""
                    }) { Text("Guess") }
                }
            } else {
                Text("Awesome! The word is FLUENT!", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                OutlinedButton(onClick = { guessed = setOf('F', 'T') }) { Text("Play Again") }
            }
        }
    }
}
