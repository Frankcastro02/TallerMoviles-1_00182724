package com.pdm0126.taller1_00182724

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MasterController()
                }
            }
        }
    }
}

@Composable
fun MasterController() {
    var flowState by remember { mutableStateOf("INTRO") }
    var currentStep by remember { mutableIntStateOf(0) }
    var points by remember { mutableIntStateOf(0) }

    when (flowState) {
        "INTRO" -> WelcomeStage { flowState = "GAME" }
        "GAME" -> TriviaStage(
            step = currentStep,
            currentPoints = points,
            onPointGained = { points++ },
            onStepFinished = {
                // listaDeRetos debe estar definida en QuizData.kt [cite: 25, 26]
                if (currentStep < 2) {
                    currentStep++
                } else {
                    flowState = "END"
                }
            }
        )
        "END" -> SummaryStage(points) {
            points = 0
            currentStep = 0
            flowState = "INTRO"
        }
    }
}

@Composable
fun WelcomeStage(onStart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary // Aquí el cambio clave es 'tint'
        )
        Text("AndroidPedia", fontSize = 34.sp, fontWeight = FontWeight.Black)
        Text("Pon a prueba tu conocimiento", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedCard {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Desarrollado por:", style = MaterialTheme.typography.labelMedium)
                Text("Francisco Castro", fontWeight = FontWeight.Bold)
                Text("Carnet: 00182724")
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth(0.8f).height(55.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Start", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TriviaStage(step: Int, currentPoints: Int, onPointGained: () -> Unit, onStepFinished: () -> Unit) {
    val data = listaDeRetos[step]
    var userChoice by remember { mutableStateOf("") }
    var revealed by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Puntos: $currentPoints/3", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text("Reto ${step + 1}/3")
        }

        LinearProgressIndicator(
            progress = { (step + 1) / 3f },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
        )

        Text(text = data.enunciado, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(vertical = 16.dp))

        data.variantes.forEach { item ->
            val isCorrect = item == data.respuestaCorrecta
            val isSelected = item == userChoice

            val containerColor = when {
                !revealed -> MaterialTheme.colorScheme.surfaceVariant
                isCorrect -> Color(0xFFD4EDDA)
                isSelected -> Color(0xFFF8D7DA)
                else -> MaterialTheme.colorScheme.surface
            }

            ElevatedButton(
                onClick = {
                    if (!revealed) {
                        userChoice = item
                        revealed = true
                        if (isCorrect) onPointGained()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = containerColor),
                enabled = !revealed,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(item, color = Color.Black)
            }
        }

        if (revealed) {
            Spacer(modifier = Modifier.height(20.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    // ESTA ES LA LÍNEA CORREGIDA
                    Icon(imageVector = Icons.Default.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(data.curiosidad, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Button(
                onClick = {
                    revealed = false
                    userChoice = ""
                    onStepFinished()
                },
                modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
            ) {
                Text(if (step < 2) "Next" else "RESULTS")
            }
        }
    }
}

@Composable
fun SummaryStage(score: Int, onReset: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Taller Completado!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Puntaje Final: $score de 3", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = when(score) {
                3 -> "¡Felicidades! Eres un experto en Android."
                2 -> "¡Muy bien! Tienes buenos conocimientos."
                else -> "¡Sigue practicando!"
            },
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onReset) {
            Text("REINTENTAR ")
        }
    }
}