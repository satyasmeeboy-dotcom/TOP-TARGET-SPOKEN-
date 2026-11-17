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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.curriculum.DailyRoutineCatalog
import com.example.data.local.DailyStudyTaskEntity
import com.example.model.BilingualMode
import com.example.model.DailyRoutineItem
import com.example.model.GrammarMistakeFeedback
import com.example.model.RoutinePeriod
import com.example.speech.SpeechHelper
import com.example.ui.components.BilingualText
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.MicrophonePrivacyDialog

@Composable
fun DailyRoutineScreen(
    todayTasks: List<DailyStudyTaskEntity>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    routineFeedback: GrammarMistakeFeedback?,
    onMarkRoutinePracticed: (String) -> Unit,
    onCheckRoutineSentence: (String) -> Unit,
    onClearRoutineFeedback: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("routine_back_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Home")
                }
                Spacer(Modifier.width(4.dp))
                Column {
                    Text(
                        text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "दैनिक दिनचर्या (Daily Routine)" else "Daily Routine",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "सुबह से रात तक अंग्रेज़ी बोलना सीखें" else "Speak your whole day in fluent English",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        DailyRoutineContent(
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
    }
}

@Composable
fun DailyRoutineContent(
    todayTasks: List<DailyStudyTaskEntity>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    isListening: Boolean,
    speechHelper: SpeechHelper,
    routineFeedback: GrammarMistakeFeedback?,
    onMarkRoutinePracticed: (String) -> Unit,
    onCheckRoutineSentence: (String) -> Unit,
    onClearRoutineFeedback: () -> Unit
) {
    val context = LocalContext.current
    var selectedPeriod by remember { mutableStateOf<RoutinePeriod?>(null) }
    var usePastTense by remember { mutableStateOf(false) }
    var customRoutineText by remember { mutableStateOf("") }
    var showMicPrivacyDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            speechHelper.startListening { spoken ->
                customRoutineText = spoken
                onCheckRoutineSentence(spoken)
            }
        }
    }

    val allItems = DailyRoutineCatalog.items
    val practicedIds = remember(todayTasks) {
        todayTasks
            .filter { it.isCompleted && it.taskKey.startsWith("routine_") }
            .map { it.taskKey.removePrefix("routine_") }
            .toSet()
    }
    val practicedCount = allItems.count { it.id in practicedIds }
    val filteredItems = remember(selectedPeriod) {
        if (selectedPeriod == null) allItems else allItems.filter { it.period == selectedPeriod }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Summary & Tense Switcher Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = when (bilingualMode) {
                                        BilingualMode.HINDI_ASSIST -> "दैनिक दिनचर्या अभ्यास (Morning to Night)"
                                        else -> "Morning-to-Night Daily Routine"
                                    },
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "$practicedCount / ${allItems.size} routine steps practiced today",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    val script = filteredItems.joinToString(" ") {
                                        if (usePastTense) it.pastTenseVariantEn else it.sentenceEn
                                    }
                                    speechHelper.speakEnglish(script, speechRate)
                                },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("routine_listen_all_button")
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Listen All")
                            }
                        }

                        LinearProgressIndicator(
                            progress = { (practicedCount.toFloat() / allItems.size.coerceAtLeast(1)).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                        )

                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            SegmentedButton(
                                selected = !usePastTense,
                                onClick = { usePastTense = false },
                                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                                modifier = Modifier.testTag("routine_tense_present")
                            ) {
                                Text("Daily Habit (Present)")
                            }
                            SegmentedButton(
                                selected = usePastTense,
                                onClick = { usePastTense = true },
                                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                                modifier = Modifier.testTag("routine_tense_past")
                            ) {
                                Text("Yesterday (Past)")
                            }
                        }
                    }
                }
            }

            // 2. Period Filter Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedPeriod == null,
                            onClick = { selectedPeriod = null },
                            label = { Text("All (${allItems.size})") },
                            leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.testTag("routine_filter_all")
                        )
                    }
                    items(RoutinePeriod.entries) { period ->
                        val periodIcon = when (period) {
                            RoutinePeriod.MORNING -> Icons.Default.WbSunny
                            RoutinePeriod.AFTERNOON -> Icons.Default.WbSunny
                            RoutinePeriod.EVENING -> Icons.Default.WbTwilight
                            RoutinePeriod.NIGHT -> Icons.Default.NightsStay
                        }
                        FilterChip(
                            selected = selectedPeriod == period,
                            onClick = { selectedPeriod = period },
                            label = {
                                Text(
                                    if (bilingualMode == BilingualMode.HINDI_ASSIST) "${period.labelHi} (${period.labelEn})"
                                    else period.labelEn
                                )
                            },
                            leadingIcon = { Icon(periodIcon, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.testTag("routine_filter_${period.name.lowercase()}")
                        )
                    }
                }
            }

            // 3. Write / Speak Your Own Daily Routine Studio
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = when (bilingualMode) {
                                BilingualMode.HINDI_ASSIST -> "अपनी दिनचर्या लिखें या बोलें — CHENI से जांचें"
                                else -> "Speak or Write Your Own Daily Routine"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        BilingualText(
                            english = "Describe your morning, study time, or night routine. CHENI will check tenses, prepositions, and verbs!",
                            hindi = "अपनी सुबह, पढ़ाई या रात की दिनचर्या बताएं। CHENI आपके Tense, Preposition और Verb की जांच करेगी!",
                            mode = bilingualMode
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(DailyRoutineCatalog.starterTemplates) { (label, sample) ->
                                OutlinedButton(
                                    onClick = { customRoutineText = sample },
                                    modifier = Modifier.minimumInteractiveComponentSize()
                                ) {
                                    Text(label)
                                }
                            }
                        }
                        OutlinedTextField(
                            value = customRoutineText,
                            onValueChange = { customRoutineText = it },
                            label = { Text("Your daily routine sentence") },
                            placeholder = { Text("e.g., I wake up at 6 AM and practice English.") },
                            minLines = 2,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("custom_routine_input")
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val hasPerm = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.RECORD_AUDIO
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (hasPerm) {
                                        if (isListening) speechHelper.stopListening()
                                        else speechHelper.startListening { spoken ->
                                            customRoutineText = spoken
                                            onCheckRoutineSentence(spoken)
                                        }
                                    } else {
                                        showMicPrivacyDialog = true
                                    }
                                },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("routine_mic_button")
                            ) {
                                Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(if (isListening) "Listening..." else "Speak")
                            }
                            Button(
                                onClick = { onCheckRoutineSentence(customRoutineText) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("check_routine_button")
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Check with CHENI")
                            }
                        }

                        routineFeedback?.let { fb ->
                            Spacer(Modifier.height(4.dp))
                            GrammarFeedbackCard(
                                feedback = fb,
                                bilingualMode = bilingualMode,
                                onTryAgainClick = { customRoutineText = fb.correctedSentence }
                            )
                            TextButton(onClick = onClearRoutineFeedback) {
                                Text("Clear Feedback")
                            }
                        }
                    }
                }
            }

            // 4. Morning-to-Night Routine Step Cards
            items(filteredItems, key = { it.id }) { item ->
                DailyRoutineStepCard(
                    item = item,
                    usePastTense = usePastTense,
                    isPracticed = item.id in practicedIds,
                    bilingualMode = bilingualMode,
                    onSpeakClick = {
                        val line = if (usePastTense) item.pastTenseVariantEn else item.sentenceEn
                        speechHelper.speakEnglish(line, speechRate)
                    },
                    onCopyToPractice = {
                        customRoutineText = if (usePastTense) item.pastTenseVariantEn else item.sentenceEn
                    },
                    onMarkPracticed = { onMarkRoutinePracticed(item.id) }
                )
            }
        }
    }

    if (showMicPrivacyDialog) {
        MicrophonePrivacyDialog(
            onConfirmAllow = {
                showMicPrivacyDialog = false
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            },
            onCancel = { showMicPrivacyDialog = false }
        )
    }
}

