package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.model.DailyStudyTaskSpec
import com.example.model.StudentLevel
import com.example.ui.components.BilingualText
import com.example.ui.components.FolderAccent
import com.example.ui.components.FolderPresets
import com.example.ui.components.StudyFolderCard
import com.example.ui.theme.FolderAmber
import com.example.ui.theme.FolderAmberLight
import com.example.ui.theme.FolderCoral
import com.example.ui.theme.FolderCoralLight
import com.example.ui.theme.FolderIndigo
import com.example.ui.theme.FolderIndigoLight
import com.example.ui.theme.FolderSky
import com.example.ui.theme.FolderSkyLight
import com.example.ui.theme.PureWhite

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
    onOpenWordsTab: (Int?) -> Unit,
    onOpenDailyRoutine: () -> Unit,
    onOpenProgress: () -> Unit,
    onToggleDailyTask: (String, Boolean) -> Unit,
    onUpdateNickname: (String) -> Unit,
    onRefreshCompliment: (Boolean) -> Unit
) {
    var showNicknameDialog by remember { mutableStateOf(false) }
    var showDailyStudyPlanFolder by rememberSaveable { mutableStateOf(false) }

    BackHandler(enabled = showDailyStudyPlanFolder) {
        showDailyStudyPlanFolder = false
    }

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

    if (showDailyStudyPlanFolder) {
        DailyStudyPlanFolderView(
            dailySpecs = dailySpecs,
            completedTaskKeys = completedTaskKeys,
            completedDailyCount = completedDailyCount,
            bilingualMode = bilingualMode,
            onToggleDailyTask = onToggleDailyTask,
            onOpenTarget = { route ->
                showDailyStudyPlanFolder = false
                when (route) {
                    "learn" -> onOpenGrammar()
                    "routine" -> onOpenDailyRoutine()
                    "teacher" -> onOpenTeacher()
                    "skills" -> onOpenSkills()
                    "words" -> onOpenWordsTab(null)
                    "progress" -> onOpenProgress()
                }
            },
            onOpenDailyRoutine = {
                showDailyStudyPlanFolder = false
                onOpenDailyRoutine()
            },
            onOpenProgress = {
                showDailyStudyPlanFolder = false
                onOpenProgress()
            },
            onBack = { showDailyStudyPlanFolder = false }
        )
        return
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val isWide = maxWidth >= 600.dp
        val completedTopicsCount = remember(topicProgress) { topicProgress.count { it.isCompleted } }
        val routineDoneCount = remember(todayTasks) {
            todayTasks.count { it.isCompleted && it.taskKey.startsWith("routine_") }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Hero Welcome Banner
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 210.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_classroom_hero),
                            contentDescription = stringResource(id = R.string.classroom_banner_desc),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
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
                                    BilingualMode.HINDI_ASSIST -> "व्यक्तिगत AI अंग्रेज़ी कक्षा • Beginner से Advanced"
                                    else -> "Personal AI English Academy • Beginner to Advanced"
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
                                        BilingualMode.HINDI_ASSIST -> "AI शिक्षक से बात करें"
                                        else -> "Talk with AI Teacher"
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 1b. Student Compliment from CHENI Card (Warm Sunshine Amber Gradient)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.5.dp, FolderAmber.copy(alpha = 0.45f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(FolderAmberLight, Color(0xFFFFFBEB), PureWhite)
                                )
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(FolderAmber),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = PureWhite,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = when (bilingualMode) {
                                            BilingualMode.HINDI_ASSIST -> "आज की प्रशंसा और प्रेरणा"
                                            else -> "Daily Compliment & Motivation"
                                        },
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF78350F)
                                    )
                                }
                                Row {
                                    IconButton(
                                        onClick = { onRefreshCompliment(true) },
                                        modifier = Modifier.minimumInteractiveComponentSize().testTag("listen_compliment_button")
                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen to compliment", tint = FolderAmber)
                                    }
                                    IconButton(
                                        onClick = { onRefreshCompliment(false) },
                                        modifier = Modifier.minimumInteractiveComponentSize().testTag("refresh_compliment_button")
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = "New compliment", tint = FolderCoral)
                                    }
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            BilingualText(
                                english = dailyCompliment.first,
                                hindi = dailyCompliment.second,
                                mode = bilingualMode,
                                englishColor = Color(0xFF451A03),
                                hindiColor = Color(0xFF92400E)
                            )
                        }
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
                                containerColor = FolderAmberLight,
                                contentColor = FolderAmber,
                                modifier = Modifier.weight(1f)
                            )
                            StatPill(
                                icon = Icons.Default.LocalFireDepartment,
                                value = "${profile?.streakDays ?: 1}d",
                                label = "Streak",
                                containerColor = FolderCoralLight,
                                contentColor = FolderCoral,
                                modifier = Modifier.weight(1f)
                            )
                            StatPill(
                                icon = Icons.Default.WorkspacePremium,
                                value = "${profile?.xp ?: 20}",
                                label = "XP",
                                containerColor = FolderIndigoLight,
                                contentColor = FolderIndigo,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 3. Knowledge Assessment Banner (Sky-Emerald Gradient)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.5.dp, FolderSky.copy(alpha = 0.45f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(FolderSkyLight, Color(0xFFECFDF5))
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(FolderSky),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (profile?.assessmentCompleted == true)
                                        "Your Level: ${level.labelEn}"
                                    else "Step 1: Quick Knowledge Assessment",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = FolderSky
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
                                    colors = ButtonDefaults.buttonColors(containerColor = FolderSky),
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
            }

            // 4. Creative Colourful Study Folders (1-Column Big Cards on Phones, 2-Column on Tablets)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "आपके रंगीन स्टडी फ़ोल्डर (Study Folders)" else "Creative Study Folders",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                    "सीखने के लिए किसी भी बड़े फ़ोल्डर पर टैप करें"
                                else "Tap any colourful folder below to start learning",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "9 Folders",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                    }

                    val quickFolders = listOf(
                        QuickFolderSpec(
                            tabLabel = "AI TEACHER • LIVE",
                            title = if (bilingualMode == BilingualMode.HINDI_ASSIST) "AI शिक्षक कक्षा (Live Teacher)" else "AI Teacher Classroom",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Face-to-face speaking, grammar fixes & Hindi support" else "AI शिक्षक के साथ बोलें, गलती सुधारें और अभ्यास करें",
                            badge = "Live AI",
                            icon = Icons.Default.RecordVoiceOver,
                            accent = FolderPresets.Emerald,
                            onClick = onOpenTeacher,
                            tag = "quick_teacher"
                        ),
                        QuickFolderSpec(
                            tabLabel = "TODAY'S PLAN",
                            title = if (bilingualMode == BilingualMode.HINDI_ASSIST) "आज का अध्ययन लक्ष्य (Daily Plan)" else "Today's Daily Study Plan",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "${dailySpecs.size} daily targets checklist & direct shortcuts" else "आज के ${dailySpecs.size} लक्ष्य देखें, टिक करें और अभ्यास खोलें",
                            badge = "$completedDailyCount/${dailySpecs.size} Done",
                            icon = Icons.Default.Checklist,
                            accent = FolderPresets.Teal,
                            progress = completedDailyCount.toFloat() / dailySpecs.size.coerceAtLeast(1),
                            previewLine = dailySpecs.firstOrNull { it.key !in completedTaskKeys }?.let { "Next: ${it.titleEn}" }
                                ?: "All ${dailySpecs.size} targets completed today!",
                            onClick = { showDailyStudyPlanFolder = true },
                            tag = "quick_daily_study_plan"
                        ),
                        QuickFolderSpec(
                            tabLabel = "DAILY 10 QUIZ",
                            title = "10 Daily Fresh Practice",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "8 skills covered daily • Never repeats" else "Grammar, Vocab, Translation, Reading & Speaking",
                            badge = "10 Fresh Qs",
                            icon = Icons.Default.AutoAwesome,
                            accent = FolderPresets.Sky,
                            onClick = { onOpenWordsTab(0) },
                            tag = "quick_daily_ten"
                        ),
                        QuickFolderSpec(
                            tabLabel = "GRAMMAR COURSE",
                            title = "18 Complete Grammar Topics",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Beginner to Advanced • Rules, Quiz & Homework" else "सरल हिंदी नियम, उदाहरण, क्विज़ और गृहकार्य",
                            badge = "$completedTopicsCount/18 Done",
                            icon = Icons.Default.School,
                            accent = FolderPresets.Indigo,
                            progress = completedTopicsCount / 18f,
                            onClick = onOpenGrammar,
                            tag = "quick_grammar"
                        ),
                        QuickFolderSpec(
                            tabLabel = "DAILY ROUTINE",
                            title = if (bilingualMode == BilingualMode.HINDI_ASSIST) "दैनिक दिनचर्या (4 समय फ़ोल्डर)" else "Daily Routine (4 Folders)",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Morning, Afternoon, Evening & Night sentences" else "सुबह, दोपहर, शाम और रात के बोलने वाले वाक्य",
                            badge = "$routineDoneCount/16 Done",
                            icon = Icons.Default.Schedule,
                            accent = FolderPresets.Amber,
                            progress = routineDoneCount / 16f,
                            onClick = onOpenDailyRoutine,
                            tag = "quick_routine"
                        ),
                        QuickFolderSpec(
                            tabLabel = "4 SKILLS STUDIO",
                            title = "Speak, Listen, Read, Write & Fix",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Real-life speaking, audio lessons & writing check" else "बोलना, सुनना, पढ़ना, लिखना और वाक्य सुधार",
                            badge = "5 Studios",
                            icon = Icons.Default.Mic,
                            accent = FolderPresets.Emerald,
                            onClick = onOpenSkills,
                            tag = "quick_skills"
                        ),
                        QuickFolderSpec(
                            tabLabel = "WORDS & IDIOMS",
                            title = "Gen Z, Vocab & Daily Idioms",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "10 Gen Z + 10 Vocabulary + 10 Phrases & Idioms" else "रोज़ नए Gen Z शब्द, शब्दावली और मुहावरे",
                            badge = "30 Daily",
                            icon = Icons.Default.AutoAwesome,
                            accent = FolderPresets.Violet,
                            onClick = { onOpenWordsTab(1) },
                            tag = "quick_words"
                        ),
                        QuickFolderSpec(
                            tabLabel = "LEARNING GAMES",
                            title = "9 Offline Learning Games",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Vocab Battle, Grammar Arena, Word Match & More" else "बिना इंटरनेट मज़ेदार खेलों से अंग्रेज़ी पक्की करें",
                            badge = "9 Games",
                            icon = Icons.Default.Extension,
                            accent = FolderPresets.Coral,
                            onClick = { onOpenWordsTab(6) },
                            tag = "quick_games"
                        ),
                        QuickFolderSpec(
                            tabLabel = "DAILY NEWS",
                            title = "Daily Bilingual English News",
                            subtitle = if (bilingualMode == BilingualMode.ENGLISH_ONLY) "Simple stories + Hindi meaning + 5 Questions" else "सरल अंग्रेज़ी समाचार, हिंदी अर्थ व 5 अभ्यास प्रश्न",
                            badge = "5 Qs Each",
                            icon = Icons.Default.Newspaper,
                            accent = FolderPresets.Rose,
                            onClick = { onOpenWordsTab(7) },
                            tag = "quick_news"
                        )
                    )

                    val rows = if (isWide) quickFolders.chunked(2) else quickFolders.map { listOf(it) }
                    rows.forEach { rowFolders ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowFolders.forEach { folder ->
                                StudyFolderCard(
                                    tabLabel = folder.tabLabel,
                                    title = folder.title,
                                    subtitle = folder.subtitle,
                                    badgeText = folder.badge,
                                    icon = folder.icon,
                                    accent = folder.accent,
                                    previewLine = folder.previewLine,
                                    progress = folder.progress,
                                    onClick = folder.onClick,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag(folder.tag)
                                )
                            }
                            if (isWide && rowFolders.size == 1) {
                                Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // 5. Recommended Next Lesson Card (Teal-Emerald Gradient Header)
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.5.dp, FolderPresets.Teal.primary.copy(alpha = 0.45f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .background(FolderPresets.Teal.primary)
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "RECOMMENDED NEXT LESSON",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = FolderPresets.Teal.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                if (nextTopic.id in recommendedIds) {
                                    Surface(
                                        color = FolderPresets.Amber.softTint,
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text(
                                            text = "Weak Area Focus",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = FolderPresets.Amber.primary,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "${nextTopic.order}. ${nextTopic.titleEn} (${nextTopic.titleHi})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(6.dp))
                            BilingualText(
                                english = nextTopic.summaryEn,
                                hindi = nextTopic.explanationHi.take(120) + "...",
                                mode = bilingualMode
                            )
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(
                                    onClick = onOpenTeacher,
                                    colors = ButtonDefaults.buttonColors(containerColor = FolderPresets.Teal.primary),
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Text("Open AI Classroom")
                                }
                                OutlinedButton(
                                    onClick = onOpenGrammar,
                                    border = BorderStroke(1.2.dp, FolderPresets.Teal.primary),
                                    modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = FolderPresets.Teal.primary, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("Open Course", color = FolderPresets.Teal.primary)
                                }
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

private data class QuickFolderSpec(
    val tabLabel: String,
    val title: String,
    val subtitle: String,
    val badge: String,
    val icon: ImageVector,
    val accent: FolderAccent,
    val onClick: () -> Unit,
    val tag: String,
    val progress: Float? = null,
    val previewLine: String? = null
)

@Composable
private fun DailyStudyPlanFolderView(
    dailySpecs: List<DailyStudyTaskSpec>,
    completedTaskKeys: Set<String>,
    completedDailyCount: Int,
    bilingualMode: BilingualMode,
    onToggleDailyTask: (String, Boolean) -> Unit,
    onOpenTarget: (String) -> Unit,
    onOpenDailyRoutine: () -> Unit,
    onOpenProgress: () -> Unit,
    onBack: () -> Unit
) {
    val headerAccent = FolderPresets.Teal
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = headerAccent.softTint),
                    border = BorderStroke(1.5.dp, headerAccent.primary.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = onBack,
                                border = BorderStroke(1.2.dp, headerAccent.primary),
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("daily_plan_back_button")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Home",
                                    tint = headerAccent.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text("All Folders", color = headerAccent.primary, fontWeight = FontWeight.Bold)
                            }
                            Surface(
                                color = headerAccent.primary,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "$completedDailyCount / ${dailySpecs.size} Done",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Text(
                            text = if (bilingualMode == BilingualMode.HINDI_ASSIST) "आज की दैनिक अध्ययन योजना (Daily Study Plan)" else "Today's Daily Study Plan",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = headerAccent.primary
                        )
                        Text(
                            text = if (bilingualMode == BilingualMode.HINDI_ASSIST)
                                "हर लक्ष्य पर टैप करके पूरा करें या 'Open' दबाकर सीधे अभ्यास खोलें।"
                            else "Tap any target to mark it done, or tap Open to jump right in.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        LinearProgressIndicator(
                            progress = { completedDailyCount.toFloat() / dailySpecs.size.coerceAtLeast(1) },
                            color = headerAccent.primary,
                            trackColor = PureWhite.copy(alpha = 0.85f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(
                                onClick = onOpenDailyRoutine,
                                border = BorderStroke(1.dp, FolderPresets.Amber.primary),
                                modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                            ) {
                                Text("Daily Routine", color = FolderPresets.Amber.primary, fontWeight = FontWeight.SemiBold)
                            }
                            OutlinedButton(
                                onClick = onOpenProgress,
                                border = BorderStroke(1.dp, FolderPresets.Indigo.primary),
                                modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                            ) {
                                Text("Analytics", color = FolderPresets.Indigo.primary, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            items(dailySpecs, key = { it.key }) { spec ->
                val checked = spec.key in completedTaskKeys
                val taskAccent = when (spec.routeHint) {
                    "teacher" -> FolderPresets.Emerald
                    "learn" -> FolderPresets.Indigo
                    "routine" -> FolderPresets.Amber
                    "skills" -> FolderPresets.Teal
                    "words" -> FolderPresets.Violet
                    else -> FolderPresets.Sky
                }
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (checked) taskAccent.softTint.copy(alpha = 0.7f) else PureWhite
                    ),
                    border = BorderStroke(
                        1.5.dp,
                        if (checked) taskAccent.primary else taskAccent.primary.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onToggleDailyTask(spec.key, !checked) }
                        .testTag("daily_plan_item_${spec.key}")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(taskAccent.primary)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (checked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = if (checked) "Completed" else "Pending",
                                tint = if (checked) taskAccent.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            BilingualText(
                                english = spec.titleEn,
                                hindi = spec.titleHi,
                                mode = bilingualMode,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            Surface(
                                color = taskAccent.primary.copy(alpha = 0.14f),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .clip(RoundedCornerShape(50))
                                    .clickable { onOpenTarget(spec.routeHint) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Open",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = taskAccent.primary
                                    )
                                    Spacer(Modifier.width(3.dp))
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Open ${spec.titleEn}",
                                        tint = taskAccent.primary,
                                        modifier = Modifier.size(15.dp)
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
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.2.dp, contentColor.copy(alpha = 0.35f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = label, tint = contentColor, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(8.dp))
            Column {
                Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = contentColor)
                Text(label, style = MaterialTheme.typography.labelMedium, color = contentColor)
            }
        }
    }
}
