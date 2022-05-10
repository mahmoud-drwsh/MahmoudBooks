package com.mahmoud_darwish.ui_main.favorites

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.mahmoud_darwish.core.model.ModuleInstallationState
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.core.util.Constants
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class FavoritesModuleInstallationScreenViewModel(
    private val moduleInstallationRepo: IModuleInstallationRepository
) : ViewModel() {

    init {
        moduleInstallationRepo.setModuleName(Constants.favoritesFeatureModuleName)
        // If you (the reviewer) want to see how the module would be installed by the user,
        // remove the line below.
        installModule()
    }

    val installationState: StateFlow<ModuleInstallationState> =
        moduleInstallationRepo.installationStateFlow

    fun installModule() = moduleInstallationRepo.installModuleAndInstallationStateUpdated()

    fun goToFavorites(context: Context) {
        val featuresLauncherActivityIntent = Intent(
            Intent.ACTION_VIEW,
            Constants.FeaturesMainActivityUriString.toUri()
        )

        context.startActivity(featuresLauncherActivityIntent)
    }
}
