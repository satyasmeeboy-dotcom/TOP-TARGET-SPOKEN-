package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.BilingualMode
import com.example.model.GrammarMistakeFeedback
import com.example.model.WhiteboardState
import com.example.ui.CelebrationBanner
import com.example.ui.theme.ChalkboardBorder
import com.example.ui.theme.ChalkboardGreen
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryContainer
import com.example.ui.theme.ErrorContainerLight
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.FolderAmber
import com.example.ui.theme.FolderAmberLight
import com.example.ui.theme.FolderCoral
import com.example.ui.theme.FolderCoralLight
import com.example.ui.theme.FolderEmerald
import com.example.ui.theme.FolderEmeraldLight
import com.example.ui.theme.FolderIndigo
import com.example.ui.theme.FolderIndigoLight
import com.example.ui.theme.FolderRose
import com.example.ui.theme.FolderRoseLight
import com.example.ui.theme.FolderSky
import com.example.ui.theme.FolderSkyLight
import com.example.ui.theme.FolderTeal
import com.example.ui.theme.FolderTealLight
import com.example.ui.theme.FolderViolet
import com.example.ui.theme.FolderVioletLight
import com.example.ui.theme.HindiFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldTertiary

@Composable
fun BilingualText(
    english: String,
    hindi: String,
    mode: BilingualMode,
    modifier: Modifier = Modifier,
    englishColor: Color = MaterialTheme.colorScheme.onSurface,
    hindiColor: Color = MaterialTheme.colorScheme.primary
) {
    Column(modifier = modifier) {
        when (mode) {
            BilingualMode.ENGLISH_ONLY -> {
                Text(
                    text = english,
                    style = MaterialTheme.typography.bodyLarge,
                    color = englishColor
                )
            }
            BilingualMode.BILINGUAL -> {
                Text(
                    text = english,
                    style = MaterialTheme.typography.bodyLarge,
                    color = englishColor
                )
                if (hindi.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = hindi,
                        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = HindiFontFamily),
                        color = hindiColor
                    )
                }
            }
            BilingualMode.HINDI_ASSIST -> {
                if (hindi.isNotBlank()) {
                    Text(
                        text = hindi,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = HindiFontFamily,
                            fontWeight = FontWeight.Medium
                        ),
                        color = englishColor
                    )
                    Spacer(Modifier.height(4.dp))
                }
                Text(
                    text = english,
                    style = MaterialTheme.typography.bodyMedium,
                    color = hindiColor
                )
            }
        }
    }
}

