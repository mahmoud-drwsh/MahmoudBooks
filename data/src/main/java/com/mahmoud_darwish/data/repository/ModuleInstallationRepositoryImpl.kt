package com.mahmoud_darwish.data.repository

import android.app.Application
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single(binds = [IModuleInstallationRepository::class])
class ModuleInstallationRepositoryImpl(
    private val uiText: UiText,
    private val viewModelScope: CoroutineScope,
    private val app: Application
) : IModuleInstallationRepository {

    /**
     * The name of the module to install.
     * */
    private var _moduleName: String? = null

    private val _installationState: MutableStateFlow<ModuleInstallationState> =
        MutableStateFlow(ModuleInstallationState.Loading)

    override val installationStateFlow: StateFlow<ModuleInstallationState>
        get() = _installationState

    /**
     * This must be called before installModule is invoked to set the name of the module to install.
     * */
    override fun setModuleName(moduleName: String) {
        _moduleName = moduleName
    }

    override fun installModuleAndInstallationStateUpdated() {
        if (_moduleName == null)
            updateInstallationState(ModuleInstallationState.InstallError(uiText.pleaseProvideModuleNameToInstall))

        viewModelScope.launch {
            val splitInstallManager = SplitInstallManagerFactory.create(app)

            if (splitInstallManager.installedModules.contains(_moduleName)) {
                updateInstallationState(ModuleInstallationState.Installed)
            } else {
                updateInstallationState(ModuleInstallationState.Loading)

                val request = SplitInstallRequest.newBuilder()
                    .addModule(_moduleName)
                    .build()

                splitInstallManager.startInstall(request)
                    .addOnSuccessListener {
                        updateInstallationState(ModuleInstallationState.Installed)
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
    }

    /**
     * This is used to make the code more readable.
     * */
    private fun updateInstallationState(newState: ModuleInstallationState) {
        _installationState.value = newState
    }
}