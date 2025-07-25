package com.usil.myappcomponents.quiz

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usil.myappcomponents.quiz.ui.theme.MyAppComponentsTheme
import com.usil.myappcomponents.viewModel.QuizViewModel

class QuizQuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppComponentsTheme {
                QuizScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun QuizScreen() {
    val context = LocalContext.current
    val viewModel: QuizViewModel = viewModel()
    val questions by viewModel.questions.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    val currentQuestion = questions.getOrNull(currentIndex)

    val totalQuestions = questions.size

    val userAnswers = remember { mutableStateListOf<Pair<String, Boolean>>() }

    val isQuizFinished = currentIndex >= totalQuestions

    LaunchedEffect(Unit) {
        viewModel.getQuestions()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Question", color = Color.Red) },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            currentQuestion?.let { question ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        QuizQuestionView(
                            paddingValues = paddingValues,
                            question = question.question
                        )
                        QuizAnswerView(
                            paddingValues = paddingValues,
                            correctAnswer = question.correctAnswer,
                            incorrectAnswers = question.incorrectAnswers,
                            onAnswerSelected = { selected ->
                                val isCorrect = selected == question.correctAnswer
                                if( userAnswers.size <= currentIndex ) {
                                    userAnswers.add(selected to isCorrect)
                                }
                            }
                        )
                    }

                    // Botón Siguiente
                    Button(
                        onClick = {
                            if (currentIndex < questions.lastIndex) {
                                currentIndex++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        enabled = currentIndex < questions.lastIndex
                    ) {
                        Text("Siguiente")
                    }
                }
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando pregunta...")
            }
        }
    )
}

@Composable
fun QuizQuestionView(
    paddingValues: PaddingValues = PaddingValues(12.dp),
    question: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        color = Color.Magenta
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = question,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun QuizAnswerView(
    paddingValues: PaddingValues = PaddingValues(12.dp),
    correctAnswer: String,
    incorrectAnswers: List<String>,
    onAnswerSelected: (String) -> Unit = {}
) {
    val allAnswers = remember(correctAnswer, incorrectAnswers) {
        (incorrectAnswers + correctAnswer).shuffled()
    }

    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 16.dp),
        color = Color.Cyan
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            allAnswers.forEach { answer ->

                val isSelected = selectedAnswer == answer
                val isCorrect = answer == correctAnswer

                Button(
                    onClick = {
                        selectedAnswer = answer
                        onAnswerSelected(answer)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            isSelected && isCorrect -> Color(0xFF4CAF50)
                            isSelected && !isCorrect -> Color(0xFFF44336)
                            else -> Color(0xFF6200EE)
                        },
                    )
                ) {
                    Text(text = answer, color = Color.Black)
                }
            }
        }
    }
}
