package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.curriculum.GrammarCurriculum
import com.example.data.local.DailyStudyTaskEntity
import com.example.data.local.StudentProfileEntity
import com.example.data.local.TopicProgressEntity
import com.example.model.BilingualMode
import com.example.model.StudentLevel
import com.example.ui.components.BilingualText

@Composable
fun DashboardScreen(
    profile: StudentProfileEntity?,
    topicProgress: List<TopicProgressEntity>,
    todayTasks: List<DailyStudyTaskEntity>,
    dailyCompliment: Pair<String, String>,
    bilingualMode: BilingualMode,
    onStartAssessment: () -> Unit,
    onOpenTeacher: () -> Unit,
    onOpenGrammar: () -> Unit,
    onOpenSkills: () -> Unit,
    onOpenWordsAndGames: () -> Unit,
    onOpenDailyRoutine: () -> Unit,
    onOpenProgress: () -> Unit,
    onToggleDailyTask: (String, Boolean) -> Unit,
    onUpdateNickname: (String) -> Unit,
    onRefreshCompliment: (Boolean) -> Unit
) {
    var showNicknameDialog by remember { mutableStateOf(false) }
    val level = runCatching {
        StudentLevel.valueOf(profile?.currentLevel ?: StudentLevel.BEGINNER.name)
    }.getOrDefault(StudentLevel.BEGINNER)

    val completedTaskKeys = remember(todayTasks) {
        todayTasks.filter { it.isCompleted }.map { it.taskKey }.toSet()
    }
    val dailySpecs = GrammarCurriculum.dailyStudyPlanSpecs
    val completedDailyCount = dailySpecs.count { it.key in completedTaskKeys }
    val recommendedIds = remember(profile) {
        profile?.recommendedTopicIdsCsv?.split(",")?.filter { it.isNotBlank() } ?: listOf("parts_of_speech", "tenses")
    }
    val nextTopic = remember(recommendedIds, topicProgress) {
        val completedSet = topicProgress.filter { it.isCompleted }.map { it.topicId }.toSet()
        recommendedIds.mapNotNull { GrammarCurriculum.topicById(it) }.firstOrNull { it.id !in completedSet }
            ?: GrammarCurriculum.allTopics.firstOrNull { it.id !in completedSet }
            ?: GrammarCurriculum.allTopics.first()
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
            // 1. Hero Welcome Banner
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_classroom_hero),
                            contentDescription = stringResource(id = R.string.classroom_banner_desc),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.94f),
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.72f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Surface(
                                color = Color.White.copy(alpha = 0.22f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "100% FREE • NO LOGIN • OFFLINE READY",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Learn English,\nSpeak Confidently!",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = when (bilingualMode) {
                                    BilingualMode.HINDI_ASSIST -> "संस्थापक एवं निर्माता: CHENI"
                                    else -> "Founder & Creator: CHENI"
                                },
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFD1FAE5)
                            )
                            Spacer(Modifier.height(10.dp))
                            Button(
                                onClick = onOpenTeacher,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("hero_meet_teacher_button")
                            ) {
                                Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = when (bilingualMode) {
                                        BilingualMode.HINDI_ASSIST -> "CHENI से बात करें (AI दोस्त)"
                                        else -> "Talk with CHENI (AI Friend)"
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 1b. Student Compliment from CHENI Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = when (bilingualMode) {
                                        BilingualMode.HINDI_ASSIST -> "CHENI की ओर से प्रशंसा (आपकी AI दोस्त)"
                                        else -> "Compliment from CHENI (Your AI Friend)"
                                    },
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                            Row {
                                IconButton(
                                    onClick = { onRefreshCompliment(true) },
                                    modifier = Modifier.minimumInteractiveComponentSize().testTag("listen_compliment_button")
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Listen to compliment", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(
                                    onClick = { onRefreshCompliment(false) },
                                    modifier = Modifier.minimumInteractiveComponentSize().testTag("refresh_compliment_button")
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = "New compliment", tint = MaterialTheme.colorScheme.tertiary)
                                }
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        BilingualText(
                            english = dailyCompliment.first,
                            hindi = dailyCompliment.second,
                            mode = bilingualMode
                        )
                    }
                }
            }

            // 2. Anonymous Student Profile & Stats Row
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Anonymous Profile",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                val displayName = profile?.nickname?.takeIf { it.isNotBlank() }
                                    ?: "Student ${profile?.anonymousStudentId ?: "TT-0001"}"
                                Text(
                                    text = displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Anonymous ID: ${profile?.anonymousStudentId ?: "TT-0001"} • ${level.labelEn}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(
                                onClick = { showNicknameDialog = true },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("edit_nickname_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Set optional nickname",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            StatPill(
                                icon = Icons.Default.Star,
                                value = "${profile?.stars ?: 5}",
                                label = "Stars",
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.weight(1f)
                            )
                            StatPill(
                                icon = Icons.Default.LocalFireDepartment,
                                value = "${profile?.streakDays ?: 1}d",
                                label = "Streak",
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                            StatPill(
                                icon = Icons.Default.WorkspacePremium,
                                value = "${profile?.xp ?: 20}",
                                label = "XP",
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 3. Knowledge Assessment Banner (Primary step for new students or retake anytime)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (profile?.assessmentCompleted == true)
                            MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (profile?.assessmentCompleted == true)
                                    "Your Level: ${level.labelEn}"
                                else "Step 1: Quick Knowledge Assessment",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            BilingualText(
                                english = if (profile?.assessmentCompleted == true)
                                    "Retake anytime to update your individual study plan."
                                else "Check Grammar, Vocabulary, Reading & Listening—no personal info needed!",
                                hindi = "अपना स्तर (Beginner से Advanced) जानें और व्यक्तिगत योजना पाएं।",
                                mode = bilingualMode
                            )
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = onStartAssessment,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("start_assessment_button")
                            ) {
                                Text(if (profile?.assessmentCompleted == true) "Retake Assessment" else "Start Assessment Now")
                                Spacer(Modifier.width(4.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // 4. Quick Learning Shortcuts
            item {
                Text(
                    text = "Explore Free Learning Modules",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                val quickItems = listOf(
                    QuickModule(
                        title = when (bilingualMode) {
                            BilingualMode.HINDI_ASSIST -> "CHENI (AI दोस्त)"
                            else -> "CHENI (AI Friend)"
                        },
                        subtitle = when (bilingualMode) {
                            BilingualMode.ENGLISH_ONLY -> "AI Teacher & Friend"
                            else -> "AI शिक्षक व दोस्त"
                        },
                        icon = Icons.Default.RecordVoiceOver,
                        onClick = onOpenTeacher,
                        tag = "quick_teacher"
                    ),
                    QuickModule(
                        title = if (bilingualMode == BilingualMode.HINDI_ASSIST) "दैनिक दिनचर्या" else "Daily Routine",
                        subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Morning to Night" else "सुबह से रात तक",
                        icon = Icons.Default.Schedule,
                        onClick = onOpenDailyRoutine,
                        tag = "quick_routine"
                    ),
                    QuickModule("10 Daily Quiz", "8-कौशल अभ्यास", Icons.Default.AutoAwesome, onOpenWordsAndGames, "quick_daily_ten"),
                    QuickModule("18 Grammar", "संपूर्ण व्याकरण", Icons.Default.School, onOpenGrammar, "quick_grammar"),
                    QuickModule("4 Skills + Check", "बोलना व लिखना", Icons.Default.Mic, onOpenSkills, "quick_skills"),
                    QuickModule("Words & Idioms", "Gen Z, Vocab, मुहावरे", Icons.Default.AutoAwesome, onOpenWordsAndGames, "quick_words"),
                    QuickModule("9 Learning Games", "खेल से सीखें", Icons.Default.Extension, onOpenWordsAndGames, "quick_games"),
                    QuickModule("News & Reading", "समाचार व कहानियाँ", Icons.Default.Newspaper, onOpenWordsAndGames, "quick_news")
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(quickItems) { item ->
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            modifier = Modifier
                                .width(136.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable(onClick = item.onClick)
                                .testTag(item.tag)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            // 5. Recommended Next Lesson Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "RECOMMENDED NEXT LESSON",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            if (nextTopic.id in recommendedIds) {
                                Surface(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = "Weak Area Focus",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "${nextTopic.order}. ${nextTopic.titleEn} (${nextTopic.titleHi})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        BilingualText(
                            english = nextTopic.summaryEn,
                            hindi = nextTopic.explanationHi.take(120) + "...",
                            mode = bilingualMode
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onOpenTeacher,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Learn with Teacher")
                            }
                            OutlinedButton(
                                onClick = onOpenGrammar,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Open Course")
                            }
                        }
                    }
                }
            }

            // 6. Daily Study Plan Checklist
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = "Today's Daily Study Plan",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$completedDailyCount of ${dailySpecs.size} targets finished today",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            TextButton(onClick = onOpenDailyRoutine) {
                                Text("Daily Routine")
                            }
                            TextButton(onClick = onOpenProgress) {
                                Text("Analytics")
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { completedDailyCount.toFloat() / dailySpecs.size.coerceAtLeast(1) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                        )
                        Spacer(Modifier.height(10.dp))
                        dailySpecs.forEach { spec ->
                            val checked = spec.key in completedTaskKeys
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { onToggleDailyTask(spec.key, !checked) }
                                    .padding(horizontal = 6.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (checked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = if (checked) "Completed" else "Pending",
                                    tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.width(10.dp))
                                BilingualText(
                                    english = spec.titleEn,
                                    hindi = spec.titleHi,
                                    mode = bilingualMode,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showNicknameDialog) {
        var inputName by remember { mutableStateOf(profile?.nickname.orEmpty()) }
        AlertDialog(
            onDismissRequest = { showNicknameDialog = false },
            title = { Text("Optional Display Nickname") },
            text = {
                Column {
                    Text(
                        "No real name required! Set an optional nickname for your teacher greetings and certificates.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        label = { Text("Nickname (optional)") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("nickname_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateNickname(inputName)
                        showNicknameDialog = false
                    },
                    modifier = Modifier.testTag("save_nickname_button")
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNicknameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private data class QuickModule(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val tag: String
)

@Composable
private fun StatPill(
    icon: ImageVector,
    value: String,
    label: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = label, tint = contentColor, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Column {
                Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = contentColor)
                Text(label, style = MaterialTheme.typography.labelSmall, color = contentColor)
            }
        }
    }
}
