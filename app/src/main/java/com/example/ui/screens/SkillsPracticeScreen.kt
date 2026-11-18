package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import com.example.data.ai.SpeakingEvaluation
import com.example.data.curriculum.PracticeAndNewsCatalog
import com.example.data.local.MistakeLogEntity
import com.example.model.BilingualMode
import com.example.model.GrammarMistakeFeedback
import com.example.model.SkillType
import com.example.speech.SpeechHelper
import com.example.ui.components.BilingualText
import com.example.ui.components.FolderPresets
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.MicrophonePrivacyDialog
import com.example.ui.components.SampleAiNoticeBanner
import com.example.ui.theme.PureWhite

@Composable
fun SkillsPracticeScreen(
    bilingualMode: BilingualMode,
    speechRate: Float,
    isSpeaking: Boolean,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    grammarResult: GrammarMistakeFeedback?,
    speakingEvaluation: SpeakingEvaluation?,
    recentMistakes: List<MistakeLogEntity>,
    onCheckGrammar: (String, SkillType) -> Unit,
    onEvaluateSpeaking: (String, String) -> Unit,
    onRecordSkillQuiz: (SkillType, Int, Int, String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        Triple("Grammar Fix", Icons.Default.Spellcheck, FolderPresets.Coral),
        Triple("Speaking", Icons.Default.Mic, FolderPresets.Emerald),
        Triple("Listening", Icons.Default.Hearing, FolderPresets.Sky),
        Triple("Reading", Icons.AutoMirrored.Filled.MenuBook, FolderPresets.Amber),
        Triple("Writing", Icons.Default.EditNote, FolderPresets.Violet)
    )
    val activeTriple = tabs[selectedTab]
    val activeAccent = activeTriple.third

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = activeAccent.softTint,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(listOf(activeAccent.softTint, PureWhite))
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(activeAccent.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(activeTriple.second, contentDescription = null, tint = PureWhite, modifier = Modifier.size(19.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "${activeTriple.first} Folder",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = activeAccent.primary
                    )
                    Text(
                        text = "Interactive Practice • Hindi + English Help",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 12.dp
        ) {
            tabs.forEachIndexed { idx, triple ->
                val isSelected = selectedTab == idx
                val tabAccent = triple.third
                Tab(
                    selected = isSelected,
                    onClick = { selectedTab = idx },
                    text = {
                        Text(
                            triple.first,
                            color = if (isSelected) tabAccent.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            triple.second,
                            contentDescription = null,
                            tint = if (isSelected) tabAccent.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            when (selectedTab) {
                0 -> GrammarCheckerTab(
                    bilingualMode = bilingualMode,
                    grammarResult = grammarResult,
                    recentMistakes = recentMistakes,
                    onCheckGrammar = { onCheckGrammar(it, SkillType.GRAMMAR) }
                )
                1 -> SpeakingPracticeTab(
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    isListening = isListening,
                    speechHelper = speechHelper,
                    evaluation = speakingEvaluation,
                    onEvaluateSpeaking = onEvaluateSpeaking
                )
                2 -> ListeningPracticeTab(
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    isSpeaking = isSpeaking,
                    speechHelper = speechHelper,
                    onRecordQuiz = { c, t -> onRecordSkillQuiz(SkillType.LISTENING, c, t, "listening") }
                )
                3 -> ReadingPracticeTab(
                    bilingualMode = bilingualMode,
                    speechRate = speechRate,
                    speechHelper = speechHelper,
                    onRecordQuiz = { c, t -> onRecordSkillQuiz(SkillType.READING, c, t, "reading") }
                )
                4 -> WritingPracticeTab(
                    bilingualMode = bilingualMode,
                    grammarResult = grammarResult,
                    onCheckWriting = { onCheckGrammar(it, SkillType.WRITING) }
                )
            }
        }
    }
}

@Composable
private fun GrammarCheckerTab(
    bilingualMode: BilingualMode,
    grammarResult: GrammarMistakeFeedback?,
    recentMistakes: List<MistakeLogEntity>,
    onCheckGrammar: (String) -> Unit
) {
    var sentence by remember { mutableStateOf("Yesterday he go to market and buy a apple.") }
    val samples = listOf(
        "Yesterday he go to market and buy a apple.",
        "Everyone are happy on Monday.",
        "I didn't went there because I was busy.",
        "Let's discuss about grammer rules."
    )

    val accent = FolderPresets.Coral
    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 720.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Automatic AI Grammar Correction", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = accent.primary)
            Text("Checks Tense, Spelling, Articles, Prepositions, Verb Forms, SVA, Structure & Vocabulary.", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            SampleAiNoticeBanner()
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(samples) { s ->
                    FilterChip(selected = sentence == s, onClick = { sentence = s }, label = { Text(s.take(26) + "...") })
                }
            }
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = sentence,
                onValueChange = { sentence = it },
                label = { Text("Enter any English sentence to check") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth().testTag("grammar_checker_input")
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { onCheckGrammar(sentence) },
                colors = ButtonDefaults.buttonColors(containerColor = accent.primary),
                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize().testTag("run_grammar_check_button")
            ) {
                Text("Analyze & Teach Why")
            }
        }
        grammarResult?.let { fb ->
            item {
                GrammarFeedbackCard(
                    feedback = fb,
                    bilingualMode = bilingualMode,
                    onTryAgainClick = { sentence = fb.correctedSentence }
                )
            }
        }
        if (recentMistakes.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text("Your Tracked Mistake Patterns (Local)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        recentMistakes.take(5).forEach { m ->
                            Text("• ${m.category} (${m.occurrenceCount}x): '${m.correctedSentence}'", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpeakingPracticeTab(
    bilingualMode: BilingualMode,
    speechRate: Float,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    evaluation: SpeakingEvaluation?,
    onEvaluateSpeaking: (String, String) -> Unit
) {
    val context = LocalContext.current
    var selectedScenario by remember { mutableStateOf(PracticeAndNewsCatalog.speakingScenarios.first()) }
    var spokenText by remember { mutableStateOf("") }
    var showMicDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            speechHelper.startListening { spoken ->
                spokenText = spoken
                onEvaluateSpeaking(selectedScenario.titleEn, spoken)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Daily Speaking Classes (9 Real-Life Topics)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            SampleAiNoticeBanner()
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PracticeAndNewsCatalog.speakingScenarios) { sc ->
                    FilterChip(
                        selected = selectedScenario.id == sc.id,
                        onClick = { selectedScenario = sc },
                        label = { Text(sc.titleEn) }
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(selectedScenario.titleEn, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    BilingualText(selectedScenario.teacherPromptEn, selectedScenario.teacherPromptHi, bilingualMode)
                    Spacer(Modifier.height(8.dp))
                    Text("Useful Phrases:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    selectedScenario.keyPhrases.forEach { p -> Text("• $p", style = MaterialTheme.typography.bodySmall) }
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(onClick = { speechHelper.speakEnglish(selectedScenario.sampleLines.joinToString(" "), speechRate) }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Hear Sample Answer")
                    }
                }
            }
        }
        item {
            OutlinedTextField(
                value = spokenText,
                onValueChange = { spokenText = it },
                label = { Text("Speak with Mic or type your response") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth().testTag("speaking_input")
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        val ok = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        if (ok) {
                            if (isListening) speechHelper.stopListening()
                            else speechHelper.startListening { res ->
                                spokenText = res
                                onEvaluateSpeaking(selectedScenario.titleEn, res)
                            }
                        } else {
                            showMicDialog = true
                        }
                    },
                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize().testTag("speaking_mic_button")
                ) {
                    Icon(Icons.Default.Mic, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text(if (isListening) "Listening..." else "Speak Now")
                }
                OutlinedButton(
                    onClick = { if (spokenText.isNotBlank()) onEvaluateSpeaking(selectedScenario.titleEn, spokenText) },
                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize().testTag("speaking_evaluate_button")
                ) {
                    Text("Evaluate Speech")
                }
            }
        }
        evaluation?.let { eval ->
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Speaking Feedback • Fluency Score: ${eval.fluencyScore}%", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Grammar: ${eval.grammarNote}", style = MaterialTheme.typography.bodySmall)
                        Text("Pronunciation: ${eval.pronunciationTip}", style = MaterialTheme.typography.bodySmall)
                        Text(eval.encouragement, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }

    if (showMicDialog) {
        MicrophonePrivacyDialog(
            onConfirmAllow = {
                showMicDialog = false
                launcher.launch(Manifest.permission.RECORD_AUDIO)
            },
            onCancel = { showMicDialog = false }
        )
    }
}

@Composable
private fun ListeningPracticeTab(
    bilingualMode: BilingualMode,
    speechRate: Float,
    isSpeaking: Boolean,
    speechHelper: SpeechHelper,
    onRecordQuiz: (Int, Int) -> Unit
) {
    var selectedLesson by remember { mutableStateOf(PracticeAndNewsCatalog.listeningLessons.first()) }
    var showTranscript by remember { mutableStateOf(false) }
    val answers = remember { mutableStateMapOf<String, Int>() }
    var submitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Listening Practice & Comprehension", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PracticeAndNewsCatalog.listeningLessons) { lesson ->
                    FilterChip(
                        selected = selectedLesson.id == lesson.id,
                        onClick = {
                            selectedLesson = lesson
                            submitted = false
                            answers.clear()
                        },
                        label = { Text("${lesson.category} (${lesson.level.labelEn})") }
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(selectedLesson.titleEn, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(selectedLesson.titleHi, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                if (isSpeaking) speechHelper.stopSpeaking()
                                else speechHelper.speakEnglish(selectedLesson.audioScriptEn, speechRate)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = FolderPresets.Sky.primary),
                            modifier = Modifier.testTag("listening_play_button")
                        ) {
                            Icon(if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text(if (isSpeaking) "Stop Audio" else "Play Audio")
                        }
                        OutlinedButton(onClick = { showTranscript = !showTranscript }) {
                            Text(if (showTranscript) "Hide Script" else "Show Script")
                        }
                    }
                    if (showTranscript) {
                        Spacer(Modifier.height(8.dp))
                        BilingualText(selectedLesson.audioScriptEn, selectedLesson.hindiSummary, bilingualMode)
                    }
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Comprehension Check", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    selectedLesson.comprehensionQuiz.forEach { q ->
                        Text(q.question, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        q.options.forEachIndexed { idx, opt ->
                            FilterChip(
                                selected = answers[q.id] == idx,
                                onClick = { if (!submitted) answers[q.id] = idx },
                                label = { Text(opt) }
                            )
                        }
                        if (submitted) {
                            val ok = answers[q.id] == q.correctIndex
                            Text(
                                text = if (ok) "Correct! ${q.explanationEn}" else "Correct: ${q.options[q.correctIndex]} — ${q.explanationHi}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (ok) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Button(
                        onClick = {
                            submitted = true
                            val score = selectedLesson.comprehensionQuiz.count { answers[it.id] == it.correctIndex }
                            onRecordQuiz(score, selectedLesson.comprehensionQuiz.size)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check Listening Answers")
                    }
                }
            }
        }
    }
}

@Composable
private fun ReadingPracticeTab(
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    onRecordQuiz: (Int, Int) -> Unit
) {
    var selected by remember { mutableStateOf(PracticeAndNewsCatalog.readingPassages.first()) }
    val answers = remember { mutableStateMapOf<String, Int>() }
    var submitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Reading Practice (Stories & Articles)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PracticeAndNewsCatalog.readingPassages) { p ->
                    FilterChip(
                        selected = selected.id == p.id,
                        onClick = { selected = p; submitted = false; answers.clear() },
                        label = { Text("${p.category}: ${p.titleEn}") }
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(selected.titleEn, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        OutlinedButton(onClick = { speechHelper.speakEnglish(selected.contentEn, speechRate) }) {
                            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = FolderPresets.Amber.primary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Read Aloud", color = FolderPresets.Amber.primary)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    BilingualText(selected.contentEn, selected.contentHi, bilingualMode)
                    Spacer(Modifier.height(10.dp))
                    Text("Difficult Word Meanings:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    selected.difficultWords.forEach { dw ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                        ) {
                            Text(
                                text = "${dw.word}: ${dw.meaningEn} (${dw.meaningHi})",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Reading Comprehension", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    selected.quiz.forEach { q ->
                        Text(q.question, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        q.options.forEachIndexed { idx, opt ->
                            FilterChip(
                                selected = answers[q.id] == idx,
                                onClick = { if (!submitted) answers[q.id] = idx },
                                label = { Text(opt) }
                            )
                        }
                        if (submitted) {
                            val ok = answers[q.id] == q.correctIndex
                            Text(
                                text = if (ok) "Correct! ${q.explanationEn}" else "Correct: ${q.options[q.correctIndex]}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (ok) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Button(
                        onClick = {
                            submitted = true
                            val score = selected.quiz.count { answers[it.id] == it.correctIndex }
                            onRecordQuiz(score, selected.quiz.size)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit Reading Quiz")
                    }
                }
            }
        }
    }
}

@Composable
private fun WritingPracticeTab(
    bilingualMode: BilingualMode,
    grammarResult: GrammarMistakeFeedback?,
    onCheckWriting: (String) -> Unit
) {
    var selectedPrompt by remember { mutableStateOf(PracticeAndNewsCatalog.writingPrompts.first()) }
    var draft by remember(selectedPrompt.id) { mutableStateOf(selectedPrompt.starterTemplate) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().widthIn(max = 680.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Writing Practice (7 Formats)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            SampleAiNoticeBanner()
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PracticeAndNewsCatalog.writingPrompts) { p ->
                    FilterChip(
                        selected = selectedPrompt.id == p.id,
                        onClick = { selectedPrompt = p },
                        label = { Text(p.category) }
                    )
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(selectedPrompt.titleEn, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    BilingualText(selectedPrompt.instructionEn, selectedPrompt.instructionHi, bilingualMode)
                }
            }
        }
        item {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                label = { Text("Write your ${selectedPrompt.category}") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth().testTag("writing_draft_input")
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { onCheckWriting(draft) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize().testTag("check_writing_button")
            ) {
                Text("Check Grammar, Spelling & Structure")
            }
        }
        grammarResult?.let { fb ->
            item {
                GrammarFeedbackCard(feedback = fb, bilingualMode = bilingualMode, onTryAgainClick = { draft = fb.correctedSentence })
            }
        }
    }
}
