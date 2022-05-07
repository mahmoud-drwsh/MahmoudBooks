package com.mahmoud_darwish.favorites.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.data.R
import com.mahmoud_darwish.favorites.FavoritesDynamicFeatureModuleMainActivity
import com.mahmoud_darwish.favorites.compose_nav_graph.FavoritesNavGraph
import com.mahmoud_darwish.favorites.destinations.DetailsDestinationDestination
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.ui_core.theme.MahmoudBooksTheme
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Destination
@FavoritesNavGraph(start = true)
@Composable
fun FavoritesHome(
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel = getViewModel()
) {
    val favorites by viewModel.favorites.collectAsState(initial = CachedResource.Loading)

    MahmoudBooksTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.my_favorites)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            FavoritesDynamicFeatureModuleMainActivity.currentRunningInstance?.finish()
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.navigate_back_icon_content_description)
                            )
                        }
                    })
            },
        ) {

            favorites.ResourceComposable(modifier = Modifier.padding(it)) { list: List<Volume>, _ ->
                Column(
                    verticalArrangement = Arrangement.Absolute.spacedBy(mediumPadding),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = mediumPadding)
                ) {
                    Text(
                        text = stringResource(id = R.string.favorites_count, list.size),
                        modifier = Modifier.padding(horizontal = mediumPadding)
                    )
                    StatelessBooksHorizontalLazyRow(
                        volumesList = list,
                        onItemClick = { volume ->
                            navigator.navigate(DetailsDestinationDestination(volume.id))
                        }
                    )
                }
            }
        }
    }
}