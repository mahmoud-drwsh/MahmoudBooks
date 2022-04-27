package com.mahmoud_darwish.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoud_darwish.compose_components.ResourceComposable
import com.mahmoud_darwish.compose_components.SmallLoadingIndicator
import com.mahmoud_darwish.compose_components.theme.imageHeight
import com.mahmoud_darwish.compose_components.theme.imageShape
import com.mahmoud_darwish.compose_components.theme.mediumPadding
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import java.text.DecimalFormat
import java.text.NumberFormat


@Destination(start = true)
@Composable
fun Home(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = { HomeBottomBar() }
    ) {
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
fun HomeBottomBar() {
    BottomNavigation {
        BottomNavigationItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Home, "Home icon") },
            label = {
                Text(text = "Home")
            }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Favorite, "My favorites icon") },
            label = {
                Text(text = "Favorites")
            }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Settings, "Settings icon") },
            label = {
                Text(text = "Settings")
            })
    }
}


@Composable
private fun ResultsSection(data: Resource<List<Volume>>) {
    val numberFormat = remember { DecimalFormat.getInstance() }

    data.ResourceComposable { volumesList, source, message ->
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "Results source: ${
                        when (source) {
                            Source.REMOTE -> "Remote"
                            Source.CACHE -> "Cache"
                        }
                    }",
                    modifier = Modifier.padding(horizontal = mediumPadding),
                    style = MaterialTheme.typography.h6
                )
            }
            LazyRow(
                contentPadding = PaddingValues(mediumPadding),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = spacedBy(8.dp),
            ) {
                items(volumesList) { volume ->
                    BookThumbnailWithPriceListItem(volume, numberFormat)
                }
            }
        }
    }
}

@Composable
private fun BookThumbnailWithPriceListItem(
    volume: Volume,
    numberFormat: NumberFormat
) {
    Column(
        verticalArrangement = spacedBy(mediumPadding),
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .height(imageHeight)
    ) {
        Surface(
            modifier = Modifier
                .clip(imageShape)
                .weight(1f)
        ) {
            GlideImage(
                imageModel = volume.thumbnailLarge,
                contentScale = ContentScale.FillHeight,
                loading = {
                    SmallLoadingIndicator()
                },
            )
        }

        Text(
            text = "${numberFormat.format(volume.price)} IDR",
            style = MaterialTheme.typography.caption.copy(fontSize = 21.sp)
        )
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
        label = { Text(text = "Book title") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") }
    )
}