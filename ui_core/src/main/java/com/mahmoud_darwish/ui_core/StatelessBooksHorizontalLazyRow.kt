package com.mahmoud_darwish.ui_core

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.data.R
import com.mahmoud_darwish.ui_core.theme.imageHeight
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.ui_core.util.priceFormatter
import java.text.NumberFormat

@Composable
fun StatelessBooksHorizontalLazyRow(
    volumesList: List<Volume>,
    numberFormat: NumberFormat = priceFormatter,
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
                    text = stringResource(
                        id = R.string.price_idr,
                        numberFormat.format(volume.price)
                    ),
                    style = MaterialTheme.typography.caption.copy(fontSize = 17.sp)
                )
            }
        }
    }
}
