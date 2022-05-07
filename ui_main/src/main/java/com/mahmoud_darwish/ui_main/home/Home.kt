package com.mahmoud_darwish.ui_main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.data.R.string
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.ui_main.NavGraphs
import com.mahmoud_darwish.ui_main.compose_nav_graph.MainUiNavGraph
import com.mahmoud_darwish.ui_main.destinations.DetailsDestination
import com.mahmoud_darwish.ui_main.destinations.FavoritesModuleInstallationScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@Composable
fun Home() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

@MainUiNavGraph(start = true)
@Destination
@Composable
fun HomeContent(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val onVolumeClicked: (Volume) -> Unit = {
        navigator.navigate(DetailsDestination(it.id))
    }

    Column {

        val data by homeViewModel.searchResult.collectAsState(CachedResource.Loading)

        val query by homeViewModel.query.collectAsState(stringResource(id = string.empty_string))

        SearchBar(
            queryString = query,
            onQueryStringChanged = { newQuery ->
                homeViewModel.onEvent(HomeUIEvent.UpdateQuery(newQuery))
            },
            goToFavorites = {
                navigator.navigate(FavoritesModuleInstallationScreenDestination())
            }
        )

        SearchResultsSection(
            data = data,
            onRefreshClicked = { homeViewModel.onEvent(homeUIEvent = HomeUIEvent.ForceLoadingFromServer) },
            onItemClicked = onVolumeClicked
        )
    }
}

@Composable
private fun SearchResultsSection(
    data: CachedResource<List<Volume>>,
    onRefreshClicked: () -> Unit,
    onItemClicked: (Volume) -> Unit
) {
    data.ResourceComposable { volumesList, source ->

        Column(verticalArrangement = spacedBy(mediumPadding)) {

            DataSourceInformationRow(source, onRefreshClicked)

            StatelessBooksHorizontalLazyRow(volumesList, onItemClick = onItemClicked)
        }
    }
}

@Composable
private fun DataSourceInformationRow(
    source: Source,
    onRefreshClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = stringResource(
                id = string.results_source_message, when (source) {
                    Source.REMOTE -> stringResource(string.remote)
                    Source.CACHE -> stringResource(string.cache)
                }
            ),
            modifier = Modifier.padding(horizontal = mediumPadding),
            style = MaterialTheme.typography.h6
        )

        TextButton(
            onClick = onRefreshClicked,
            modifier = Modifier.padding(horizontal = mediumPadding),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = stringResource(string.refresh))
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    queryString: String,
    onQueryStringChanged: (String) -> Unit,
    goToFavorites: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .padding(mediumPadding)
            .fillMaxWidth(),
        value = queryString,
        onValueChange = onQueryStringChanged,
        placeholder = { Text(text = stringResource(string.search_play_books)) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = goToFavorites) {
                Icon(
                    Icons.Default.Bookmarks,
                    contentDescription = stringResource(string.open_favorites_icon_description)
                )
            }
        }
    )
}

