package com.mahmoud_darwish.ui_main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.ui_main.NavGraphs
import com.mahmoud_darwish.ui_main.destinations.DetailsDestination
import com.mahmoud_darwish.ui_main.shared.BottomBar
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@Composable
fun Home() {
    val navHostController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController = navHostController)
    }) { paddingValues ->
        DestinationsNavHost(
            navController = navHostController,
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Destination(start = true)
@Composable
fun HomeContent(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = getViewModel(),
) {
    val onVolumeClicked: (Volume) -> Unit = {
        navigator.navigate(DetailsDestination(it.id))
    }

    Column {

        val data by homeViewModel.searchResult.collectAsState(Resource.Loading)

        val query by homeViewModel.query.collectAsState("")

        SearchBar(
            queryString = query,
            onQueryStringChanged = { newQuery ->
                homeViewModel.onEvent(HomeUIEvent.UpdateQuery(newQuery))
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
    data: Resource<List<Volume>>,
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

