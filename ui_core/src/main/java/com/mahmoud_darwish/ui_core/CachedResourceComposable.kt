package com.mahmoud_darwish.ui_core

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.ResponseSource
import com.mahmoud_darwish.ui_core.theme.loadingIndicatorSize

/**
 * This composable is used to show the user standard UI in the Error, and loading states.
 * @param onSuccess is required because it differs from one use case to another, as opposed to the other two parameters.
 * */
@Composable
fun <T> CachedResource<T>.Composable(
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = {
        CenteredContent(modifier) {
            CircularProgressIndicator(modifier = Modifier.size(loadingIndicatorSize))
        }
    },
    onError: @Composable (errorMessage: String) -> Unit = { message ->
        CenteredText(message = message)
    },
    onSuccess: @Composable (data: T, responseSource: ResponseSource) -> Unit
) = when (this) {
    is CachedResource.Success -> onSuccess(data, responseSource)
    is CachedResource.Loading -> onLoading()
    is CachedResource.Error -> onError(errorMessage)
}

