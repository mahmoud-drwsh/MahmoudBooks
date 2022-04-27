package com.mahmoud_darwish.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoud_darwish.compose_components.ResourceComposable
import com.mahmoud_darwish.compose_components.SmallLoadingIndicator
import com.mahmoud_darwish.compose_components.theme.imageHeight
import com.mahmoud_darwish.compose_components.theme.imageShape
import com.mahmoud_darwish.compose_components.theme.mediumPadding
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import java.text.DecimalFormat


@Destination(start = true)
@Composable
fun Home(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold {
        Column(Modifier.padding(it)) {

            val data by homeViewModel.searchResult.collectAsState(Resource.Loading)

            val query by homeViewModel.query.collectAsState("")

            SearchBar(
                queryString = query,
                onQueryStringChanged = { newQuery ->
                    homeViewModel.onEvent(HomeUIEvent.UpdateQuery(newQuery))
                }
            )

            ResultsSection(data)
        }
    }
}


@Composable
private fun ResultsSection(data: Resource<List<Volume>>) {
    val numberFormat = remember { DecimalFormat.getInstance() }

    data.ResourceComposable { volumesList ->
        Column(
            verticalArrangement = spacedBy(mediumPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "Book results",
                    modifier = Modifier.padding(horizontal = mediumPadding),
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "${volumesList.size}",
                    modifier = Modifier.padding(horizontal = mediumPadding)
                )
            }
            LazyRow(
                contentPadding = PaddingValues(mediumPadding), modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = spacedBy(8.dp),
            ) {
                items(volumesList) { volume ->
                    Column(
                        verticalArrangement = spacedBy(mediumPadding),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        GlideImage(
                            imageModel = volume.thumbnail,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .height(imageHeight)
                                .clip(imageShape),
                            loading = {
                                SmallLoadingIndicator()
                            }
                        )

                        Text(text = "${numberFormat.format(volume.price)} IDR")
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    queryString: String,
    onQueryStringChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .padding(mediumPadding)
            .fillMaxWidth(),
        value = queryString,
        onValueChange = onQueryStringChanged,
        label = { Text(text = "Book title") }
    )
}