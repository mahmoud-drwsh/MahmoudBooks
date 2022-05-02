package com.mahmoud_darwish.gbooks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mahmoud_darwish.ui_core.theme.MahmoudBooksTheme
import com.mahmoud_darwish.ui_main.home.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MahmoudBooksTheme { Home() }
        }
    }
}


