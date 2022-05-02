package com.mahmoud_darwish.ui_main.favorites

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FavoritesModuleInstallationProgressViewModel @Inject constructor(
    private val IModuleInstallationRepository: IModuleInstallationRepository,
    private val app: Application
) : ViewModel() {

    init {
        IModuleInstallationRepository.setModuleName(Constants.favoritesFeatureModuleName)
    }

    val installationState: StateFlow<ModuleInstallationState> =
        IModuleInstallationRepository.installationState

    fun installModule() = IModuleInstallationRepository.installModule()

    fun goToFavorites() {
        app.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Constants.FeaturesMainActivityUriString.toUri()
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
    }
}
