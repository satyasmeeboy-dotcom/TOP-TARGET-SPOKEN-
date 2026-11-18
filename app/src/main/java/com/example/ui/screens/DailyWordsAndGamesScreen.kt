package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.curriculum.DailyPracticeCatalog
import com.example.data.curriculum.DailyRoutineCatalog
import com.example.data.curriculum.PracticeAndNewsCatalog
import com.example.data.curriculum.WordsCatalog
import com.example.data.local.DailyStudyTaskEntity
import com.example.data.local.LearnedWordEntity
import com.example.data.local.SavedNewsEntity
import com.example.model.BilingualMode
import com.example.model.DailyPhraseOrIdiom
import com.example.model.DailyWord
import com.example.model.GrammarMistakeFeedback
import com.example.model.PracticeQuestionKind
import com.example.model.SkillType
import com.example.speech.SpeechHelper
import com.example.ui.components.BilingualText
import com.example.ui.components.FolderAccent
import com.example.ui.components.FolderPresets
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.MicrophonePrivacyDialog
import com.example.ui.components.SampleAiNoticeBanner
import com.example.ui.components.StudyFolderCard
import com.example.ui.theme.PureWhite

private data class WordsFolderMeta(
    val index: Int,
    val tabLabel: String,
    val shortTab: String,
    val title: String,
    val subtitle: String,
    val badge: String,
    val icon: ImageVector,
    val accent: FolderAccent
)

