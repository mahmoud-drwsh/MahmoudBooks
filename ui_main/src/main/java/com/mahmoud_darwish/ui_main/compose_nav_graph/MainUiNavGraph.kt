package com.mahmoud_darwish.ui_main.compose_nav_graph

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class MainUiNavGraph(
    @Suppress("unused")
    // this will be used by the code-generating library
    val start: Boolean = false
)