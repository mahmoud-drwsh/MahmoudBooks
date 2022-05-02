package com.mahmoud_darwish.core.model

sealed class ModuleInstallationState {
    object Loading : ModuleInstallationState()
    class InstallError(val message: String? = null) : ModuleInstallationState()
    object Installed : ModuleInstallationState()
}