@Composable
private fun DailyRoutineStepCard(
    item: DailyRoutineItem,
    usePastTense: Boolean,
    isPracticed: Boolean,
    bilingualMode: BilingualMode,
    onSpeakClick: () -> Unit,
    onCopyToPractice: () -> Unit,
    onMarkPracticed: () -> Unit
) {
    val activeEn = if (usePastTense) item.pastTenseVariantEn else item.sentenceEn
    val activeHi = if (usePastTense) item.pastTenseVariantHi else item.sentenceHi

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, if (isPracticed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "${item.timeLabel} • ${item.period.labelEn}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = onSpeakClick,
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Listen to routine sentence",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                if (isPracticed) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Practiced today",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            BilingualText(
                english = item.titleEn,
                hindi = item.titleHi,
                mode = bilingualMode,
                englishColor = MaterialTheme.colorScheme.primary,
                hindiColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.padding(12.dp)) {
                    BilingualText(
                        english = activeEn,
                        hindi = activeHi,
                        mode = bilingualMode
                    )
                }
            }

            BilingualText(
                english = "Tip: ${item.grammarTipEn}",
                hindi = "नियम: ${item.grammarTipHi}",
                mode = bilingualMode,
                englishColor = MaterialTheme.colorScheme.onSurfaceVariant,
                hindiColor = MaterialTheme.colorScheme.secondary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCopyToPractice,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("try_routine_${item.id}")
                ) {
                    Text("Edit & Practice")
                }
                if (!isPracticed) {
                    Button(
                        onClick = onMarkPracticed,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("mark_routine_${item.id}")
                    ) {
                        Text("Mark Practiced (+1 Star)")
                    }
                } else {
                    OutlinedButton(
                        onClick = onSpeakClick,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                    ) {
                        Text("Practiced Today ✓")
                    }
                }
            }
        }
    }
}
