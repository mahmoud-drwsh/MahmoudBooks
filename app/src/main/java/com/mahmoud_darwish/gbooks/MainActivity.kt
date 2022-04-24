package com.mahmoud_darwish.gbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mahmoud_darwish.presentation.NavGraphs
import com.mahmoud_darwish.presentation.ui.theme.MahmoudBooksTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MahmoudBooksTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}