@Composable
fun TopBrandHeader(
    isOnline: Boolean,
    bilingualMode: BilingualMode,
    onCycleBilingual: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon_emblem),
                contentDescription = stringResource(id = R.string.brand_badge_desc),
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "TOP TARGET SPOKEN ENGLISH",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = when (bilingualMode) {
                        BilingualMode.HINDI_ASSIST -> "अंग्रेज़ी सीखें, आत्मविश्वास से बोलें!"
                        else -> "Learn English, Speak Confidently!"
                    },
                    style = if (bilingualMode == BilingualMode.HINDI_ASSIST) {
                        MaterialTheme.typography.labelSmall.copy(fontFamily = HindiFontFamily)
                    } else {
                        MaterialTheme.typography.labelSmall
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(6.dp))
            Surface(
                color = if (isOnline) EmeraldPrimaryContainer else WarmGoldContainer,
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(end = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isOnline) Icons.Default.CloudDone else Icons.Default.CloudOff,
                        contentDescription = if (isOnline) "Online" else "Offline Mode",
                        modifier = Modifier.size(14.dp),
                        tint = if (isOnline) EmeraldPrimary else WarmGoldTertiary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = if (isOnline) "Online" else "Offline",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isOnline) EmeraldPrimary else WarmGoldTertiary
                    )
                }
            }
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clip(RoundedCornerShape(50))
                    .clickable(onClick = onCycleBilingual)
                    .testTag("language_mode_toggle")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Switch Language Mode",
                        tint = PureWhite,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = bilingualMode.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = PureWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedTeacherAvatar(
    isSpeaking: Boolean,
    isListening: Boolean,
    isThinking: Boolean,
    bilingualMode: BilingualMode = BilingualMode.BILINGUAL,
    avatarSize: Dp = 88.dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "teacher_anim")
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath_scale"
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "speak_pulse"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isSpeaking || isListening) {
                Box(
                    modifier = Modifier
                        .size(avatarSize + 18.dp)
                        .scale(pulseScale)
                        .clip(CircleShape)
                        .background(
                            if (isListening) WarmGoldTertiary.copy(alpha = 0.22f)
                            else EmeraldPrimary.copy(alpha = 0.22f)
                        )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.img_teacher_avatar),
                contentDescription = stringResource(id = R.string.teacher_avatar_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(avatarSize)
                    .scale(if (isSpeaking) 1.04f else breathScale)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = when {
                            isListening -> WarmGoldTertiary
                            isSpeaking -> EmeraldPrimary
                            else -> MaterialTheme.colorScheme.primaryContainer
                        },
                        shape = CircleShape
                    )
            )
        }
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(50),
            color = when {
                isListening -> WarmGoldContainer
                isSpeaking -> EmeraldPrimaryContainer
                isThinking -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when {
                        isListening -> Icons.Default.Mic
                        isSpeaking -> Icons.AutoMirrored.Filled.VolumeUp
                        isThinking -> Icons.Default.AutoAwesome
                        else -> Icons.Default.GraphicEq
                    },
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = when {
                        isListening -> WarmGoldTertiary
                        else -> EmeraldPrimary
                    }
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = when (bilingualMode) {
                        BilingualMode.HINDI_ASSIST -> when {
                            isListening -> "CHENI सुन रही हैं..."
                            isSpeaking -> "CHENI बोल रही हैं..."
                            isThinking -> "CHENI सोच रही हैं..."
                            else -> "CHENI • AI शिक्षक व दोस्त"
                        }
                        else -> when {
                            isListening -> "CHENI is Listening..."
                            isSpeaking -> "CHENI is Speaking..."
                            isThinking -> "CHENI is Thinking..."
                            else -> "CHENI • AI Teacher & Friend"
                        }
                    },
                    style = if (bilingualMode == BilingualMode.HINDI_ASSIST) {
                        MaterialTheme.typography.labelSmall.copy(fontFamily = HindiFontFamily)
                    } else {
                        MaterialTheme.typography.labelSmall
                    },
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun DigitalWhiteboardCard(
    whiteboard: WhiteboardState,
    bilingualMode: BilingualMode,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = ChalkboardGreen,
        border = BorderStroke(3.dp, ChalkboardBorder),
        shadowElevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DIGITAL WHITEBOARD • ${whiteboard.topicTitle}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFA7F3D0),
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = Color.White.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = whiteboard.stageLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = PureWhite,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            BilingualText(
                english = whiteboard.ruleEnglish,
                hindi = whiteboard.ruleHindi,
                mode = bilingualMode,
                englishColor = PureWhite,
                hindiColor = Color(0xFFD1FAE5)
            )
            if (!whiteboard.highlightedCorrection.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Surface(
                    color = Color(0xFFFEF3C7),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Corrected Form: ${whiteboard.highlightedCorrection}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF78350F),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            if (whiteboard.examples.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                Spacer(Modifier.height(6.dp))
                whiteboard.examples.take(3).forEach { example ->
                    Text(
                        text = "• $example",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFECFDF5),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SampleAiNoticeBanner(modifier: Modifier = Modifier) {
    Surface(
        color = WarmGoldContainer,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, WarmGoldTertiary.copy(alpha = 0.35f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = WarmGoldTertiary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.ai_notice_firebase),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF78350F)
            )
        }
    }
}

@Composable
fun GrammarFeedbackCard(
    feedback: GrammarMistakeFeedback,
    bilingualMode: BilingualMode,
    onTryAgainClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (feedback.isCorrect) EmeraldPrimaryContainer else PureWhite
        ),
        border = BorderStroke(
            1.5.dp,
            if (feedback.isCorrect) EmeraldPrimary else ErrorCrimson.copy(alpha = 0.45f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (feedback.isCorrect) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = if (feedback.isCorrect) EmeraldPrimary else ErrorCrimson
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (feedback.isCorrect) "Clean & Correct!" else "${feedback.mistakeCategory} Detected",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (feedback.isCorrect) EmeraldPrimary else ErrorCrimson
                )
            }
            Spacer(Modifier.height(8.dp))
            if (feedback.originalSentence.isNotBlank()) {
                Surface(
                    color = if (feedback.isCorrect) PureWhite else ErrorContainerLight,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Text("1. Your Sentence:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text(feedback.originalSentence, style = MaterialTheme.typography.bodyMedium)
                        if (!feedback.isCorrect) {
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Highlighted Issue: ${feedback.highlightedMistake}",
                                style = MaterialTheme.typography.labelSmall,
                                color = ErrorCrimson,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
            Surface(
                color = EmeraldPrimaryContainer.copy(alpha = 0.7f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(10.dp)) {
                    Text(
                        text = "2. Corrected Sentence:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                    Text(
                        text = feedback.correctedSentence,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("3. Explanation & Hindi Rule:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(2.dp))
            BilingualText(
                english = feedback.whyIncorrectEn,
                hindi = feedback.grammarRuleHi,
                mode = bilingualMode
            )
            if (feedback.extraExamples.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text("4. Two More Examples:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                feedback.extraExamples.forEach { ex ->
                    Text("• $ex", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 2.dp))
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = feedback.compliment,
                style = MaterialTheme.typography.labelMedium,
                color = EmeraldPrimary,
                fontWeight = FontWeight.SemiBold
            )
            if (onTryAgainClick != null && !feedback.isCorrect) {
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onTryAgainClick,
                    modifier = Modifier.testTag("try_again_grammar_button")
                ) {
                    Text(feedback.tryAgainPrompt)
                }
            }
        }
    }
}

@Composable
fun ComplimentCelebrationDialog(
    celebration: CelebrationBanner?,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(visible = celebration != null) {
        if (celebration != null) {
            AlertDialog(
                onDismissRequest = onDismiss,
                icon = {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(EmeraldPrimary, WarmGoldTertiary))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Reward Star",
                            tint = PureWhite,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = celebration.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = celebration.message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (celebration.starsEarned > 0) {
                            Spacer(Modifier.height(10.dp))
                            Surface(
                                color = WarmGoldContainer,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "+${celebration.starsEarned} Stars Earned!",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = WarmGoldTertiary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        modifier = Modifier.testTag("dismiss_celebration_button")
                    ) {
                        Text("Keep Learning!")
                    }
                }
            )
        }
    }
}

@Composable
fun MicrophonePrivacyDialog(
    onConfirmAllow: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(
                imageVector = Icons.Default.PrivacyTip,
                contentDescription = null,
                tint = EmeraldPrimary,
                modifier = Modifier.size(34.dp)
            )
        },
        title = {
            Text("Microphone Permission & Privacy")
        },
        text = {
            Column {
                Text(
                    "Top Target Spoken English only listens while you tap Speak. Your camera remains OFF, no audio recordings are stored, and nothing is shared with third parties.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "माइक केवल आपके बोलने के समय चालू होगा। कैमरा बंद रहता है और कोई रिकॉर्डिंग सेव नहीं की जाती।",
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = HindiFontFamily),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmAllow,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                modifier = Modifier.testTag("confirm_mic_privacy_button")
            ) {
                Text("Allow & Speak")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Type Instead")
            }
        }
    )
}

data class FolderAccent(
    val primary: Color,
    val softTint: Color
)

object FolderPresets {
    val Emerald = FolderAccent(FolderEmerald, FolderEmeraldLight)
    val Sky = FolderAccent(FolderSky, FolderSkyLight)
    val Indigo = FolderAccent(FolderIndigo, FolderIndigoLight)
    val Amber = FolderAccent(FolderAmber, FolderAmberLight)
    val Violet = FolderAccent(FolderViolet, FolderVioletLight)
    val Teal = FolderAccent(FolderTeal, FolderTealLight)
    val Coral = FolderAccent(FolderCoral, FolderCoralLight)
    val Rose = FolderAccent(FolderRose, FolderRoseLight)
}

/**
 * Creative, large, colourful Study Folder card with a top-left folder tab lip, peeking paper sheet,
 * vibrant gradient pocket body, crisp badge, optional progress bar, and bold readable typography.
 */
@Composable
fun StudyFolderCard(
    tabLabel: String,
    title: String,
    subtitle: String,
    badgeText: String,
    icon: ImageVector,
    accent: FolderAccent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    previewLine: String? = null,
    progress: Float? = null,
    actionLabel: String = "Open Folder"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        // Top folder tab + peeking white sheet
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Surface(
                color = accent.primary,
                shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = tabLabel,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                )
            }
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .padding(end = 14.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 10.dp))
                    .background(PureWhite)
                    .border(
                        width = 1.dp,
                        color = accent.primary.copy(alpha = 0.28f),
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 10.dp)
                    )
            )
        }

        // Main colourful folder pocket body
        Surface(
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            border = BorderStroke(1.8.dp, accent.primary.copy(alpha = 0.52f)),
            shadowElevation = 3.dp,
            color = PureWhite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PureWhite,
                                accent.softTint.copy(alpha = 0.92f)
                            )
                        )
                    )
            ) {
                // Top vibrant accent stripe
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(accent.primary)
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(accent.primary, accent.primary.copy(alpha = 0.82f))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = PureWhite,
                                modifier = Modifier.size(27.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = accent.primary,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    if (!previewLine.isNullOrBlank()) {
                        Surface(
                            color = PureWhite.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, accent.primary.copy(alpha = 0.22f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = previewLine,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }

                    if (progress != null) {
                        LinearProgressIndicator(
                            progress = { progress.coerceIn(0f, 1f) },
                            color = accent.primary,
                            trackColor = PureWhite.copy(alpha = 0.8f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(7.dp)
                                .clip(RoundedCornerShape(50))
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Surface(
                                color = accent.primary.copy(alpha = 0.14f),
                                shape = RoundedCornerShape(50),
                                border = BorderStroke(1.dp, accent.primary.copy(alpha = 0.28f))
                            ) {
                                Text(
                                    text = badgeText,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = accent.primary,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                )
                            }
                        }
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            color = accent.primary,
                            shape = RoundedCornerShape(50)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = actionLabel,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite,
                                    maxLines = 1
                                )
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
