package com.mahmoud_darwish.favorites.compose_nav_graph

import com.ramcosta.composedestinations.annotation.NavGraph

/**
 * This is used by the Compose Destinations library for generating a nav graph.
 * */
@NavGraph
annotation class FavoritesNavGraph(
    @Suppress("unused")
    // this will be used by the code-generating library
    val start: Boolean = false
)