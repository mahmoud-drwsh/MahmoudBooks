package com.mahmoud_darwish.ui_main.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mahmoud_darwish.core.model.ModuleInstallationState

@Composable
fun ModuleInstallationState.progressMessage(): String = when (this) {
    ModuleInstallationState.Loading ->
        stringResource(com.mahmoud_darwish.data.R.string.module_is_being_installed)

    is ModuleInstallationState.Installed ->
        stringResource(com.mahmoud_darwish.data.R.string.module_has_been_installed)

    is ModuleInstallationState.InstallError ->
        message ?: stringResource(id = com.mahmoud_darwish.data.R.string.unknown_error_message)
}