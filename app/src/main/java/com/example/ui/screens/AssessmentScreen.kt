package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.curriculum.GrammarCurriculum
import com.example.model.BilingualMode
import com.example.model.SkillType
import com.example.model.StudentLevel
import com.example.speech.SpeechHelper
import com.example.ui.components.BilingualText
import com.example.ui.components.MicrophonePrivacyDialog

@Composable
fun AssessmentScreen(
    bilingualMode: BilingualMode,
    speechHelper: SpeechHelper,
    onSubmitAssessment: (Map<String, Int>, String, String) -> Pair<StudentLevel, List<String>>,
    onBack: () -> Unit,
    onStartRecommendedPlan: () -> Unit
) {
    val context = LocalContext.current
    val questions = GrammarCurriculum.assessmentQuestions
    var currentIndex by remember { mutableIntStateOf(0) }
    val selectedAnswers = remember { mutableStateMapOf<String, Int>() }
    var sampleWriting by remember { mutableStateOf("I want to speak English confidently.") }
    var sampleSpeaking by remember { mutableStateOf("") }
    var showMicDialog by remember { mutableStateOf(false) }
    var resultSummary by remember { mutableStateOf<Pair<StudentLevel, List<String>>?>(null) }

    val micLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            speechHelper.startListening { spoken -> sampleSpeaking = spoken }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack, modifier = Modifier.minimumInteractiveComponentSize()) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Student Knowledge Assessment",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "100% Anonymous • No Personal Details Asked",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (resultSummary == null) {
                val q = questions[currentIndex]
                item {
                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Question ${currentIndex + 1} of ${questions.size} • Skill: ${q.skill.titleEn}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            BilingualText(
                                english = q.promptEn,
                                hindi = q.promptHi,
                                mode = bilingualMode
                            )
                            if (q.skill == SkillType.LISTENING) {
                                Spacer(Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        speechHelper.speakEnglish(
                                            "Remember, mistakes help you learn faster! Speak a little bit every single day.",
                                            0.9f
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                    modifier = Modifier.testTag("assessment_play_audio")
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                                    Spacer(Modifier.width(6.dp))
                                    Text("Play Listening Audio")
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            q.options.forEachIndexed { idx, opt ->
                                val selected = selectedAnswers[q.id] == idx
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                    border = BorderStroke(
                                        1.dp,
                                        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .minimumInteractiveComponentSize()
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable { selectedAnswers[q.id] = idx }
                                        .testTag("assessment_opt_$idx")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (selected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Text(opt, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }

                if (currentIndex == questions.lastIndex) {
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Quick Writing & Speaking Check",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Write one sentence about your English goal, and optionally speak a short greeting:",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = sampleWriting,
                                    onValueChange = { sampleWriting = it },
                                    label = { Text("Writing sentence") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = sampleSpeaking,
                                        onValueChange = { sampleSpeaking = it },
                                        label = { Text("Speaking sentence (optional)") },
                                        modifier = Modifier.weight(1f)
                                    )
                                    OutlinedButton(
                                        onClick = {
                                            val ok = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                                            if (ok) {
                                                speechHelper.startListening { spoken -> sampleSpeaking = spoken }
                                            } else {
                                                showMicDialog = true
                                            }
                                        },
                                        modifier = Modifier.minimumInteractiveComponentSize()
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = "Speak for assessment", modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (currentIndex > 0) {
                            OutlinedButton(
                                onClick = { currentIndex-- },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                            ) {
                                Text("Previous")
                            }
                        }
                        if (currentIndex < questions.lastIndex) {
                            Button(
                                onClick = { currentIndex++ },
                                enabled = selectedAnswers.containsKey(q.id),
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("assessment_next_button")
                            ) {
                                Text("Next Question")
                            }
                        } else {
                            Button(
                                onClick = {
                                    resultSummary = onSubmitAssessment(selectedAnswers.toMap(), sampleWriting, sampleSpeaking)
                                },
                                enabled = selectedAnswers.containsKey(q.id),
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("assessment_finish_button")
                            ) {
                                Text("Create My Learning Plan")
                            }
                        }
                    }
                }
            } else {
                val (level, weakIds) = resultSummary!!
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Your Level: ${level.labelEn}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            BilingualText(
                                english = level.descriptionEn,
                                hindi = level.descriptionHi,
                                mode = bilingualMode
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Individual Learning Plan Focus Areas:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(6.dp))
                            val focusTopics = weakIds.ifEmpty { listOf("tenses", "modals", "voice") }
                                .mapNotNull { GrammarCurriculum.topicById(it) }
                            focusTopics.forEach { topic ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("${topic.order}. ${topic.titleEn} (${topic.titleHi})", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            Spacer(Modifier.height(14.dp))
                            Button(
                                onClick = onStartRecommendedPlan,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .testTag("start_personalized_plan_button")
                            ) {
                                Text("Start Personalized AI Class")
                            }
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
