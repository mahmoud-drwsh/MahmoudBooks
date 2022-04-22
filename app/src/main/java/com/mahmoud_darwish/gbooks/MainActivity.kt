package com.mahmoud_darwish.gbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.mahmoud_darwish.presentation.Home
import com.mahmoud_darwish.presentation.ui.theme.GBooksTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GBooksTheme {
                Scaffold(
                    topBar = { HomeTopBar() }
                ) { }
            }
        }
    }

}

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(text = "GBooks")
        }
    )
}
