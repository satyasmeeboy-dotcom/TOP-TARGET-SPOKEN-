package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.model.BilingualMode
import com.example.ui.AppViewModel
import com.example.ui.components.ComplimentCelebrationDialog
import com.example.ui.components.TopBrandHeader
import com.example.ui.screens.AssessmentScreen
import com.example.ui.screens.DailyRoutineScreen
import com.example.ui.screens.DailyWordsAndGamesScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GrammarCourseScreen
import com.example.ui.screens.ProgressAndPrivacyScreen
import com.example.ui.screens.SkillsPracticeScreen
import com.example.ui.screens.TeacherClassroomScreen
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.TopTargetTheme
import kotlinx.coroutines.delay

object AppRoutes {
    const val HOME = "route_home"
    const val TEACHER = "route_teacher"
    const val LEARN = "route_learn"
    const val WORDS = "route_words"
    const val PROGRESS = "route_progress"
    const val ASSESSMENT = "route_assessment"
    const val DAILY_ROUTINE = "route_daily_routine"
}

private data class NavTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopTargetTheme {
                TopTargetApp()
            }
        }
    }
}

@Composable
fun TopTargetApp(viewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppRoutes.HOME

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val topicProgress by viewModel.topicProgress.collectAsStateWithLifecycle()
    val mistakes by viewModel.mistakes.collectAsStateWithLifecycle()
    val learnedWords by viewModel.learnedWords.collectAsStateWithLifecycle()
    val savedNews by viewModel.savedNews.collectAsStateWithLifecycle()
    val badges by viewModel.badges.collectAsStateWithLifecycle()
    val todayTasks by viewModel.todayTasks.collectAsStateWithLifecycle()
    val bilingualMode by viewModel.bilingualMode.collectAsStateWithLifecycle()
    val speechRate by viewModel.speechRate.collectAsStateWithLifecycle()
    val autoSpeak by viewModel.autoSpeakTeacher.collectAsStateWithLifecycle()
    val optionalSync by viewModel.optionalSyncEnabled.collectAsStateWithLifecycle()
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()

    val activeTopic by viewModel.activeTopic.collectAsStateWithLifecycle()
    val teacherStage by viewModel.teacherStage.collectAsStateWithLifecycle()
    val whiteboard by viewModel.whiteboard.collectAsStateWithLifecycle()
    val teacherMessages by viewModel.teacherMessages.collectAsStateWithLifecycle()
    val isThinking by viewModel.isTeacherThinking.collectAsStateWithLifecycle()
    val grammarResult by viewModel.grammarCheckResult.collectAsStateWithLifecycle()
    val routineFeedback by viewModel.routineCheckResult.collectAsStateWithLifecycle()
    val dailyPracticeFeedback by viewModel.dailyPracticeFeedback.collectAsStateWithLifecycle()
    val weakTags by viewModel.weakTopicTags.collectAsStateWithLifecycle()
    val speakingEval by viewModel.speakingEvaluation.collectAsStateWithLifecycle()
    val celebration by viewModel.celebration.collectAsStateWithLifecycle()

    val dailyCompliment by viewModel.dailyCompliment.collectAsStateWithLifecycle()

    val isSpeaking by viewModel.speechHelper.isSpeaking.collectAsStateWithLifecycle()
    val isListening by viewModel.speechHelper.isListening.collectAsStateWithLifecycle()
    val speechError by viewModel.speechHelper.speechError.collectAsStateWithLifecycle()

    var learnSectionIndex by rememberSaveable { mutableIntStateOf(0) }
    var showSplash by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1200)
        showSplash = false
    }

    val navTabs = remember {
        listOf(
            NavTab(AppRoutes.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
            NavTab(AppRoutes.TEACHER, "CHENI", Icons.Filled.RecordVoiceOver, Icons.Outlined.RecordVoiceOver, "nav_teacher"),
            NavTab(AppRoutes.LEARN, "Courses", Icons.Filled.School, Icons.Outlined.School, "nav_learn"),
            NavTab(AppRoutes.WORDS, "Words & Play", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome, "nav_words"),
            NavTab(AppRoutes.PROGRESS, "Progress", Icons.Filled.BarChart, Icons.Outlined.BarChart, "nav_progress")
        )
    }

    fun navigateTopTab(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val useRail = maxWidth >= 600.dp

            Scaffold(
                contentWindowInsets = WindowInsets.safeDrawing,
                topBar = {
                    TopBrandHeader(
                        isOnline = isOnline,
                        bilingualMode = bilingualMode,
                        onCycleBilingual = {
                            val next = when (bilingualMode) {
                                BilingualMode.BILINGUAL -> BilingualMode.HINDI_ASSIST
                                BilingualMode.HINDI_ASSIST -> BilingualMode.ENGLISH_ONLY
                                BilingualMode.ENGLISH_ONLY -> BilingualMode.BILINGUAL
                            }
                            viewModel.setBilingualMode(next)
                        },
                        modifier = Modifier.statusBarsPadding()
                    )
                },
                bottomBar = {
                    if (!useRail && currentRoute != AppRoutes.ASSESSMENT && currentRoute != AppRoutes.DAILY_ROUTINE) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            navTabs.forEach { tab ->
                                val selected = currentRoute == tab.route
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navigateTopTab(tab.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = tab.label
                                        )
                                    },
                                    label = { Text(tab.label, maxLines = 1) },
                                    modifier = Modifier.testTag(tab.tag)
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (useRail && currentRoute != AppRoutes.ASSESSMENT && currentRoute != AppRoutes.DAILY_ROUTINE) {
                        NavigationRail(containerColor = MaterialTheme.colorScheme.surface) {
                            navTabs.forEach { tab ->
                                val selected = currentRoute == tab.route
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = { navigateTopTab(tab.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = tab.label
                                        )
                                    },
                                    label = { Text(tab.label) },
                                    modifier = Modifier.testTag(tab.tag)
                                )
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.HOME,
                        modifier = Modifier.weight(1f)
                    ) {
                        composable(AppRoutes.HOME) {
                            DashboardScreen(
                                profile = profile,
                                topicProgress = topicProgress,
                                todayTasks = todayTasks,
                                dailyCompliment = dailyCompliment,
                                bilingualMode = bilingualMode,
                                onStartAssessment = { navController.navigate(AppRoutes.ASSESSMENT) },
                                onOpenTeacher = { navigateTopTab(AppRoutes.TEACHER) },
                                onOpenGrammar = {
                                    learnSectionIndex = 0
                                    navigateTopTab(AppRoutes.LEARN)
                                },
                                onOpenSkills = {
                                    learnSectionIndex = 1
                                    navigateTopTab(AppRoutes.LEARN)
                                },
                                onOpenWordsAndGames = { navigateTopTab(AppRoutes.WORDS) },
                                onOpenDailyRoutine = { navController.navigate(AppRoutes.DAILY_ROUTINE) },
                                onOpenProgress = { navigateTopTab(AppRoutes.PROGRESS) },
                                onToggleDailyTask = { key, done -> viewModel.toggleDailyTask(key, done) },
                                onUpdateNickname = { viewModel.updateNickname(it) },
                                onRefreshCompliment = { speak -> viewModel.refreshCompliment(speak) }
                            )
                        }

                        composable(AppRoutes.TEACHER) {
                            TeacherClassroomScreen(
                                activeTopic = activeTopic,
                                teacherStage = teacherStage,
                                whiteboard = whiteboard,
                                messages = teacherMessages,
                                isThinking = isThinking,
                                isSpeaking = isSpeaking,
                                isListening = isListening,
                                speechError = speechError,
                                autoSpeak = autoSpeak,
                                speechRate = speechRate,
                                bilingualMode = bilingualMode,
                                speechHelper = viewModel.speechHelper,
                                onSelectTopic = { viewModel.startTeacherLesson(it) },
                                onSendMessage = { viewModel.sendTeacherStudentMessage(it) },
                                onAdvanceStage = { viewModel.advanceTeacherStagePrompt() },
                                onToggleAutoSpeak = { viewModel.setAutoSpeakTeacher(it) }
                            )
                        }

                        composable(AppRoutes.LEARN) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    SingleChoiceSegmentedButtonRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        SegmentedButton(
                                            selected = learnSectionIndex == 0,
                                            onClick = { learnSectionIndex = 0 },
                                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                                            modifier = Modifier.testTag("learn_tab_grammar")
                                        ) {
                                            Text("18 Grammar Topics")
                                        }
                                        SegmentedButton(
                                            selected = learnSectionIndex == 1,
                                            onClick = { learnSectionIndex = 1 },
                                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                                            modifier = Modifier.testTag("learn_tab_skills")
                                        ) {
                                            Text("4 Skills + Grammar Fix")
                                        }
                                    }
                                }
                                if (learnSectionIndex == 0) {
                                    GrammarCourseScreen(
                                        topicProgress = topicProgress,
                                        bilingualMode = bilingualMode,
                                        speechRate = speechRate,
                                        speechHelper = viewModel.speechHelper,
                                        grammarFeedback = grammarResult,
                                        onCheckExercise = { viewModel.checkGrammarSentence(it) },
                                        onClearExerciseFeedback = { viewModel.clearGrammarCheckResult() },
                                        onRecordQuizResult = { topic, score, total, hw, rev ->
                                            viewModel.recordTopicQuizResult(topic, score, total, hw, rev)
                                        },
                                        onPracticeWithTeacher = { topic ->
                                            viewModel.startTeacherLesson(topic)
                                            navigateTopTab(AppRoutes.TEACHER)
                                        }
                                    )
                                } else {
                                    SkillsPracticeScreen(
                                        bilingualMode = bilingualMode,
                                        speechRate = speechRate,
                                        isSpeaking = isSpeaking,
                                        isListening = isListening,
                                        speechHelper = viewModel.speechHelper,
                                        grammarResult = grammarResult,
                                        speakingEvaluation = speakingEval,
                                        recentMistakes = mistakes,
                                        onCheckGrammar = { text, skill -> viewModel.checkGrammarSentence(text, skill) },
                                        onEvaluateSpeaking = { sc, text -> viewModel.evaluateSpeakingResponse(sc, text) },
                                        onRecordSkillQuiz = { skill, c, t, key -> viewModel.recordSkillQuiz(skill, c, t, key) }
                                    )
                                }
                            }
                        }

                        composable(AppRoutes.WORDS) {
                            DailyWordsAndGamesScreen(
                                epochDay = viewModel.currentEpochDay,
                                learnedWords = learnedWords,
                                savedNews = savedNews,
                                todayTasks = todayTasks,
                                weakTags = weakTags,
                                bilingualMode = bilingualMode,
                                speechRate = speechRate,
                                isListening = isListening,
                                speechHelper = viewModel.speechHelper,
                                routineFeedback = routineFeedback,
                                dailyPracticeFeedback = dailyPracticeFeedback,
                                onMarkWordLearned = { id, word, isGenZ -> viewModel.markWordLearned(id, word, isGenZ) },
                                onMarkPhraseLearned = { id, phrase -> viewModel.markPhraseOrIdiomLearned(id, phrase) },
                                onMarkRoutinePracticed = { id -> viewModel.markRoutineStepPracticed(id) },
                                onCheckRoutineSentence = { sentence -> viewModel.checkRoutineSentence(sentence) },
                                onClearRoutineFeedback = { viewModel.clearRoutineFeedback() },
                                onCheckDailyPracticeAnswer = { text, skill -> viewModel.checkDailyPracticeSentence(text, skill) },
                                onClearDailyPracticeFeedback = { viewModel.clearDailyPracticeFeedback() },
                                onToggleSaveNews = { id, headline, saved -> viewModel.toggleSaveNews(id, headline, saved) },
                                onRecordQuizScore = { skill, c, t, key -> viewModel.recordSkillQuiz(skill, c, t, key) }
                            )
                        }

                        composable(AppRoutes.DAILY_ROUTINE) {
                            DailyRoutineScreen(
                                todayTasks = todayTasks,
                                bilingualMode = bilingualMode,
                                speechRate = speechRate,
                                isListening = isListening,
                                speechHelper = viewModel.speechHelper,
                                routineFeedback = routineFeedback,
                                onMarkRoutinePracticed = { id -> viewModel.markRoutineStepPracticed(id) },
                                onCheckRoutineSentence = { sentence -> viewModel.checkRoutineSentence(sentence) },
                                onClearRoutineFeedback = { viewModel.clearRoutineFeedback() },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(AppRoutes.PROGRESS) {
                            val certs = remember(profile, topicProgress, learnedWords) {
                                viewModel.buildCertificates(profile, topicProgress, learnedWords)
                            }
                            ProgressAndPrivacyScreen(
                                profile = profile,
                                topicProgress = topicProgress,
                                mistakes = mistakes,
                                learnedWords = learnedWords,
                                badges = badges,
                                certificates = certs,
                                bilingualMode = bilingualMode,
                                isOnline = isOnline,
                                speechRate = speechRate,
                                optionalSyncEnabled = optionalSync,
                                onSpeechRateChange = { viewModel.setSpeechRate(it) },
                                onToggleOptionalSync = { viewModel.setOptionalSync(it) },
                                onDeleteAllData = { viewModel.deleteAllLocalData() }
                            )
                        }

                        composable(AppRoutes.ASSESSMENT) {
                            AssessmentScreen(
                                bilingualMode = bilingualMode,
                                speechHelper = viewModel.speechHelper,
                                onSubmitAssessment = { answers, writing, speaking ->
                                    viewModel.submitAssessment(answers, writing, speaking)
                                },
                                onBack = { navController.popBackStack() },
                                onStartRecommendedPlan = {
                                    navController.popBackStack()
                                    navigateTopTab(AppRoutes.TEACHER)
                                }
                            )
                        }
                    }
                }
            }
        }

        ComplimentCelebrationDialog(
            celebration = celebration,
            onDismiss = { viewModel.dismissCelebration() }
        )

        // Branded App Logo Intro Splash
        AnimatedVisibility(
            visible = showSplash,
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(EmeraldPrimary, Color(0xFF064E3B))
                        )
                    )
                    .clickable { showSplash = false },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon_emblem),
                        contentDescription = stringResource(id = R.string.brand_badge_desc),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(116.dp)
                            .clip(RoundedCornerShape(28.dp))
                    )
                    Spacer(Modifier.height(18.dp))
                    Text(
                        text = "TOP TARGET SPOKEN ENGLISH",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Learn English, Speak Confidently!",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFD1FAE5)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Founder & Creator: CHENI",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}
