package com.mahmoud_darwish.compose_components

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.mahmoud_darwish.compose_components.theme.imageHeight
import com.mahmoud_darwish.compose_components.theme.mediumPadding
import com.mahmoud_darwish.core.model.Volume
import java.text.DecimalFormat
import java.text.NumberFormat

@Composable
fun StatelessBooksHorizontalLazyRow(
    volumesList: List<Volume>,
    numberFormat: NumberFormat = remember { DecimalFormat.getInstance() },
    onItemClick: (Volume) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = mediumPadding),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = spacedBy(mediumPadding),
    ) {
        items(volumesList) { volume ->
            Column(
                verticalArrangement = spacedBy(mediumPadding),
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier.height(imageHeight)
            ) {
                BookImage(
                    imageUrl = volume.thumbnailLarge,
                    modifier = Modifier
                        .weight(1f),
                    contentScale = ContentScale.FillHeight
                ) { onItemClick(volume) }
                Text(
                    text = "${numberFormat.format(volume.price)} IDR",
                    style = MaterialTheme.typography.caption.copy(fontSize = 17.sp)
                )
            }
        }
    }
}
