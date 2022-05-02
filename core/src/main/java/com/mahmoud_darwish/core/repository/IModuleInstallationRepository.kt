package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.ModuleInstallationState
import kotlinx.coroutines.flow.StateFlow

interface IModuleInstallationRepository {
    fun setModuleName(moduleName: String)
    fun installModule()
    val installationState: StateFlow<ModuleInstallationState>
}