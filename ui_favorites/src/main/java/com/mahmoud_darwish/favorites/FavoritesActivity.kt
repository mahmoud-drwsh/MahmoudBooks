package com.mahmoud_darwish.favorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.data.di.FavoritesModuleDependencies
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.ui_core.theme.MahmoudBooksTheme
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoritesActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()

        super.onCreate(savedInstanceState)

        setContent {
            val favorites by viewModel.favorites.collectAsState(initial = Resource.Loading)

            MahmoudBooksTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "My Favorites") }, navigationIcon = {
                            IconButton(onClick = { navigateBack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Navigate back")
                            }
                        })
                    },
                ) {

                    favorites.ResourceComposable(modifier = Modifier.padding(it)) { list: List<Volume>, _ ->
                        Column(
                            verticalArrangement = spacedBy(mediumPadding),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = mediumPadding)
                        ) {
                            Text(
                                text = "Favorites count: ${list.size}",
                                modifier = Modifier.padding(horizontal = mediumPadding)
                            )
                            StatelessBooksHorizontalLazyRow(
                                volumesList = list,
                                onItemClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun inject() {
        DaggerFavoritesComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoritesModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    private fun navigateBack() = this.finish()
}


// ------------------------------------------------------------------------------------------------

