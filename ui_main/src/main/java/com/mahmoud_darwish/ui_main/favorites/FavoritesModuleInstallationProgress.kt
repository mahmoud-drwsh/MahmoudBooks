package com.mahmoud_darwish.ui_main.favorites

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.core.util.Constants
import com.mahmoud_darwish.ui_core.CenteredContent
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavoritesModuleInstallationProgress(
    navigator: DestinationsNavigator,
    viewModel: FavoritesModuleInstallationProgressViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val collectAsState: ModuleInstallationState by viewModel
        .installationState
        .collectAsState(ModuleInstallationState.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "navigate back")
                    }
                },
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        bottomBar = {

        }
    ) {
        CenteredContent {
            Column(
                Modifier
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mediumPadding)
            ) {

                if (collectAsState !is ModuleInstallationState.Installed)
                    Button(onClick = { viewModel.installModule() }) {
                        Text(text = "Install module")
                    }
                else {
                    Text(
                        text = collectAsState.progressMessage(),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { /*viewModel.goToFavorites()*/
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Constants.FeaturesMainActivityUriString.toUri()
                            ).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                    }) {
                        Text(text = "Open")
                    }
                }
            }
        }
    }
}

fun ModuleInstallationState.progressMessage(): String = when (this) {
    ModuleInstallationState.Loading -> "The module is being installed"
    is ModuleInstallationState.Installed -> "The module has been installed"
    is ModuleInstallationState.InstallError -> message ?: ""
}

