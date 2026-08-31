package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.HomeScreen
import com.example.ui.ProcessingScreen
import com.example.ui.ResultsScreen
import com.example.ui.UploadScreen
import com.example.ui.theme.ResyncTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ResyncTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val navController = rememberNavController()
          NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("upload") { UploadScreen(navController) }
            composable("processing") { ProcessingScreen(navController) }
            composable("results") { ResultsScreen(navController) }
          }
        }
      }
    }
  }
}
