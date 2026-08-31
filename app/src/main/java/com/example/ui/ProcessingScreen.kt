package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ProcessingScreen(navController: NavController) {
    var currentStep by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(1000)
        currentStep = 1
        delay(1000)
        currentStep = 2
        delay(1500)
        currentStep = 3
        delay(1000)
        currentStep = 4
        delay(500)
        navController.navigate("results") {
            popUpTo("home") // Pop everything up to home so back button on results goes home
        }
    }

    Scaffold(containerColor = BackgroundWhite) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            
            // Scanning Icon Animation Placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(PrimaryBlue.copy(alpha = 0.5f), Color.Transparent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryBlue, Color(0xFF818CF8))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = SurfaceWhite, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Scanning...", style = MaterialTheme.typography.headlineMedium, color = TextDark)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Sit tight, this takes about 2-3 minutes.", style = MaterialTheme.typography.bodyMedium, color = TextGray)

            Spacer(modifier = Modifier.height(48.dp))

            // Steps
            ProcessingStep(
                title = "Parsing manuscript structure",
                status = if (currentStep > 0) StepStatus.COMPLETED else StepStatus.PENDING
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProcessingStep(
                title = "Computing semantic embeddings",
                status = when {
                    currentStep > 1 -> StepStatus.COMPLETED
                    currentStep == 1 -> StepStatus.IN_PROGRESS
                    else -> StepStatus.PENDING
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProcessingStep(
                title = "Gemini 2.5 Pro deep reasoning",
                status = when {
                    currentStep > 2 -> StepStatus.COMPLETED
                    currentStep == 2 -> StepStatus.IN_PROGRESS
                    else -> StepStatus.PENDING
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProcessingStep(
                title = "Checking citation accessibility",
                status = when {
                    currentStep > 3 -> StepStatus.COMPLETED
                    currentStep == 3 -> StepStatus.IN_PROGRESS
                    else -> StepStatus.PENDING
                }
            )
        }
    }
}

enum class StepStatus { PENDING, IN_PROGRESS, COMPLETED }

@Composable
fun ProcessingStep(title: String, status: StepStatus) {
    val bgColor = SurfaceWhite
    val contentColor = when (status) {
        StepStatus.COMPLETED -> Color(0xFF16A34A) // Green
        StepStatus.IN_PROGRESS -> PrimaryBlue
        StepStatus.PENDING -> TextGray.copy(alpha = 0.5f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when (status) {
                        StepStatus.COMPLETED -> PastelGreen
                        StepStatus.IN_PROGRESS -> PastelBlue
                        StepStatus.PENDING -> BackgroundWhite
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (status == StepStatus.COMPLETED) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = contentColor, modifier = Modifier.size(16.dp))
            } else if (status == StepStatus.IN_PROGRESS) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(PrimaryBlue))
            } else {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(TextGray.copy(alpha = 0.3f)))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = if (status == StepStatus.IN_PROGRESS) FontWeight.Bold else FontWeight.Normal),
            color = contentColor
        )
    }
}
