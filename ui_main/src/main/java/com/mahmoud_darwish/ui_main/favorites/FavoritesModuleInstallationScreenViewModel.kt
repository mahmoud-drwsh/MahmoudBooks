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
class FavoritesModuleInstallationScreenViewModel constructor(
    private val IModuleInstallationRepository: IModuleInstallationRepository
) : ViewModel() {

    init {
        IModuleInstallationRepository.setModuleName(Constants.favoritesFeatureModuleName)
        installModule() // TODO: remove to make the user install it manually
    }

    val installationState: StateFlow<ModuleInstallationState> =
        IModuleInstallationRepository.installationState

    fun installModule() = IModuleInstallationRepository.installModule()

    fun goToFavorites(context: Context) {
        val featuresLauncherActivityIntent = Intent(
            Intent.ACTION_VIEW,
            Constants.FeaturesMainActivityUriString.toUri()
        )

        context.startActivity(featuresLauncherActivityIntent)
    }
}
