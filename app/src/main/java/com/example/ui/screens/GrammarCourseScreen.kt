package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.GrammarCurriculum
import com.example.data.local.TopicProgressEntity
import com.example.model.BilingualMode
import com.example.model.GrammarMistakeFeedback
import com.example.model.GrammarTopic
import com.example.model.SkillType
import com.example.model.StudentLevel
import com.example.speech.SpeechHelper
import com.example.ui.components.BilingualText
import com.example.ui.components.GrammarFeedbackCard
import com.example.ui.components.SampleAiNoticeBanner

@Composable
fun GrammarCourseScreen(
    topicProgress: List<TopicProgressEntity>,
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    grammarFeedback: GrammarMistakeFeedback?,
    onCheckExercise: (String) -> Unit,
    onClearExerciseFeedback: () -> Unit,
    onRecordQuizResult: (GrammarTopic, Int, Int, Boolean, Boolean) -> Unit,
    onPracticeWithTeacher: (GrammarTopic) -> Unit
) {
    var selectedLevel by remember { mutableStateOf<StudentLevel?>(null) }
    var activeTopic by remember { mutableStateOf<GrammarTopic?>(null) }

    if (activeTopic != null) {
        BackHandler {
            onClearExerciseFeedback()
            activeTopic = null
        }
        GrammarTopicDetailView(
            topic = activeTopic!!,
            progress = topicProgress.find { it.topicId == activeTopic!!.id },
            bilingualMode = bilingualMode,
            speechRate = speechRate,
            speechHelper = speechHelper,
            grammarFeedback = grammarFeedback,
            onCheckExercise = onCheckExercise,
            onSubmitQuiz = { score, total, hwDone, revised ->
                onRecordQuizResult(activeTopic!!, score, total, hwDone, revised)
            },
            onTeachMeClick = { onPracticeWithTeacher(activeTopic!!) },
            onBack = {
                onClearExerciseFeedback()
                activeTopic = null
            }
        )
        return
    }

    val progressMap = remember(topicProgress) { topicProgress.associateBy { it.topicId } }
    val topics = remember(selectedLevel) {
        if (selectedLevel == null) GrammarCurriculum.allTopics
        else GrammarCurriculum.allTopics.filter { it.level == selectedLevel }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Complete English Grammar (18 Topics)",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hindi Explanations • Examples • MCQs • Homework • Revision",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(50)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CloudOff, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(4.dp))
                            Text("Offline Ready", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedLevel == null,
                            onClick = { selectedLevel = null },
                            label = { Text("All (${GrammarCurriculum.allTopics.size})") }
                        )
                    }
                    items(StudentLevel.entries) { lvl ->
                        FilterChip(
                            selected = selectedLevel == lvl,
                            onClick = { selectedLevel = lvl },
                            label = { Text(lvl.labelEn) }
                        )
                    }
                }
            }

            items(topics, key = { it.id }) { topic ->
                val prog = progressMap[topic.id]
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(
                        1.dp,
                        if (prog?.isCompleted == true) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { activeTopic = topic }
                        .testTag("grammar_topic_${topic.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (prog?.isCompleted == true) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${topic.order}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (prog?.isCompleted == true) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = topic.titleEn,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = topic.titleHi,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${topic.level.labelEn} • Best Quiz: ${prog?.bestQuizScore ?: 0}/${topic.mcqQuestions.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (prog?.isCompleted == true) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Completed",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GrammarTopicDetailView(
    topic: GrammarTopic,
    progress: TopicProgressEntity?,
    bilingualMode: BilingualMode,
    speechRate: Float,
    speechHelper: SpeechHelper,
    grammarFeedback: GrammarMistakeFeedback?,
    onCheckExercise: (String) -> Unit,
    onSubmitQuiz: (Int, Int, Boolean, Boolean) -> Unit,
    onTeachMeClick: () -> Unit,
    onBack: () -> Unit
) {
    var exerciseInput by remember { mutableStateOf("") }
    val quizSelections = remember { mutableStateMapOf<String, Int>() }
    var quizSubmitted by remember { mutableStateOf(false) }
    var homeworkChecked by remember { mutableStateOf(progress?.homeworkDone == true) }
    var revisedChecked by remember { mutableStateOf((progress?.revisionCount ?: 0) > 0) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack, modifier = Modifier.minimumInteractiveComponentSize()) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to grammar list")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${topic.order}. ${topic.titleEn}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(topic.titleHi, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                    OutlinedButton(onClick = onTeachMeClick) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("AI Class")
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Concept & Simple Hindi Explanation", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        BilingualText(english = topic.summaryEn, hindi = topic.explanationHi, mode = bilingualMode)
                        Spacer(Modifier.height(8.dp))
                        topic.keyRules.forEach { rule ->
                            Text("• $rule", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
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
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("English & Daily-Life Examples", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        (topic.examples + topic.dailyLifeExamples).forEach { ex ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("${ex.english} [${ex.contextTag}]", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                                    Text(ex.hindi, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(
                                    onClick = { speechHelper.speakEnglish(ex.english, speechRate) },
                                    modifier = Modifier.minimumInteractiveComponentSize()
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Listen example", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }

            // Practice Exercise with Auto Grammar Checker
            topic.exercises.firstOrNull()?.let { ex ->
                item {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Practice Exercise", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            BilingualText(english = ex.prompt, hindi = ex.hintHi, mode = bilingualMode)
                            Spacer(Modifier.height(8.dp))
                            SampleAiNoticeBanner()
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = exerciseInput,
                                onValueChange = { exerciseInput = it },
                                label = { Text("Write your practice sentence") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("topic_exercise_input")
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { onCheckExercise(exerciseInput) },
                                    modifier = Modifier.testTag("check_exercise_button")
                                ) {
                                    Text("Check My Sentence")
                                }
                                OutlinedButton(onClick = { exerciseInput = ex.sampleAnswer }) {
                                    Text("See Sample")
                                }
                            }
                            grammarFeedback?.let { fb ->
                                Spacer(Modifier.height(10.dp))
                                GrammarFeedbackCard(feedback = fb, bilingualMode = bilingualMode)
                            }
                        }
                    }
                }
            }

            // MCQ Test
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Quiz, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(6.dp))
                            Text("MCQ Test (${topic.mcqQuestions.size} Questions)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(8.dp))
                        topic.mcqQuestions.forEachIndexed { qIdx, q ->
                            Text("${qIdx + 1}. ${q.question}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            q.options.forEachIndexed { oIdx, opt ->
                                val picked = quizSelections[q.id] == oIdx
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .minimumInteractiveComponentSize()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable(enabled = !quizSubmitted) { quizSelections[q.id] = oIdx }
                                        .padding(vertical = 4.dp, horizontal = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (picked) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(opt, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            if (quizSubmitted) {
                                val isRight = quizSelections[q.id] == q.correctIndex
                                Text(
                                    text = if (isRight) "Correct! ${q.explanationEn}" else "Answer: ${q.options[q.correctIndex]} — ${q.explanationEn} (${q.explanationHi})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isRight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                quizSubmitted = true
                                val score = topic.mcqQuestions.count { quizSelections[it.id] == it.correctIndex }
                                onSubmitQuiz(score, topic.mcqQuestions.size, homeworkChecked, revisedChecked)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("submit_topic_quiz_button")
                        ) {
                            Text(if (quizSubmitted) "Score Recorded! Save Homework/Revision" else "Submit Quiz & Save Progress")
                        }
                    }
                }
            }

            // Homework & Revision
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Homework & Revision", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        BilingualText(english = topic.homeworkEn, hindi = topic.homeworkHi, mode = bilingualMode)
                        Spacer(Modifier.height(8.dp))
                        topic.revisionPoints.forEach { rev ->
                            Text("✓ $rev", style = MaterialTheme.typography.bodySmall)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = homeworkChecked,
                                onClick = { homeworkChecked = !homeworkChecked },
                                label = { Text("Homework Done") }
                            )
                            FilterChip(
                                selected = revisedChecked,
                                onClick = { revisedChecked = !revisedChecked },
                                label = { Text("Revised") }
                            )
                        }
                    }
                }
            }
        }
    }
}
