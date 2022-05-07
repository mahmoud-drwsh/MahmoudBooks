package com.mahmoud_darwish.ui_core

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CenteredText(message: String) = CenteredContent {
    Text(text = message, textAlign = TextAlign.Center)
}

