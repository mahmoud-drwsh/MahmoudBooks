package com.mahmoud_darwish.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoud_darwish.compose_components.ResourceComposable
import com.mahmoud_darwish.compose_components.theme.mediumPadding
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import com.mahmoud_darwish.presentation.R
import com.mahmoud_darwish.presentation.destinations.DetailsDestination
import com.mahmoud_darwish.compose_components.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.presentation.shared.HomeBottomBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


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

            val query by homeViewModel.query.collectAsState(stringResource(R.string.empty_string))

            SearchBar(
                queryString = query,
                onQueryStringChanged = { newQuery ->
                    homeViewModel.onEvent(HomeUIEvent.UpdateQuery(newQuery))
                }
            )

            SearchResultsSection(data, {
                homeViewModel.onEvent(homeUIEvent = HomeUIEvent.ForceLoadingFromServer())
            }
            ) { volume ->
                navigator.navigate(DetailsDestination(volumeId = volume.id))
            }
        }
    }
}

@Composable
private fun SearchResultsSection(
    data: Resource<List<Volume>>,
    onRefreshClicked: () -> Unit,
    onItemClicked: (Volume) -> Unit
) {
    data.ResourceComposable { volumesList, source, message ->

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
            text = "Results source: ${
                when (source) {
                    Source.REMOTE -> "Remote"
                    Source.CACHE -> "Cache"
                }
            }",
            modifier = Modifier.padding(horizontal = mediumPadding),
            style = MaterialTheme.typography.h6
        )

        TextButton(
            onClick = onRefreshClicked,
            modifier = Modifier.padding(horizontal = mediumPadding),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = "Refresh")
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
        label = { Text(text = "Book title") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") }
    )
}

