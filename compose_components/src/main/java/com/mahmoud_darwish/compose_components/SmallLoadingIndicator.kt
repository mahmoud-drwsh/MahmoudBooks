package com.mahmoud_darwish.compose_components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mahmoud_darwish.compose_components.theme.loadingIndicatorSize

@Composable
fun SmallLoadingIndicator() {
    CenteredContent {
        CircularProgressIndicator(
            modifier = Modifier.size(loadingIndicatorSize)
        )
    }
}
