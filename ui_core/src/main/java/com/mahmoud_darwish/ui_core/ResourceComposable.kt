package com.mahmoud_darwish.ui_core

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.ui_core.theme.loadingIndicatorSize

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
    onSuccess: @Composable (data: T, source: Source) -> Unit
) = when (this) {
    is Resource.Success -> onSuccess(data, source)
    is Resource.Loading -> onLoading()
    is Resource.Error -> onError(message)
}

