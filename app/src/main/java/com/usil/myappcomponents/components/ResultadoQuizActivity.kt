package com.usil.myappcomponents.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.usil.myappcomponents.quiz.QuizQuestionActivity
import com.usil.myappcomponents.quiz.ui.theme.MyAppComponentsTheme

class ResultadoQuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val correct = intent.getIntExtra("correctCount", 0)
        val incorrect = intent.getIntExtra("incorrectCount", 0)
        val unanswered = intent.getIntExtra("unansweredCount", 0)

        setContent {
            MyAppComponentsTheme {
                ResultadoScreen(
                    correctCount = correct,
                    incorrectCount = incorrect,
                    unansweredCount = unanswered,
                    onRetry = {
                        val intent = Intent(this, QuizQuestionActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    },
                    onExit = {
                        (this as Activity).finish()
                    }
                )
            }
        }
    }
}
