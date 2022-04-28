package com.mahmoud_darwish.compose_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.mahmoud_darwish.compose_components.SmallLoadingIndicator
import com.mahmoud_darwish.compose_components.theme.imageHeight
import com.mahmoud_darwish.compose_components.theme.imageShape
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BookImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    Surface(
        modifier = modifier
            .height(imageHeight)
            .height(imageHeight * 2)
            .clip(imageShape)
    ) {
        GlideImage(
            imageModel = imageUrl,
            contentScale = contentScale,
            loading = {
                SmallLoadingIndicator()
            },
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}
