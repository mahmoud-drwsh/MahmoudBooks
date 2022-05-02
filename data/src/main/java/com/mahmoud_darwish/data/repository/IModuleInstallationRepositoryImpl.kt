package com.mahmoud_darwish.data.repository

import android.app.Application
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.data.di.AppIoCoroutineScope
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IModuleInstallationRepositoryImpl @Inject constructor(
    private val uiText: UiText,
    @AppIoCoroutineScope
    private val viewModelScope: CoroutineScope,
    private val app: Application
) : IModuleInstallationRepository {

    private var _moduleName: String? = null

    private val _installationState: MutableStateFlow<ModuleInstallationState> =
        MutableStateFlow(ModuleInstallationState.Loading)

    override val installationState: StateFlow<ModuleInstallationState>
        get() = _installationState

    override fun setModuleName(moduleName: String) {
        _moduleName = moduleName
    }

    override fun installModule() {
        if (_moduleName == null)
            updateInstallationState(ModuleInstallationState.InstallError("Please provide a module name to install"))

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

    fun updateInstallationState(newState: ModuleInstallationState) {
        _installationState.value = newState
    }
}