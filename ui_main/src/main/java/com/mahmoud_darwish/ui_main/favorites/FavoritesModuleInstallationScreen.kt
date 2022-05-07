package com.mahmoud_darwish.ui_main.favorites

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.data.R
import com.mahmoud_darwish.ui_core.CenteredContent
import com.mahmoud_darwish.ui_core.CenteredText
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.ui_main.compose_nav_graph.MainUiNavGraph
import com.mahmoud_darwish.ui_main.destinations.HomeContentDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@MainUiNavGraph
@Destination
@Composable
fun FavoritesModuleInstallationScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesModuleInstallationScreenViewModel = getViewModel()
) {
    val context = LocalContext.current

    val moduleInstallationState: ModuleInstallationState by viewModel
        .installationState
        .collectAsState(ModuleInstallationState.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back_icon_content_description)
                        )
                    }
                },
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        CenteredContent {
            Column(
                Modifier
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mediumPadding)
            ) {
                when (moduleInstallationState) {
                    is ModuleInstallationState.Installed -> {
                        GoToFavoritesScreenAndRemoveCurrentBackstackEntry(
                            viewModel, context, navigator
                        )
                    }
                    !is ModuleInstallationState.Installed -> Button(onClick = { viewModel.installModule() }) {
                        Text(text = stringResource(R.string.install_module))
                    }
                    else -> {
                        CenteredText(message = moduleInstallationState.progressMessage())
                    }
                }
            }
        }
    }
}

@Composable
fun GoToFavoritesScreenAndRemoveCurrentBackstackEntry(
    viewModel: FavoritesModuleInstallationScreenViewModel,
    context: Context,
    navigator: DestinationsNavigator
) {
    LaunchedEffect(true) {
        navigator.popBackStack(route = HomeContentDestination.route, inclusive = false)
        viewModel.goToFavorites(context)
    }
}

