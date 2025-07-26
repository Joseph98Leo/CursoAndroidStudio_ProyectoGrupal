package com.usil.myappcomponents.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultadoScreen(
    correctCount: Int,
    incorrectCount: Int,
    unansweredCount: Int,
    onRetry: () -> Unit,
    onExit: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF3E5F5)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("¡Quiz finalizado!", style = MaterialTheme.typography.headlineLarge)

            Text("✅ Correctas: $correctCount", style = MaterialTheme.typography.titleMedium, color = Color(0xFF4CAF50))
            Text("❌ Incorrectas: $incorrectCount", style = MaterialTheme.typography.titleMedium, color = Color(0xFFF44336))
            Text("❓ No contestadas: $unansweredCount", style = MaterialTheme.typography.titleMedium, color = Color(0xFF9E9E9E))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Intentar de nuevo")
                }

                OutlinedButton(
                    onClick = onExit,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salir")
                }
            }
        }
    }
}