@Composable
fun DailyWordsAndGamesScreen(
    epochDay: Long,
    learnedWords: List<LearnedWordEntity>,
    savedNews: List<SavedNewsEntity>,
    todayTasks: List<DailyStudyTaskEntity>,
    weakTags: List<String>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    routineFeedback: GrammarMistakeFeedback?,
    dailyPracticeFeedback: GrammarMistakeFeedback?,
    requestedTab: Int? = null,
    tabRequestNonce: Int = 0,
    onMarkWordLearned: (String, String, Boolean) -> Unit,
    onMarkPhraseLearned: (String, String) -> Unit,
    onMarkRoutinePracticed: (String) -> Unit,
    onCheckRoutineSentence: (String) -> Unit,
    onClearRoutineFeedback: () -> Unit,
    onCheckDailyPracticeAnswer: (String, SkillType) -> Unit,
    onClearDailyPracticeFeedback: () -> Unit,
    onToggleSaveNews: (String, String, Boolean) -> Unit,
    onRecordQuizScore: (SkillType, Int, Int, String) -> Unit
) {
    var activeFolderIndex by remember { mutableStateOf<Int?>(requestedTab) }
    LaunchedEffect(requestedTab, tabRequestNonce) {
        if (tabRequestNonce > 0) {
            activeFolderIndex = requestedTab
        }
    }

    val learnedIds = remember(learnedWords) { learnedWords.map { it.wordId }.toSet() }
    val genZWords = remember(epochDay) { WordsCatalog.todaysTenGenZWords(epochDay) }
    val vocabWords = remember(epochDay) { WordsCatalog.todaysTenVocabWords(epochDay) }
    val phrasesIdioms = remember(epochDay) { WordsCatalog.todaysPhrasesAndIdioms(epochDay) }

    val genZCount = genZWords.count { it.id in learnedIds }
    val vocabCount = vocabWords.count { it.id in learnedIds }
    val phraseCount = phrasesIdioms.count { it.id in learnedIds }
    val routineDoneCount = todayTasks.count { it.isCompleted && it.taskKey.startsWith("routine_") }

    val folders = remember(genZCount, vocabCount, phraseCount, routineDoneCount, savedNews.size) {
        listOf(
            WordsFolderMeta(
                index = 0,
                tabLabel = "DAILY 10",
                shortTab = "10 Daily Quiz",
                title = "10 Daily Practice",
                subtitle = "8 skills • Non-repeating",
                badge = "10 Qs",
                icon = Icons.Default.Quiz,
                accent = FolderPresets.Sky
            ),
            WordsFolderMeta(
                index = 1,
                tabLabel = "GEN Z",
                shortTab = "10 Gen Z",
                title = "10 Gen Z Words",
                subtitle = "Modern spoken slang",
                badge = "$genZCount/10 Done",
                icon = Icons.Default.AutoAwesome,
                accent = FolderPresets.Violet
            ),
            WordsFolderMeta(
                index = 2,
                tabLabel = "VOCAB",
                shortTab = "10 Vocab",
                title = "10 Vocabulary",
                subtitle = "Synonyms & Hindi usage",
                badge = "$vocabCount/10 Done",
                icon = Icons.Default.Translate,
                accent = FolderPresets.Indigo
            ),
            WordsFolderMeta(
                index = 3,
                tabLabel = "IDIOMS",
                shortTab = "Phrases & Idioms",
                title = "Phrases & Idioms",
                subtitle = "Natural spoken idioms",
                badge = "$phraseCount/10 Done",
                icon = Icons.Default.Bookmark,
                accent = FolderPresets.Teal
            ),
            WordsFolderMeta(
                index = 4,
                tabLabel = "ROUTINE",
                shortTab = "Daily Routine",
                title = "Daily Routine",
                subtitle = "Morning to Night",
                badge = "$routineDoneCount/${DailyRoutineCatalog.items.size}",
                icon = Icons.Default.Schedule,
                accent = FolderPresets.Amber
            ),
            WordsFolderMeta(
                index = 5,
                tabLabel = "20 CHALLENGE",
                shortTab = "20 Challenge",
                title = "20-Word Challenge",
                subtitle = "Lock your daily streak",
                badge = "Quiz",
                icon = Icons.Default.EmojiEvents,
                accent = FolderPresets.Emerald
            ),
            WordsFolderMeta(
                index = 6,
                tabLabel = "9 GAMES",
                shortTab = "9 Games",
                title = "9 Learning Games",
                subtitle = "Battle, Puzzle & Match",
                badge = "9 Games",
                icon = Icons.Default.Extension,
                accent = FolderPresets.Coral
            ),
            WordsFolderMeta(
                index = 7,
                tabLabel = "NEWS",
                shortTab = "Daily News",
                title = "Daily English News",
                subtitle = "Bilingual + 5 Qs each",
                badge = "${savedNews.size} Saved",
                icon = Icons.Default.Newspaper,
                accent = FolderPresets.Rose
            )
        )
    }

    if (activeFolderIndex == null) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            val isWide = maxWidth >= 600.dp
            val folderRows = if (isWide) folders.chunked(2) else folders.map { listOf(it) }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 720.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(
                                            FolderPresets.Emerald.softTint,
                                            FolderPresets.Sky.softTint
                                        )
                                    )
                                )
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.FolderOpen, contentDescription = null, tint = PureWhite, modifier = Modifier.size(26.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Study Folder Cabinet",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                        "अपनी पसंद का बड़ा रंगीन स्टडी फ़ोल्डर खोलें"
                                    else "Choose any colourful folder to practice offline & interactively",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                items(folderRows) { rowFolders ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowFolders.forEach { folder ->
                            val progress = when (folder.index) {
                                1 -> genZCount / 10f
                                2 -> vocabCount / 10f
                                3 -> phraseCount / 10f
                                4 -> routineDoneCount / DailyRoutineCatalog.items.size.toFloat().coerceAtLeast(1f)
                                else -> null
                            }
                            StudyFolderCard(
                                tabLabel = folder.tabLabel,
                                title = folder.title,
                                subtitle = folder.subtitle,
                                badgeText = folder.badge,
                                icon = folder.icon,
                                accent = folder.accent,
                                progress = progress,
                                onClick = { activeFolderIndex = folder.index },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("words_folder_${folder.index}")
                            )
                        }
                        if (isWide && rowFolders.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
        return
    }

    val selectedTab = activeFolderIndex ?: 0
    val activeMeta = folders.getOrElse(selectedTab) { folders.first() }
    BackHandler { activeFolderIndex = null }

    Column(modifier = Modifier.fillMaxSize()) {
        // Active folder header bar with quick return to Folder Cabinet
        Surface(
            color = activeMeta.accent.softTint,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { activeFolderIndex = null },
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "All Folders",
                        tint = activeMeta.accent.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Folders", color = activeMeta.accent.primary, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(6.dp))
                Text(
                    text = activeMeta.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = activeMeta.accent.primary,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 12.dp
        ) {
            folders.forEach { folder ->
                val isSelected = selectedTab == folder.index
                Tab(
                    selected = isSelected,
                    onClick = { activeFolderIndex = folder.index },
                    text = {
                        Text(
                            text = folder.shortTab,
                            color = if (isSelected) folder.accent.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            folder.icon,
                            contentDescription = null,
                            tint = if (isSelected) folder.accent.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            when (selectedTab) {
                0 -> DailyTenPracticeTab(
                    epochDay = epochDay,
                    weakTags = weakTags,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    isListening = isListening,
                    speechHelper = speechHelper,
                    dailyPracticeFeedback = dailyPracticeFeedback,
                    onCheckSentenceAnswer = onCheckDailyPracticeAnswer,
                    onClearSentenceFeedback = onClearDailyPracticeFeedback,
                    onFinishDailyTen = { correct, total ->
                        onRecordQuizScore(SkillType.GRAMMAR, correct, total, "daily_quiz")
                    }
                )
                1 -> WordListTab(
                    title = "Daily 10 New Gen Z Words",
                    subtitle = "Modern slang + Hindi meaning + pronunciation + real-life usage",
                    words = genZWords,
                    learnedIds = learnedIds,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    speechHelper = speechHelper,
                    accent = FolderPresets.Violet,
                    onMarkLearned = { onMarkWordLearned(it.id, it.word, true) }
                )
                2 -> WordListTab(
                    title = "Daily 10 New Vocabulary Words",
                    subtitle = "Beginner to Advanced + synonyms + antonyms + Hindi examples",
                    words = vocabWords,
                    learnedIds = learnedIds,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    speechHelper = speechHelper,
                    accent = FolderPresets.Indigo,
                    onMarkLearned = { onMarkWordLearned(it.id, it.word, false) }
                )
                3 -> PhrasesAndIdiomsTab(
                    items = phrasesIdioms,
                    learnedIds = learnedIds,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    speechHelper = speechHelper,
                    onMarkLearned = { onMarkPhraseLearned(it.id, it.phrase) }
                )
                4 -> DailyRoutineContent(
                    todayTasks = todayTasks,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    isListening = isListening,
                    speechHelper = speechHelper,
                    routineFeedback = routineFeedback,
                    onMarkRoutinePracticed = onMarkRoutinePracticed,
                    onCheckRoutineSentence = onCheckRoutineSentence,
                    onClearRoutineFeedback = onClearRoutineFeedback
                )
                5 -> DailyTwentyWordChallengeTab(
                    words = genZWords + vocabWords,
                    onFinishChallenge = { correct, total ->
                        onRecordQuizScore(SkillType.VOCABULARY, correct, total, "daily_quiz")
                    }
                )
                6 -> LearningGamesView(onGameScore = onRecordQuizScore)
                7 -> DailyNewsTab(
                    savedNews = savedNews,
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    speechHelper = speechHelper,
                    onToggleSaveNews = onToggleSaveNews,
                    onRecordQuizScore = { c, t -> onRecordQuizScore(SkillType.READING, c, t, "reading") }
                )
            }
        }
    }
}

@Composable
private fun DailyTenPracticeTab(
    epochDay: Long,
    weakTags: List<String>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    dailyPracticeFeedback: GrammarMistakeFeedback?,
    onCheckSentenceAnswer: (String, SkillType) -> Unit,
    onClearSentenceFeedback: () -> Unit,
    onFinishDailyTen: (Int, Int) -> Unit
) {
    val context = LocalContext.current
    val questions = remember(epochDay, weakTags) {
        DailyPracticeCatalog.generateTodaysTenQuestions(epochDay, weakTags)
    }
    var currentIdx by remember(epochDay) { mutableIntStateOf(0) }
    val mcqAnswers = remember(epochDay) { mutableStateMapOf<String, Int>() }
    val checkedMcqIds = remember(epochDay) { mutableStateMapOf<String, Boolean>() }
    val textResponses = remember(epochDay) { mutableStateMapOf<String, String>() }
    val evaluatedTextCorrect = remember(epochDay) { mutableStateMapOf<String, Boolean>() }
    var finished by remember(epochDay) { mutableStateOf(false) }
    var showMicDialog by remember { mutableStateOf(false) }
    var activeMicQuestionId by remember { mutableStateOf<String?>(null) }

    val micLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val qId = activeMicQuestionId
            if (qId != null) {
                speechHelper.startListening { spoken -> textResponses[qId] = spoken }
            }
        }
    }

    val currentQ = questions.getOrNull(currentIdx)
    val answeredCorrectCount = questions.count { q ->
        if (q.options.isNotEmpty()) mcqAnswers[q.id] == q.correctIndex
        else evaluatedTextCorrect[q.id] == true
    }

    val accent = FolderPresets.Sky
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = accent.softTint),
                border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Daily 10 Fresh Practice Questions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                    Text(
                        "Covers Grammar, Vocab, Sentence Formation, Translation, Conversation, Reading, Writing & Speaking",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            SampleAiNoticeBanner()
        }

        if (currentQ != null && !finished) {
            item {
                LinearProgressIndicator(
                    progress = { (currentIdx + 1).toFloat() / questions.size.coerceAtLeast(1) },
                    color = accent.primary,
                    trackColor = accent.softTint,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(50))
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${currentIdx + 1} of ${questions.size} • ${currentQ.kind.labelEn}",
                        style = MaterialTheme.typography.labelLarge,
                        color = accent.primary,
                        fontWeight = FontWeight.Bold
                    )
                    if (currentQ.weakTopicTag in weakTags) {
                        Surface(
                            color = FolderPresets.Amber.softTint,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Weak Area Focus",
                                style = MaterialTheme.typography.labelSmall,
                                color = FolderPresets.Amber.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(modifier = Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BilingualText(
                                english = currentQ.promptEn,
                                hindi = currentQ.promptHi,
                                mode = bilingualMode,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { speechHelper.speakEnglish(currentQ.promptEn, speechRate) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen to question", tint = MaterialTheme.colorScheme.primary)
                            }
                        }

                        if (currentQ.readingSnippet.isNotBlank()) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text("Reading Passage:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.height(4.dp))
                                    Text(currentQ.readingSnippet, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }

                        if (currentQ.options.isNotEmpty()) {
                            val selectedOpt = mcqAnswers[currentQ.id]
                            val isChecked = checkedMcqIds[currentQ.id] == true
                            currentQ.options.forEachIndexed { oIdx, opt ->
                                val picked = selectedOpt == oIdx
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (picked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                    border = BorderStroke(
                                        1.dp,
                                        if (picked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .minimumInteractiveComponentSize()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable(enabled = !isChecked) { mcqAnswers[currentQ.id] = oIdx }
                                ) {
                                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (picked) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(opt, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }

                            if (!isChecked) {
                                Button(
                                    onClick = { checkedMcqIds[currentQ.id] = true },
                                    enabled = selectedOpt != null,
                                    modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                                ) {
                                    Text("Check Answer")
                                }
                            } else {
                                val right = selectedOpt == currentQ.correctIndex
                                Surface(
                                    color = if (right) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = if (right) "Correct! Well done!" else "Not quite right — Correct option: '${currentQ.options.getOrNull(currentQ.correctIndex).orEmpty()}'",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = if (right) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                        )
                                        BilingualText(
                                            english = currentQ.explanationEn,
                                            hindi = currentQ.explanationHi,
                                            mode = bilingualMode
                                        )
                                    }
                                }
                            }
                        } else {
                            // Writing or Speaking live AI check
                            val currentText = textResponses[currentQ.id].orEmpty()
                            val targetSkill = if (currentQ.kind == PracticeQuestionKind.SPEAKING) SkillType.SPEAKING else SkillType.WRITING
                            OutlinedTextField(
                                value = currentText,
                                onValueChange = { textResponses[currentQ.id] = it },
                                label = { Text(if (targetSkill == SkillType.SPEAKING) "Speak or type your answer" else "Write your English sentence") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                if (targetSkill == SkillType.SPEAKING) {
                                    OutlinedButton(
                                        onClick = {
                                            activeMicQuestionId = currentQ.id
                                            val ok = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                                            if (ok) {
                                                speechHelper.startListening { spoken -> textResponses[currentQ.id] = spoken }
                                            } else {
                                                showMicDialog = true
                                            }
                                        },
                                        modifier = Modifier.minimumInteractiveComponentSize()
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = "Speak answer", modifier = Modifier.size(18.dp))
                                        Spacer(Modifier.width(4.dp))
                                        Text(if (isListening) "Listening..." else "Speak")
                                    }
                                }
                                Button(
                                    onClick = {
                                        evaluatedTextCorrect[currentQ.id] = currentText.trim().split(Regex("\\s+")).size >= 3
                                        onCheckSentenceAnswer(currentText, targetSkill)
                                    },
                                    enabled = currentText.isNotBlank(),
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Text("Check Answer")
                                }
                            }
                            if (currentQ.sampleResponse.isNotBlank()) {
                                TextButton(onClick = { speechHelper.speakEnglish(currentQ.sampleResponse, speechRate) }) {
                                    Text("Hear sample: ${currentQ.sampleResponse}")
                                }
                            }
                            if (dailyPracticeFeedback != null) {
                                evaluatedTextCorrect[currentQ.id] = dailyPracticeFeedback.isCorrect
                                GrammarFeedbackCard(
                                    feedback = dailyPracticeFeedback,
                                    bilingualMode = bilingualMode
                                )
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (currentIdx > 0) {
                                OutlinedButton(
                                    onClick = {
                                        onClearSentenceFeedback()
                                        currentIdx--
                                    },
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Text("Previous")
                                }
                            }
                            if (currentIdx < questions.lastIndex) {
                                Button(
                                    onClick = {
                                        onClearSentenceFeedback()
                                        currentIdx++
                                    },
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Text("Next Question")
                                }
                            } else {
                                Button(
                                    onClick = {
                                        onClearSentenceFeedback()
                                        finished = true
                                        onFinishDailyTen(answeredCorrectCount, questions.size)
                                    },
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Text("Finish 10 Questions")
                                }
                            }
                        }
                    }
                    }
                }
            }
        } else {
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Daily 10 Practice Complete!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Score: $answeredCorrectCount / ${questions.size}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = if (answeredCorrectCount >= 7) {
                                "Excellent accuracy! Your grammar and vocabulary are becoming stronger."
                            } else {
                                "Good honest effort! Review the rule explanations above and try again to sharpen weak areas."
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(
                            onClick = {
                                mcqAnswers.clear()
                                checkedMcqIds.clear()
                                textResponses.clear()
                                evaluatedTextCorrect.clear()
                                onClearSentenceFeedback()
                                currentIdx = 0
                                finished = false
                            },
                            modifier = Modifier.minimumInteractiveComponentSize()
                        ) {
                            Text("Practice Again")
                        }
                    }
                }
            }
        }
    }

    if (showMicDialog) {
        MicrophonePrivacyDialog(
            onConfirmAllow = {
                showMicDialog = false
                micLauncher.launch(Manifest.permission.RECORD_AUDIO)
            },
            onCancel = { showMicDialog = false }
        )
    }
}

@Composable
private fun PhrasesAndIdiomsTab(
    items: List<DailyPhraseOrIdiom>,
    learnedIds: Set<String>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    onMarkLearned: (DailyPhraseOrIdiom) -> Unit
) {
    val accent = FolderPresets.Teal
    val learnedCount = items.count { it.id in learnedIds }
    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Daily New Phrases & Idioms", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
            Text(
                "Learn natural daily idioms & spoken phrases • Today's Progress: $learnedCount / ${items.size}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        items(items, key = { it.id }) { item ->
            val isLearned = item.id in learnedIds
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.5.dp, if (isLearned) accent.primary else accent.primary.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(accent.primary))
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Surface(
                                    color = accent.softTint,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = if (item.isIdiom) "IDIOM • ${item.level.labelEn}" else "PHRASE • ${item.level.labelEn}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = accent.primary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(item.phrase, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = accent.primary)
                            }
                            IconButton(
                                onClick = { speechHelper.speakEnglish("${item.phrase}. ${item.exampleSentence}", speechRate) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen to phrase or idiom", tint = accent.primary)
                            }
                            if (isLearned) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Learned", tint = accent.primary)
                            }
                        }
                        Surface(
                            color = accent.softTint.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Hindi Meaning: ${item.hindiMeaning}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                        Text("When to use: ${item.usageTipEn}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        BilingualText(english = "Example: ${item.exampleSentence}", hindi = item.hindiTranslation, mode = bilingualMode)
                        if (!isLearned) {
                            OutlinedButton(
                                onClick = { onMarkLearned(item) },
                                border = BorderStroke(1.dp, accent.primary),
                                modifier = Modifier.testTag("mark_phrase_${item.id}")
                            ) {
                                Text("Mark Learned (+1 Star)", color = accent.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WordListTab(
    title: String,
    subtitle: String,
    words: List<DailyWord>,
    learnedIds: Set<String>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    accent: FolderAccent = FolderPresets.Emerald,
    onMarkLearned: (DailyWord) -> Unit
) {
    val learnedTodayCount = words.count { it.id in learnedIds }
    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
            Text("$subtitle • Progress: $learnedTodayCount / ${words.size}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(words, key = { it.id }) { word ->
            val isLearned = word.id in learnedIds
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.5.dp, if (isLearned) accent.primary else accent.primary.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(accent.primary))
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(word.word, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = accent.primary)
                                Text("Pronunciation: ${word.pronunciation}", style = MaterialTheme.typography.labelSmall)
                            }
                            IconButton(
                                onClick = { speechHelper.speakEnglish("${word.word}. ${word.exampleSentence}", speechRate) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Pronounce word", tint = accent.primary)
                            }
                            if (isLearned) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Learned", tint = accent.primary)
                            }
                        }
                        Surface(
                            color = accent.softTint.copy(alpha = 0.75f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Hindi Meaning: ${word.hindiMeaning}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                        BilingualText(english = "Example: ${word.exampleSentence}", hindi = word.hindiTranslation, mode = bilingualMode)
                        Text("Real-Life Usage: ${word.realLifeUsage}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (word.synonyms.isNotEmpty() || word.antonyms.isNotEmpty()) {
                            Text(
                                text = "Synonyms: ${word.synonyms.joinToString()} | Antonyms: ${word.antonyms.joinToString()}",
                                style = MaterialTheme.typography.labelSmall,
                                color = accent.primary
                            )
                        }
                        if (!isLearned) {
                            OutlinedButton(
                                onClick = { onMarkLearned(word) },
                                border = BorderStroke(1.dp, accent.primary),
                                modifier = Modifier.testTag("mark_learned_${word.id}")
                            ) {
                                Text("Mark Learned (+1 Star)", color = accent.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyTwentyWordChallengeTab(
    words: List<DailyWord>,
    onFinishChallenge: (Int, Int) -> Unit
) {
    val quizWords = remember(words) { words.take(6) }
    var currentIdx by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var explanation by remember { mutableStateOf("") }
    var finished by remember { mutableStateOf(false) }

    val accent = FolderPresets.Amber
    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = accent.softTint),
                border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Daily 20-Word Challenge Quiz", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                    Text("Combines today's 10 Gen Z Words + 10 Vocabulary Words to lock in your daily streak!", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (!finished) {
                            val current = quizWords[currentIdx]
                            val distractors = words.filter { it.id != current.id }.take(3).map { it.hindiMeaning }
                            val options = remember(current.id) { (distractors + current.hindiMeaning).shuffled() }
                            Text("Question ${currentIdx + 1} of ${quizWords.size}", style = MaterialTheme.typography.labelLarge, color = accent.primary, fontWeight = FontWeight.Bold)
                            Text("What is the Hindi meaning of '${current.word}'?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            options.forEach { opt ->
                                OutlinedButton(
                                    onClick = {
                                        if (opt == current.hindiMeaning) {
                                            score++
                                            explanation = "Correct! '${current.word}' means '${current.hindiMeaning}'. Example: ${current.exampleSentence}"
                                        } else {
                                            explanation = "Answer: '${current.hindiMeaning}'. Remember: ${current.exampleSentence}"
                                        }
                                        if (currentIdx < quizWords.lastIndex) currentIdx++
                                        else {
                                            finished = true
                                            onFinishChallenge(score, quizWords.size)
                                        }
                                    },
                                    border = BorderStroke(1.dp, accent.primary.copy(alpha = 0.45f)),
                                    modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                                ) {
                                    Text(opt, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            if (explanation.isNotBlank()) {
                                Surface(
                                    color = accent.softTint,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(explanation, style = MaterialTheme.typography.bodySmall, color = accent.primary, modifier = Modifier.padding(10.dp))
                                }
                            }
                        } else {
                            Text("Challenge Complete! Score: $score / ${quizWords.size}", style = MaterialTheme.typography.titleLarge, color = accent.primary, fontWeight = FontWeight.Bold)
                            Text(explanation, style = MaterialTheme.typography.bodyMedium)
                            Button(
                                onClick = { currentIdx = 0; score = 0; explanation = ""; finished = false },
                                colors = ButtonDefaults.buttonColors(containerColor = accent.primary)
                            ) { Text("Retake Challenge") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyNewsTab(
    savedNews: List<SavedNewsEntity>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    onToggleSaveNews: (String, String, Boolean) -> Unit,
    onRecordQuizScore: (Int, Int) -> Unit
) {
    val accent = FolderPresets.Rose
    val savedIds = remember(savedNews) { savedNews.map { it.articleId }.toSet() }
    val answers = remember { mutableStateMapOf<String, Int>() }
    val submittedIds = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 720.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(accent.softTint, PureWhite)))
                        .padding(16.dp)
                ) {
                    Text("Daily English News (Simple & Bilingual)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
                    Text("Simple paragraph + Hindi meaning + difficult words + 5 practice questions!", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        items(PracticeAndNewsCatalog.dailyNewsArticles, key = { it.id }) { article ->
            val isSaved = article.id in savedIds
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.42f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(Modifier.fillMaxWidth().height(5.dp).background(accent.primary))
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = accent.softTint, shape = RoundedCornerShape(50)) {
                                Text(
                                    article.dateLabel,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = accent.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = { speechHelper.speakEnglish("${article.headlineEn}. ${article.bodyEn}", speechRate) }) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen to news", tint = accent.primary)
                            }
                            IconButton(onClick = { onToggleSaveNews(article.id, article.headlineEn, isSaved) }) {
                                Icon(
                                    imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = if (isSaved) "Saved for offline revision" else "Save news",
                                    tint = accent.primary
                                )
                            }
                        }
                        Text(article.headlineEn, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        BilingualText(english = article.bodyEn, hindi = article.bodyHi, mode = bilingualMode)
                        Surface(
                            color = FolderPresets.Amber.softTint.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, FolderPresets.Amber.primary.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Difficult Words & Grammar Note:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = FolderPresets.Amber.primary)
                                article.difficultWords.forEach { dw ->
                                    Text("• ${dw.word}: ${dw.meaningEn} (${dw.meaningHi})", style = MaterialTheme.typography.bodyMedium)
                                }
                                Spacer(Modifier.height(4.dp))
                                BilingualText(article.grammarNoteEn, article.grammarNoteHi, bilingualMode)
                            }
                        }
                        article.quiz.forEach { q ->
                            Text(q.question, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            q.options.forEachIndexed { oIdx, opt ->
                                FilterChip(
                                    selected = answers[q.id] == oIdx,
                                    onClick = { answers[q.id] = oIdx },
                                    label = { Text(opt, style = MaterialTheme.typography.bodyMedium) }
                                )
                            }
                        }
                        val submitted = submittedIds[article.id] == true
                        if (!submitted) {
                            Button(
                                onClick = {
                                    submittedIds[article.id] = true
                                    val c = article.quiz.count { answers[it.id] == it.correctIndex }
                                    onRecordQuizScore(c, article.quiz.size)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = accent.primary),
                                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                            ) { Text("Check ${article.quiz.size} News Questions") }
                        } else {
                            val c = article.quiz.count { answers[it.id] == it.correctIndex }
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Quiz Score: $c / ${article.quiz.size} • Saved to reading mastery.",
                                    color = FolderPresets.Emerald.primary,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                article.quiz.forEach { q ->
                                    val isRight = answers[q.id] == q.correctIndex
                                    if (!isRight) {
                                        Text(
                                            text = "• ${q.question} -> Answer: ${q.options.getOrNull(q.correctIndex).orEmpty()} (${q.explanationHi})",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
