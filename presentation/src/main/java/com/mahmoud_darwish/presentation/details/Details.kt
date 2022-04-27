package com.mahmoud_darwish.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mahmoud_darwish.compose_components.CenteredText
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Details(name: String = "") = Scaffold {
    Box(Modifier.padding(it)) {
        CenteredText(message = "Allah")
    }
}