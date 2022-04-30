package com.mahmoud_darwish.compose_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.mahmoud_darwish.compose_components.theme.imageHeight
import com.mahmoud_darwish.compose_components.theme.imageShape
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BookImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillWidth,
    onClick: (() -> Unit)? = null
) = Surface(
    modifier = modifier
        .height(imageHeight)
        .clip(imageShape)
) {
    val imageModifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier

    GlideImage(
        imageModel = imageUrl,
        contentScale = contentScale,
        loading = { SmallLoadingIndicator() },
        modifier = imageModifier
    )
}
