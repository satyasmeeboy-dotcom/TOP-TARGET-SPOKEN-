package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.curriculum.GrammarCurriculum
import com.example.data.local.AchievementBadgeEntity
import com.example.data.local.LearnedWordEntity
import com.example.data.local.MistakeLogEntity
import com.example.data.local.StudentProfileEntity
import com.example.data.local.TopicProgressEntity
import com.example.model.BilingualMode
import com.example.model.CertificateSpec
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldTertiary

@Composable
fun ProgressAndPrivacyScreen(
    profile: StudentProfileEntity?,
    topicProgress: List<TopicProgressEntity>,
    mistakes: List<MistakeLogEntity>,
    learnedWords: List<LearnedWordEntity>,
    badges: List<AchievementBadgeEntity>,
    certificates: List<CertificateSpec>,
    bilingualMode: BilingualMode = BilingualMode.BILINGUAL,
    isOnline: Boolean,
    speechRate: Float,
    optionalSyncEnabled: Boolean,
    onSpeechRateChange: (Float) -> Unit,
    onToggleOptionalSync: (Boolean) -> Unit,
    onDeleteAllData: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var previewCertificate by remember { mutableStateOf<CertificateSpec?>(null) }

    val attempted = profile?.totalAttempted ?: 0
    val correct = profile?.totalCorrect ?: 0
    val incorrect = (attempted - correct).coerceAtLeast(0)
    val accuracy = if (attempted > 0) ((correct.toFloat() / attempted) * 100).toInt() else 0
    val completedTopics = topicProgress.count { it.isCompleted }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Performance Analysis
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Student Performance Analysis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(
                            "Attempted: $attempted  •  Correct: $correct  •  Incorrect: $incorrect  •  Accuracy: $accuracy%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Completed Grammar Lessons: $completedTopics / ${GrammarCurriculum.allTopics.size}  •  Words Mastered: ${learnedWords.size}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        SkillProgressRow("Grammar Mastery", profile?.grammarMastery ?: 10)
                        SkillProgressRow("Vocabulary Mastery", profile?.vocabularyMastery ?: 10)
                        SkillProgressRow("Speaking Progress", profile?.speakingMastery ?: 10)
                        SkillProgressRow("Listening Progress", profile?.listeningMastery ?: 10)
                        SkillProgressRow("Reading Progress", profile?.readingMastery ?: 10)
                        SkillProgressRow("Writing Progress", profile?.writingMastery ?: 10)

                        if (mistakes.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Weak Areas Detected: ${mistakes.take(3).joinToString { it.category }}",
                                style = MaterialTheme.typography.labelMedium,
                                color = ErrorCrimson,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // 2. Achievement Badges
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(6.dp))
                            Text("Achievement Badges (${badges.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        badges.forEach { b ->
                            Text("★ ${b.titleEn} (${b.titleHi}) — ${b.descriptionEn}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            // 3. Certificates Section
            item {
                Text("Achievement Certificates (No Real Name Required)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(certificates, key = { it.id }) { cert ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, if (cert.isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (cert.isUnlocked) Icons.Default.Verified else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (cert.isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(cert.titleEn, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(cert.requirementDescription, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { cert.progressRatio },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50))
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { previewCertificate = cert },
                            enabled = cert.isUnlocked
                        ) {
                            Text(if (cert.isUnlocked) "View" else "Locked")
                        }
                    }
                }
            }

            // 4. Offline vs Online Mode & Voice Settings
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(if (isOnline) Icons.Default.CloudDone else Icons.Default.CloudOff, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = if (isOnline) "Network: Online • Offline Mode Also Active" else "Network: Offline Mode Active",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text("Offline Features: All 18 Grammar Lessons, Saved Vocab, Gen Z Words, 9 Games, Reading Stories, Saved News, Quizzes & Local Progress.", style = MaterialTheme.typography.bodySmall)
                        Text("Online Features: Live cloud AI conversations (when Firebase AI Logic is enabled), cloud speech recognition, and fresh news sync.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Text("Teacher Speech Speed: ${String.format("%.2fx", speechRate)}", style = MaterialTheme.typography.labelMedium)
                        Slider(value = speechRate, onValueChange = onSpeechRateChange, valueRange = 0.65f..1.35f)

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.weight(1f)) {
                                Text("Optional Sync When Online", style = MaterialTheme.typography.titleSmall)
                                Text("Disabled by default—your progress stays 100% on your device.", style = MaterialTheme.typography.bodySmall)
                            }
                            Switch(checked = optionalSyncEnabled, onCheckedChange = onToggleOptionalSync)
                        }
                    }
                }
            }

            // 5. Privacy Policy & Delete All Local Data
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmGoldContainer.copy(alpha = 0.55f)),
                    border = BorderStroke(1.dp, WarmGoldTertiary.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PrivacyTip, contentDescription = null, tint = WarmGoldTertiary)
                            Spacer(Modifier.width(8.dp))
                            Text("Student Privacy Guarantee", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            "• No full name, phone, email, address, DOB, school, photo, or ID is ever requested.\n" +
                                "• No login or paid subscription—100% Free for every student.\n" +
                                "• Camera is always OFF. Microphone activates ONLY when you tap Speak.\n" +
                                "• No voice recordings are stored; progress stays privately on this device.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Button(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = ErrorCrimson),
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .testTag("delete_all_local_data_button")
                        ) {
                            Icon(Icons.Default.DeleteForever, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text("Delete All Local Learning Data")
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete All Local Learning Data?") },
            text = { Text("This permanently deletes your nickname, quiz scores, mistake logs, learned words, and badges from this device and generates a new anonymous ID.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteAllData()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorCrimson),
                    modifier = Modifier.testTag("confirm_delete_all_data_button")
                ) {
                    Text("Delete Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    previewCertificate?.let { cert ->
        val displayHolder = profile?.nickname?.takeIf { it.isNotBlank() }
            ?: "Anonymous Learner (${profile?.anonymousStudentId ?: "TT-0001"})"
        AlertDialog(
            onDismissRequest = { previewCertificate = null },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.img_certificate_seal),
                    contentDescription = stringResource(id = R.string.certificate_seal_desc),
                    modifier = Modifier.size(84.dp)
                )
            },
            title = { Text("TOP TARGET SPOKEN ENGLISH", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Certificate of Achievement", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Awarded to:", style = MaterialTheme.typography.labelSmall)
                    Text(displayHolder, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("for earning: ${cert.titleEn}", style = MaterialTheme.typography.bodyMedium)
                    Text(cert.titleHi, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = when (bilingualMode) {
                            BilingualMode.HINDI_ASSIST -> "संस्थापक एवं निर्माता: CHENI"
                            else -> "Founder & Creator: CHENI"
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            confirmButton = {
                Button(onClick = { previewCertificate = null }) { Text("Close") }
            }
        )
    }
}

@Composable
private fun SkillProgressRow(label: String, percent: Int) {
    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
            Text("$percent%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { (percent.coerceIn(0, 100)) / 100f },
            modifier = Modifier.fillMaxWidth().height(7.dp).clip(RoundedCornerShape(50))
        )
    }
}
