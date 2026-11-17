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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.data.curriculum.GrammarCurriculum
import com.example.model.BilingualMode
import com.example.model.GrammarTopic
import com.example.model.TeacherMessage
import com.example.model.TeacherStage
import com.example.model.WhiteboardState
import com.example.speech.SpeechHelper
import com.example.ui.components.AnimatedTeacherAvatar
import com.example.ui.components.BilingualText
import com.example.ui.components.DigitalWhiteboardCard
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.MicrophonePrivacyDialog
import com.example.ui.components.SampleAiNoticeBanner

@Composable
fun TeacherClassroomScreen(
    activeTopic: GrammarTopic,
    teacherStage: TeacherStage,
    whiteboard: WhiteboardState,
    messages: List<TeacherMessage>,
    isThinking: Boolean,
    isSpeaking: Boolean,
    isListening: Boolean,
    speechError: String?,
    autoSpeak: Boolean,
    speechRate: Float,
    bilingualMode: BilingualMode,
    speechHelper: SpeechHelper,
    onSelectTopic: (GrammarTopic) -> Unit,
    onSendMessage: (String) -> Unit,
    onAdvanceStage: () -> Unit,
    onToggleAutoSpeak: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var showTopicMenu by remember { mutableStateOf(false) }
    var showMicPrivacyDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            speechHelper.startListening { spoken ->
                inputText = spoken
                onSendMessage(spoken)
            }
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            SampleAiNoticeBanner()
            Spacer(Modifier.height(8.dp))

            // Top Classroom Controls + Animated Teacher + Whiteboard
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimatedTeacherAvatar(
                    isSpeaking = isSpeaking,
                    isListening = isListening,
                    isThinking = isThinking,
                    bilingualMode = bilingualMode,
                    avatarSize = 78.dp
                )
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box {
                            OutlinedButton(
                                onClick = { showTopicMenu = true },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("teacher_topic_picker")
                            ) {
                                Text("Topic: ${activeTopic.titleEn}", maxLines = 1)
                            }
                            DropdownMenu(
                                expanded = showTopicMenu,
                                onDismissRequest = { showTopicMenu = false }
                            ) {
                                GrammarCurriculum.allTopics.forEach { topic ->
                                    DropdownMenuItem(
                                        text = { Text("${topic.order}. ${topic.titleEn} (${topic.titleHi})") },
                                        onClick = {
                                            showTopicMenu = false
                                            onSelectTopic(topic)
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.width(6.dp))
                        IconButton(
                            onClick = {
                                if (autoSpeak) speechHelper.stopSpeaking()
                                onToggleAutoSpeak(!autoSpeak)
                            },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("teacher_tts_toggle")
                        ) {
                            Icon(
                                imageVector = if (autoSpeak) Icons.Default.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                                contentDescription = if (autoSpeak) "Mute Teacher Voice" else "Enable Teacher Voice",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(TeacherStage.entries) { st ->
                            FilterChip(
                                selected = st == teacherStage,
                                onClick = onAdvanceStage,
                                label = { Text("${st.stepNumber}. ${st.labelEn}") }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            DigitalWhiteboardCard(whiteboard = whiteboard, bilingualMode = bilingualMode)
            Spacer(Modifier.height(8.dp))

            // Conversation Feed
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages, key = { it.id }) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.fromTeacher) Arrangement.Start else Arrangement.End
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (msg.fromTeacher) MaterialTheme.colorScheme.surface
                            else MaterialTheme.colorScheme.primary,
                            border = if (msg.fromTeacher) BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val teacherHeader = when (bilingualMode) {
                                        BilingualMode.HINDI_ASSIST -> "CHENI (AI शिक्षक व दोस्त)"
                                        else -> "CHENI (AI Teacher & Friend)"
                                    }
                                    val studentHeader = if (bilingualMode == BilingualMode.HINDI_ASSIST) "आप (You)" else "You"
                                    Text(
                                        text = if (msg.fromTeacher) teacherHeader else studentHeader,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (msg.fromTeacher) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onPrimary
                                    )
                                    if (msg.fromTeacher) {
                                        IconButton(
                                            onClick = { speechHelper.speakEnglish(msg.englishText, speechRate) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Listen to teacher message",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                if (msg.fromTeacher) {
                                    BilingualText(
                                        english = msg.englishText,
                                        hindi = msg.hindiText,
                                        mode = bilingualMode
                                    )
                                } else {
                                    Text(
                                        text = msg.englishText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                msg.feedback?.let { fb ->
                                    Spacer(Modifier.height(8.dp))
                                    GrammarFeedbackCard(
                                        feedback = fb,
                                        bilingualMode = bilingualMode,
                                        onTryAgainClick = {
                                            inputText = fb.correctedSentence
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (!speechError.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = speechError,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(8.dp))

            // Quick Next Step + Input + Mic Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onAdvanceStage,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("teacher_next_step_button")
                ) {
                    Icon(Icons.Default.SkipNext, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Next Step")
                }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Type or tap Mic to speak...") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("teacher_input_field")
                )
                IconButton(
                    onClick = {
                        val hasPerm = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                        if (hasPerm) {
                            if (isListening) speechHelper.stopListening()
                            else speechHelper.startListening { spoken ->
                                inputText = spoken
                                onSendMessage(spoken)
                            }
                        } else {
                            showMicPrivacyDialog = true
                        }
                    },
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("teacher_mic_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Speak answer with microphone",
                        tint = if (isListening) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("teacher_send_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Answer")
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
