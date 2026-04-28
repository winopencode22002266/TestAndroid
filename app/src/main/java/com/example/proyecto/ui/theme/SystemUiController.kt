package com.example.proyecto.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val currentWindow = (view.context as Activity).window
        currentWindow.statusBarColor = color.toArgb()
        WindowInsetsControllerCompat(currentWindow, view).isAppearanceLightStatusBars = darkIcons
    }
}
