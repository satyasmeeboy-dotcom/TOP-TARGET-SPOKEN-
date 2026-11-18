package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.SkillType
import com.example.ui.components.FolderAccent
import com.example.ui.components.FolderPresets
import com.example.ui.components.StudyFolderCard
import com.example.ui.theme.PureWhite

enum class GameKind(
    val label: String,
    val tabLabel: String,
    val subtitle: String,
    val badge: String,
    val icon: ImageVector,
    val accent: FolderAccent
) {
    VOCAB_QUIZ("Vocabulary Challenge", "GAME 1 • VOCAB", "Test synonyms, antonyms & Gen Z meanings", "3 Qs", Icons.Default.AutoAwesome, FolderPresets.Emerald),
    GRAMMAR_QUIZ("Grammar Battle", "GAME 2 • BATTLE", "Fast tense & agreement questions", "3 Qs", Icons.Default.Bolt, FolderPresets.Indigo),
    SENTENCE_BUILD("Sentence Making", "GAME 3 • BUILDER", "Arrange word tiles into correct SVO order", "Builder", Icons.Default.Extension, FolderPresets.Sky),
    WORD_GUESS("Word Puzzle", "GAME 4 • PUZZLE", "Crack the missing letters with Hindi clue", "Puzzle", Icons.Default.Lightbulb, FolderPresets.Amber),
    TRANSLATION("Translation Challenge", "GAME 5 • HINDI→ENG", "Pick natural English for Hindi sentences", "3 Qs", Icons.Default.Translate, FolderPresets.Violet),
    FILL_BLANKS("Daily Quiz", "GAME 6 • FILL IN", "Master articles, prepositions & verbs", "3 Qs", Icons.Default.Quiz, FolderPresets.Teal),
    WORD_MATCHING("Word Matching", "GAME 7 • MATCH", "Pair English words with Hindi meanings", "4 Pairs", Icons.Default.GridView, FolderPresets.Coral),
    SPELLING("Spelling Unscramble", "GAME 8 • SPELLING", "Unscramble letters into crisp spellings", "3 Words", Icons.Default.Spellcheck, FolderPresets.Rose),
    MEMORY("Memory Pairs", "GAME 9 • MEMORY", "Flip & match bilingual memory cards", "6 Cards", Icons.Default.Psychology, FolderPresets.Emerald)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearningGamesView(
    onGameScore: (SkillType, Int, Int, String) -> Unit
) {
    var activeGame by remember { mutableStateOf<GameKind?>(null) }

    BackHandler(enabled = activeGame != null) {
        activeGame = null
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val isWide = maxWidth >= 600.dp

        if (activeGame == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().widthIn(max = 720.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.5.dp, FolderPresets.Coral.primary.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(FolderPresets.Coral.softTint, FolderPresets.Amber.softTint)
                                    )
                                )
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "9 Offline English Learning Games",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = FolderPresets.Coral.primary
                            )
                            Text(
                                "Tap any colourful game folder to play offline & boost your English!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                val rows = if (isWide) GameKind.entries.chunked(2) else GameKind.entries.map { listOf(it) }
                items(rows) { rowGames ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowGames.forEach { g ->
                            StudyFolderCard(
                                tabLabel = g.tabLabel,
                                title = g.label,
                                subtitle = g.subtitle,
                                badgeText = g.badge,
                                icon = g.icon,
                                accent = g.accent,
                                actionLabel = "Play Game",
                                onClick = { activeGame = g },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("game_folder_${g.name.lowercase()}")
                            )
                        }
                        if (isWide && rowGames.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
        } else {
            val game = activeGame!!
            LazyColumn(
                modifier = Modifier.fillMaxSize().widthIn(max = 720.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Surface(
                        color = game.accent.softTint,
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(1.5.dp, game.accent.primary.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { activeGame = null },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "All 9 Games", tint = game.accent.primary)
                                Spacer(Modifier.width(4.dp))
                                Text("All 9 Games", color = game.accent.primary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = game.label,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = game.accent.primary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                item {
                    when (game) {
                        GameKind.VOCAB_QUIZ -> QuickMcqGame(
                            title = "Vocabulary Challenge",
                            accent = game.accent,
                            questions = listOf(
                                Triple("Synonym of 'Diligent'?", listOf("Lazy", "Hardworking", "Angry", "Careless"), 1),
                                Triple("Meaning of 'No Cap' (Gen Z)?", listOf("No hat", "Honestly / For real", "Very sad", "Late"), 1),
                                Triple("Antonym of 'Polite'?", listOf("Gentle", "Kind", "Rude", "Calm"), 2)
                            ),
                            onFinish = { c, t -> onGameScore(SkillType.VOCABULARY, c, t, "vocab_10") }
                        )
                        GameKind.GRAMMAR_QUIZ -> QuickMcqGame(
                            title = "Grammar Battle",
                            accent = game.accent,
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
                            accent = game.accent,
                            questions = listOf(
                                Triple("वह रोज़ अंग्रेज़ी बोलती है।", listOf("She speak English daily.", "She speaks English daily.", "She speaking English daily.", "She spoke English daily."), 1),
                                Triple("कल मैं पुस्तकालय गया था।", listOf("I go to library yesterday.", "I went to the library yesterday.", "I gone to library yesterday.", "I going library yesterday."), 1),
                                Triple("कृपया ध्यान से सुनें।", listOf("Please listen carefully.", "Please listening careful.", "Listen careful please.", "You listen to careful."), 0)
                            ),
                            onFinish = { c, t -> onGameScore(SkillType.GRAMMAR, c, t, "grammar") }
                        )
                        GameKind.FILL_BLANKS -> QuickMcqGame(
                            title = "Daily Quick Quiz",
                            accent = game.accent,
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
    }
}

@Composable
private fun WordMatchingGame(onWin: () -> Unit) {
    val accent = GameKind.WORD_MATCHING.accent
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

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Word Matching", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                Text(status, style = MaterialTheme.typography.bodyMedium, color = accent.primary)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        pairs.forEach { (en, _) ->
                            val isDone = en in matched
                            FilterChip(
                                selected = selectedEnglish == en || isDone,
                                enabled = !isDone,
                                onClick = { selectedEnglish = en },
                                label = { Text(en, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                label = { Text(hi, style = MaterialTheme.typography.bodyMedium) },
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
}

@Composable
private fun QuickMcqGame(
    title: String,
    accent: FolderAccent,
    questions: List<Triple<String, List<String>, Int>>,
    onFinish: (Int, Int) -> Unit
) {
    var index by remember(title) { mutableIntStateOf(0) }
    var score by remember(title) { mutableIntStateOf(0) }
    var feedback by remember(title) { mutableStateOf("") }
    var done by remember(title) { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                if (!done) {
                    val (prompt, options, correctIdx) = questions[index]
                    Surface(
                        color = accent.softTint,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Q${index + 1}/${questions.size}: $prompt",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
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
                            border = BorderStroke(1.2.dp, accent.primary.copy(alpha = 0.55f)),
                            modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                        ) {
                            Text(opt, style = MaterialTheme.typography.bodyLarge, color = accent.primary)
                        }
                    }
                    if (feedback.isNotBlank()) Text(feedback, style = MaterialTheme.typography.labelLarge, color = accent.primary)
                } else {
                    Text("Final Score: $score / ${questions.size}", style = MaterialTheme.typography.headlineSmall, color = accent.primary, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = { index = 0; score = 0; feedback = ""; done = false },
                        colors = ButtonDefaults.buttonColors(containerColor = accent.primary)
                    ) { Text("Play Again") }
                }
            }
        }
    }
}

@Composable
private fun SpellingGame(onWin: (Int, Int) -> Unit) {
    val accent = GameKind.SPELLING.accent
    val words = remember { listOf("CONFIDENT" to "आत्मविश्वासी", "GRAMMAR" to "व्याकरण", "PRACTICE" to "अभ्यास") }
    var idx by remember { mutableIntStateOf(0) }
    var input by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    val (target, hint) = words[idx]
    val scrambled = remember(idx) { target.toList().shuffled().joinToString(" ") }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Spelling Unscramble Game", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                Surface(color = accent.softTint, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Hint ($hint): $scrambled",
                        style = MaterialTheme.typography.titleMedium,
                        color = accent.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(14.dp)
                    )
                }
                OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Type correct spelling") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Button(
                    onClick = {
                        if (input.trim().equals(target, ignoreCase = true)) {
                            msg = "Spot on! '$target' is right!"
                            onWin(1, 1)
                            idx = (idx + 1) % words.size
                            input = ""
                        } else {
                            msg = "Try again! Starts with '${target.first()}'."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accent.primary)
                ) { Text("Check Spelling") }
                if (msg.isNotBlank()) Text(msg, style = MaterialTheme.typography.labelLarge, color = accent.primary)
            }
        }
    }
}

@Composable
private fun MemoryPairsGame(onWin: () -> Unit) {
    val accent = GameKind.MEMORY.accent
    val cards = remember { listOf("Glow Up", "निखार", "Resilient", "मजबूत", "Clarity", "स्पष्टता").shuffled() }
    val expected = mapOf("Glow Up" to "निखार", "निखार" to "Glow Up", "Resilient" to "मजबूत", "मजबूत" to "Resilient", "Clarity" to "स्पष्टता", "स्पष्टता" to "Clarity")
    val flipped = remember { mutableStateListOf<Int>() }
    val solved = remember { mutableStateListOf<Int>() }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Memory Card Pairs", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                Text("Tap two cards to pair English words with Hindi meanings!", style = MaterialTheme.typography.bodyMedium)
                cards.chunked(2).forEachIndexed { rIdx, row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        row.forEachIndexed { cIdx, text ->
                            val cardIdx = rIdx * 2 + cIdx
                            val visible = cardIdx in flipped || cardIdx in solved
                            Surface(
                                color = if (cardIdx in solved) accent.softTint else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.2.dp, if (cardIdx in solved) accent.primary else MaterialTheme.colorScheme.outline),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp)
                                    .clip(RoundedCornerShape(14.dp))
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
                                Box(Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.Center) {
                                    Text(if (visible) text else "?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                if (solved.size == cards.size) {
                    Text("All pairs found!", color = accent.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    OutlinedButton(onClick = { solved.clear(); flipped.clear() }) { Text("Shuffle Again") }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SentenceFormationGame(onWin: () -> Unit) {
    val accent = GameKind.SENTENCE_BUILD.accent
    val target = "We practice English every morning"
    val tiles = remember { listOf("morning", "We", "every", "English", "practice") }
    val built = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Sentence Formation (SVO Builder)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                Text("Tap tiles in order to form: 'हम हर सुबह अंग्रेज़ी का अभ्यास करते हैं।'", style = MaterialTheme.typography.bodyMedium)
                Surface(color = accent.softTint, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (built.isEmpty()) "(Tap words below)" else built.joinToString(" "),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(14.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tiles.forEach { w ->
                        FilterChip(
                            selected = w in built,
                            onClick = { if (w !in built) built.add(w) else built.remove(w) },
                            label = { Text(w, style = MaterialTheme.typography.bodyMedium) }
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            if (built.joinToString(" ") == target) {
                                status = "Perfect SVO order!"
                                onWin()
                            } else {
                                status = "Hint: Start with 'We practice...'"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accent.primary)
                    ) { Text("Check Order") }
                    OutlinedButton(onClick = { built.clear(); status = "" }) { Text("Clear") }
                }
                if (status.isNotBlank()) Text(status, color = accent.primary, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun WordGuessingGame(onWin: () -> Unit) {
    val accent = GameKind.WORD_GUESS.accent
    val secret = "FLUENT"
    var guessed by remember { mutableStateOf(setOf('F', 'T')) }
    var letterInput by remember { mutableStateOf("") }
    val masked = secret.map { if (it in guessed) it else '_' }.joinToString(" ")
    val won = secret.all { it in guessed }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Word Guessing Game", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                Text("Hindi Clue: धाराप्रवाह (बिना अटके बोलने वाला)", style = MaterialTheme.typography.bodyMedium, color = accent.primary)
                Surface(color = accent.softTint, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        masked,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = accent.primary,
                        modifier = Modifier.padding(14.dp)
                    )
                }
                if (!won) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = letterInput,
                            onValueChange = { letterInput = it.take(1).uppercase() },
                            label = { Text("1 Letter") },
                            singleLine = true,
                            modifier = Modifier.width(120.dp).testTag("word_guess_letter")
                        )
                        Button(
                            onClick = {
                                letterInput.firstOrNull()?.let { ch ->
                                    guessed = guessed + ch
                                    if (secret.all { it in guessed }) onWin()
                                }
                                letterInput = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = accent.primary)
                        ) { Text("Guess") }
                    }
                } else {
                    Text("Awesome! The word is FLUENT!", color = accent.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    OutlinedButton(onClick = { guessed = setOf('F', 'T') }) { Text("Play Again") }
                }
            }
        }
    }
}
