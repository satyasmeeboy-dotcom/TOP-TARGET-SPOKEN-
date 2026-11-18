package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.ui.components.FolderAccent
import com.example.ui.components.FolderPresets
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.MicrophonePrivacyDialog
import com.example.ui.components.StudyFolderCard
import com.example.ui.theme.PureWhite

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
            color = FolderPresets.Amber.softTint,
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(FolderPresets.Amber.softTint, PureWhite)
                        )
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("routine_back_button")
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = FolderPresets.Amber.primary
                    )
                }
                Spacer(Modifier.width(4.dp))
                Column {
                    Text(
                        text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "दैनिक दिनचर्या (Daily Routine)" else "Daily Routine Folders",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = FolderPresets.Amber.primary
                    )
                    Text(
                        text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "सुबह से रात तक — हर समय का अलग रंगीन फ़ोल्डर" else "Morning, Afternoon, Evening & Night — tap any folder",
                        style = MaterialTheme.typography.bodySmall,
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

private fun periodAccent(period: RoutinePeriod): FolderAccent = when (period) {
    RoutinePeriod.MORNING -> FolderPresets.Amber
    RoutinePeriod.AFTERNOON -> FolderPresets.Sky
    RoutinePeriod.EVENING -> FolderPresets.Coral
    RoutinePeriod.NIGHT -> FolderPresets.Indigo
}

private fun periodIcon(period: RoutinePeriod): ImageVector = when (period) {
    RoutinePeriod.MORNING -> Icons.Default.WbSunny
    RoutinePeriod.AFTERNOON -> Icons.Default.WbSunny
    RoutinePeriod.EVENING -> Icons.Default.WbTwilight
    RoutinePeriod.NIGHT -> Icons.Default.NightsStay
}

private fun periodTimeRange(period: RoutinePeriod): String = when (period) {
    RoutinePeriod.MORNING -> "6:00 AM – 8:30 AM"
    RoutinePeriod.AFTERNOON -> "12:30 PM – 4:30 PM"
    RoutinePeriod.EVENING -> "5:30 PM – 7:45 PM"
    RoutinePeriod.NIGHT -> "8:30 PM – 10:15 PM"
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
    var activeFolder by remember { mutableStateOf<RoutinePeriod?>(null) }
    var usePastTense by remember { mutableStateOf(false) }
    var customRoutineText by remember { mutableStateOf("") }
    var showInlineStudioInFolder by remember { mutableStateOf(false) }
    var showMicPrivacyDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = activeFolder != null) {
        activeFolder = null
        showInlineStudioInFolder = false
    }

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

    val onStartMic: () -> Unit = {
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
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val isWide = maxWidth >= 600.dp

        if (activeFolder == null) {
            // HUB VIEW: Progress Summary + 4 Colourful Time-of-Day Routine Folders + Practice Studio
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 720.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Colourful Summary & Tense Switcher
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.5.dp, FolderPresets.Emerald.primary.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(FolderPresets.Emerald.softTint, FolderPresets.Sky.softTint)
                                    )
                                )
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = when (bilingualMode) {
                                            BilingualMode.HINDI_ASSIST -> "दैनिक दिनचर्या फ़ोल्डर्स (Morning to Night)"
                                            else -> "Morning-to-Night Routine Folders"
                                        },
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = FolderPresets.Emerald.primary
                                    )
                                    Text(
                                        text = "$practicedCount / ${allItems.size} routine sentences practiced today",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Button(
                                    onClick = {
                                        val script = allItems.joinToString(" ") {
                                            if (usePastTense) it.pastTenseVariantEn else it.sentenceEn
                                        }
                                        speechHelper.speakEnglish(script, speechRate)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = FolderPresets.Emerald.primary),
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("routine_listen_all_button")
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text("Listen All")
                                }
                            }

                            LinearProgressIndicator(
                                progress = { (practicedCount.toFloat() / allItems.size.coerceAtLeast(1)).coerceIn(0f, 1f) },
                                color = FolderPresets.Emerald.primary,
                                trackColor = PureWhite,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(9.dp)
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

                // 2. Section Heading for 4 Routine Folders
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                "समय के अनुसार 4 दिनचर्या फ़ोल्डर (फ़ोल्डर खोलें)"
                            else "4 Time-of-Day Routine Folders",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                "नीचे किसी भी फ़ोल्डर पर टैप करें और उसके वाक्य सुनें व अभ्यास करें"
                            else "Tap a folder below to open its 4 spoken routine sentences",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // 3. Responsive 4 Colourful Routine Folders (1-col big cards on phone, 2-col on tablet)
                val periods = RoutinePeriod.entries
                val periodRows = if (isWide) periods.chunked(2) else periods.map { listOf(it) }
                items(periodRows) { rowPeriods ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowPeriods.forEach { period ->
                            val periodItems = remember(period) { allItems.filter { it.period == period } }
                            val doneInPeriod = periodItems.count { it.id in practicedIds }
                            val accent = periodAccent(period)
                            val sampleSentence = periodItems.firstOrNull()?.let {
                                if (usePastTense) it.pastTenseVariantEn else it.sentenceEn
                            }

                            StudyFolderCard(
                                tabLabel = "${period.labelEn.uppercase()} • ${periodTimeRange(period)}",
                                title = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                    "${period.labelHi} (${period.labelEn} Routine)"
                                else "${period.labelEn} Routine",
                                subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY)
                                    "${periodItems.size} Spoken Sentences & Tips"
                                else "${period.labelHi} • ${periodItems.size} बोलने के वाक्य",
                                badgeText = "$doneInPeriod/${periodItems.size} Practiced",
                                icon = periodIcon(period),
                                accent = accent,
                                previewLine = sampleSentence?.let { "Sample: \"$it\"" },
                                progress = doneInPeriod.toFloat() / periodItems.size.coerceAtLeast(1),
                                actionLabel = "Open ${period.labelEn}",
                                onClick = {
                                    activeFolder = period
                                    showInlineStudioInFolder = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("routine_folder_${period.name.lowercase()}")
                            )
                        }
                        if (isWide && rowPeriods.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }

                // 4. Write / Speak Your Own Daily Routine Studio
                item {
                    RoutinePracticeStudioCard(
                        bilingualMode = bilingualMode,
                        isListening = isListening,
                        customRoutineText = customRoutineText,
                        routineFeedback = routineFeedback,
                        accent = FolderPresets.Teal,
                        onTextChange = { customRoutineText = it },
                        onSelectTemplate = { customRoutineText = it },
                        onMicClick = onStartMic,
                        onCheckClick = { onCheckRoutineSentence(customRoutineText) },
                        onClearFeedback = onClearRoutineFeedback
                    )
                }
            }
        } else {
            // FOLDER DETAIL VIEW: Only sentences belonging to the selected Time-of-Day Folder!
            val folderPeriod = activeFolder!!
            val accent = periodAccent(folderPeriod)
            val folderItems = remember(folderPeriod) { allItems.filter { it.period == folderPeriod } }
            val doneInFolder = folderItems.count { it.id in practicedIds }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 720.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Vibrant Period Folder Header & Back Bar
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.55f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(accent.softTint, PureWhite)
                                    )
                                )
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = {
                                        activeFolder = null
                                        showInlineStudioInFolder = false
                                    },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("routine_folders_back_button")
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "All Routine Folders",
                                        tint = accent.primary
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        "All Folders",
                                        color = accent.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Button(
                                    onClick = {
                                        val script = folderItems.joinToString(" ") {
                                            if (usePastTense) it.pastTenseVariantEn else it.sentenceEn
                                        }
                                        speechHelper.speakEnglish(script, speechRate)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = accent.primary),
                                    modifier = Modifier.minimumInteractiveComponentSize()
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text("Listen Folder")
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(accent.primary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(periodIcon(folderPeriod), contentDescription = null, tint = PureWhite, modifier = Modifier.size(26.dp))
                                }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = "${folderPeriod.labelEn} Routine Folder (${folderPeriod.labelHi})",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = accent.primary
                                    )
                                    Text(
                                        text = "${periodTimeRange(folderPeriod)} • $doneInFolder / ${folderItems.size} practiced",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            LinearProgressIndicator(
                                progress = { doneInFolder.toFloat() / folderItems.size.coerceAtLeast(1) },
                                color = accent.primary,
                                trackColor = accent.softTint,
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

                // 2. Inline Practice Studio inside folder when user taps "Edit & Practice"
                if (showInlineStudioInFolder || routineFeedback != null) {
                    item {
                        RoutinePracticeStudioCard(
                            bilingualMode = bilingualMode,
                            isListening = isListening,
                            customRoutineText = customRoutineText,
                            routineFeedback = routineFeedback,
                            accent = accent,
                            onTextChange = { customRoutineText = it },
                            onSelectTemplate = { customRoutineText = it },
                            onMicClick = onStartMic,
                            onCheckClick = { onCheckRoutineSentence(customRoutineText) },
                            onClearFeedback = {
                                onClearRoutineFeedback()
                                showInlineStudioInFolder = false
                            }
                        )
                    }
                }

                // 3. Only the 4 Sentences Inside This Time-of-Day Folder
                items(folderItems, key = { it.id }) { item ->
                    DailyRoutineStepCard(
                        item = item,
                        accent = accent,
                        usePastTense = usePastTense,
                        isPracticed = item.id in practicedIds,
                        bilingualMode = bilingualMode,
                        onSpeakClick = {
                            val line = if (usePastTense) item.pastTenseVariantEn else item.sentenceEn
                            speechHelper.speakEnglish(line, speechRate)
                        },
                        onCopyToPractice = {
                            customRoutineText = if (usePastTense) item.pastTenseVariantEn else item.sentenceEn
                            showInlineStudioInFolder = true
                        },
                        onMarkPracticed = { onMarkRoutinePracticed(item.id) }
                    )
                }
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
private fun RoutinePracticeStudioCard(
    bilingualMode: BilingualMode,
    isListening: Boolean,
    customRoutineText: String,
    routineFeedback: GrammarMistakeFeedback?,
    accent: FolderAccent,
    onTextChange: (String) -> Unit,
    onSelectTemplate: (String) -> Unit,
    onMicClick: () -> Unit,
    onCheckClick: () -> Unit,
    onClearFeedback: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.5.dp, accent.primary.copy(alpha = 0.45f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(accent.primary)
            )
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = when (bilingualMode) {
                        BilingualMode.HINDI_ASSIST -> "अपनी दिनचर्या लिखें या बोलें — व्याकरण जांचें"
                        else -> "Speak or Write Your Own Daily Routine"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = accent.primary
                )
                BilingualText(
                    english = "Describe your morning, study time, or night routine to check tenses, prepositions, and verbs!",
                    hindi = "अपनी सुबह, पढ़ाई या रात की दिनचर्या बताएं और Tense, Preposition व Verb की जांच करें!",
                    mode = bilingualMode
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(DailyRoutineCatalog.starterTemplates) { (label, sample) ->
                        OutlinedButton(
                            onClick = { onSelectTemplate(sample) },
                            border = BorderStroke(1.dp, accent.primary),
                            modifier = Modifier.minimumInteractiveComponentSize()
                        ) {
                            Text(label, color = accent.primary)
                        }
                    }
                }
                OutlinedTextField(
                    value = customRoutineText,
                    onValueChange = onTextChange,
                    label = { Text("Your daily routine sentence") },
                    placeholder = { Text("e.g., I wake up at 6 AM and practice English.") },
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_routine_input")
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onMicClick,
                        border = BorderStroke(1.2.dp, accent.primary),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("routine_mic_button")
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = null, tint = accent.primary, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(if (isListening) "Listening..." else "Speak", color = accent.primary)
                    }
                    Button(
                        onClick = onCheckClick,
                        colors = ButtonDefaults.buttonColors(containerColor = accent.primary),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("check_routine_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Check Grammar")
                    }
                }

                routineFeedback?.let { fb ->
                    Spacer(Modifier.height(4.dp))
                    GrammarFeedbackCard(
                        feedback = fb,
                        bilingualMode = bilingualMode,
                        onTryAgainClick = { onTextChange(fb.correctedSentence) }
                    )
                    TextButton(onClick = onClearFeedback) {
                        Text("Clear Feedback")
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyRoutineStepCard(
    item: DailyRoutineItem,
    accent: FolderAccent,
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(
            width = if (isPracticed) 2.dp else 1.5.dp,
            color = if (isPracticed) accent.primary else accent.primary.copy(alpha = 0.42f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(accent.primary)
            )
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accent.softTint,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, accent.primary.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = "${item.timeLabel} • ${item.period.labelEn}",
                            style = MaterialTheme.typography.labelMedium,
                            color = accent.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
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
                            tint = accent.primary
                        )
                    }
                    if (isPracticed) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Practiced today",
                            tint = accent.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                BilingualText(
                    english = item.titleEn,
                    hindi = item.titleHi,
                    mode = bilingualMode,
                    englishColor = accent.primary,
                    hindiColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    color = accent.softTint.copy(alpha = 0.72f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, accent.primary.copy(alpha = 0.25f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(Modifier.padding(14.dp)) {
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
                    hindiColor = accent.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onCopyToPractice,
                        border = BorderStroke(1.2.dp, accent.primary),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("try_routine_${item.id}")
                    ) {
                        Text("Edit & Practice", color = accent.primary)
                    }
                    if (!isPracticed) {
                        Button(
                            onClick = onMarkPracticed,
                            colors = ButtonDefaults.buttonColors(containerColor = accent.primary),
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
                            border = BorderStroke(1.2.dp, accent.primary),
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                        ) {
                            Text("Practiced Today ✓", color = accent.primary)
                        }
                    }
                }
            }
        }
    }
}
