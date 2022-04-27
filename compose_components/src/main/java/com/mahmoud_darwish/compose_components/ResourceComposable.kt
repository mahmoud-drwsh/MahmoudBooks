package com.mahmoud_darwish.compose_components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mahmoud_darwish.compose_components.theme.loadingIndicatorSize
import com.mahmoud_darwish.domain.util.Resource

@Composable
fun <T> Resource<T>.ResourceComposable(
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = {
        CenteredContent(modifier) {
            CircularProgressIndicator(modifier = Modifier.size(loadingIndicatorSize))
        }
    },
    onError: @Composable (message: String) -> Unit = { message ->
        CenteredText(message = message)
    },
    onSuccess: @Composable (data: T) -> Unit
) = when (this) {
    is Resource.Success -> onSuccess(data)
    is Resource.Loading -> onLoading()
    is Resource.Error -> onError(message)
}

