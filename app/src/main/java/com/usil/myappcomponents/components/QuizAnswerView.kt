package com.usil.myappcomponents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuizAnswerView(
    paddingValues: PaddingValues = PaddingValues(12.dp),
    correctAnswer: String,
    incorrectAnswers: List<String>,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    val allAnswers = remember(correctAnswer, incorrectAnswers) {
        (incorrectAnswers + correctAnswer).shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        allAnswers.forEach { answer ->
            val isSelected = selectedAnswer == answer
            val isCorrect = answer == correctAnswer

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        isSelected && isCorrect -> Color(0xFF4CAF50)
                        isSelected && !isCorrect -> Color(0xFFF44336)
                        else -> Color.White
                    }
                ),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    if (selectedAnswer == null) {
                        onAnswerSelected(answer)
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = answer,
                        modifier = Modifier.padding(start = 16.dp),
                        color = if (isSelected) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

