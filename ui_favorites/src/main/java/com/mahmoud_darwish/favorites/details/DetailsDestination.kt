package com.mahmoud_darwish.favorites.details

import androidx.compose.runtime.Composable
import com.mahmoud_darwish.favorites.compose_nav_graph.FavoritesNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@FavoritesNavGraph
@Destination
@Composable
fun DetailsDestination(navigator: DestinationsNavigator, volumeId: String) =
    com.mahmoud_darwish.ui_main.details.Details(navigator = navigator, volumeId = volumeId)