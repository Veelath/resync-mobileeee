package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = SurfaceWhite,
    background = BackgroundWhite,
    onBackground = TextDark,
    surface = SurfaceWhite,
    onSurface = TextDark,
    secondary = TextGray
)

@Composable
fun ResyncTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = LightColorScheme // Force light theme for Resync brand consistency

  val view = LocalView.current
  if (!view.isInEditMode) {
      SideEffect {
          val window = (view.context as Activity).window
          window.statusBarColor = colorScheme.background.toArgb()
          window.navigationBarColor = colorScheme.background.toArgb()
          WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
          WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
      }
  }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
