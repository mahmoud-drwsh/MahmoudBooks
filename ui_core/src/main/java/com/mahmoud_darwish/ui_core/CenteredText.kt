package com.mahmoud_darwish.ui_core

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CenteredText(message: String) = CenteredContent {
    Text(text = message)
}

