package com.mahmoud_darwish.presentation.favorites

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.mahmoud_darwish.ui_core.CenteredText
import com.mahmoud_darwish.core.util.Constants
import com.mahmoud_darwish.data.util.UiText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Destination
@Composable
fun FavoritesModuleInstallationProgress(
    navigator: DestinationsNavigator,
    viewModel: FavoritesModuleInstallationProgressViewModel = hiltViewModel()
) {
    /*TODO: COMPLETE*/
    val collectAsState by viewModel
        .installationState
        .collectAsState(FavoritesModuleInstallationProgressViewModel.ModuleInstallationState.INSTALLING)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "navigate back")
                    }
                },
                title = {},
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        bottomBar = {

        }
    ) {
        Row(Modifier.padding(it)) { CenteredText(message = collectAsState.toString()) }
    }
}

@HiltViewModel
class FavoritesModuleInstallationProgressViewModel @Inject constructor(
    private val uiText: UiText,
    private val app: Application
) : ViewModel() {

    private var _moduleName: String? = Constants.favoritesFeatureModuleName

    private val _installationState: MutableStateFlow<ModuleInstallationState> =
        MutableStateFlow(ModuleInstallationState.INSTALLING)
    val installationState: StateFlow<ModuleInstallationState>
        get() = _installationState

    fun setModuleName(moduleName: String) {
        _moduleName = moduleName
    }

    private fun installChatModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(app)

        if (splitInstallManager.installedModules.contains(_moduleName)) {
            updateInstallationState(ModuleInstallationState.INSTALLED)
        } else {
            updateInstallationState(ModuleInstallationState.INSTALLING)

            val request = SplitInstallRequest.newBuilder()
                .addModule(_moduleName)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    updateInstallationState(ModuleInstallationState.INSTALLED)
                }
                .addOnFailureListener {
                    it.printStackTrace()

                    updateInstallationState(
                        ModuleInstallationState.InstallError(
                            message = it.message ?: uiText.unknownErrorMessage
                        )
                    )
                }
        }
    }

    private fun updateInstallationState(newState: ModuleInstallationState) {
        _installationState.value = newState
    }

    sealed class ModuleInstallationState {
        object INSTALLING : ModuleInstallationState()
        object INSTALLED : ModuleInstallationState()
        class InstallError(val message: String? = null) : ModuleInstallationState()
    }
}
