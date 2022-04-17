package com.mahmoud_darwish.gbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahmoud_darwish.gbooks.ui.theme.GBooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GBooksTheme {
                Scaffold(
                    topBar = { HomeTopBar() }
                ) {

                }
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
