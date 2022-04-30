package com.mahmoud_darwish.compose_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CenteredText(message: String) = CenteredContent {
    Text(text = message)
}

