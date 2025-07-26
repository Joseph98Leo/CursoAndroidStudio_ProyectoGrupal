package com.usil.myappcomponents.quiz

import android.app.Activity
import android.content.Intent
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
import com.usil.myappcomponents.components.QuizAnswerView
import com.usil.myappcomponents.components.QuizQuestionView
import com.usil.myappcomponents.components.ResultadoQuizActivity
import com.usil.myappcomponents.navigation.AppNavigation
import com.usil.myappcomponents.quiz.ui.theme.MyAppComponentsTheme
import com.usil.myappcomponents.viewModel.QuizViewModel

class QuizQuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppComponentsTheme {
                AppNavigation()
                // QuizScreen()
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

    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getQuestions()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Preguntas del Quiz",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F51B5)
                )
            )
        },
        content = { paddingValues ->
            currentQuestion?.let { question ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        QuizQuestionView(
                            paddingValues = PaddingValues(0.dp),
                            question = question.question
                        )
                        QuizAnswerView(
                            paddingValues = PaddingValues(0.dp),
                            correctAnswer = question.correctAnswer,
                            incorrectAnswers = question.incorrectAnswers,
                            selectedAnswer = selectedAnswer,
                            onAnswerSelected = { selected ->
                                val isCorrect = selected == question.correctAnswer
                                if (userAnswers.size <= currentIndex) {
                                    userAnswers.add(selected to isCorrect)
                                }
                                selectedAnswer = selected
                            }
                        )
                    }

                    Button(
                        onClick = {
                            if (currentIndex < questions.lastIndex) {
                                currentIndex++
                                selectedAnswer = null
                            } else {
                                // Ir a la pantalla de resultados
                                val correctCount = userAnswers.count { it.second }
                                val incorrectCount = userAnswers.count { !it.second }
                                val unansweredCount = questions.size - userAnswers.size

                                val intent = Intent(context, ResultadoQuizActivity::class.java).apply {
                                    putExtra("correctCount", correctCount)
                                    putExtra("incorrectCount", incorrectCount)
                                    putExtra("unansweredCount", unansweredCount)
                                }
                                context.startActivity(intent)
                                (context as Activity).finish()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)),
                        enabled = selectedAnswer != null // Solo permite avanzar si se eligió una respuesta
                    ) {
                        Text(
                            if (currentIndex < questions.lastIndex) "Siguiente" else "Finalizar",
                            color = Color.White
                        )
                    }
                }
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF3F51B5))
            }
        }
    )
}