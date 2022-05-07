package com.mahmoud_darwish.favorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mahmoud_darwish.favorites.di.FavoritesFeatureModule
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.core.context.loadKoinModules
import org.koin.ksp.generated.module

class FavoritesDynamicFeatureModuleMainActivity : ComponentActivity() {

    init {
        koinInject()
        addReferenceInCompanionObject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DestinationsNavHost(navGraph = NavGraphs.favorites)
        }
    }

    private fun koinInject() {
        loadKoinModules(
            FavoritesFeatureModule().module
        )
    }

    private fun addReferenceInCompanionObject() {
        currentRunningInstance = this
    }

    private fun removeReferenceInCompanionObject() {
        currentRunningInstance = null
    }

    /**
     * In this function, the static reference to the activity is set to null in order to avoid having
     * memory leaks taking place
     * */
    override fun onDestroy() {
        super.onDestroy()
        removeReferenceInCompanionObject()
    }

    companion object {
        /**
         * This instance static reference will be used to close the favorites activity.
         * to ensure that no memory leaks take place, this reference is set to null when the activity is destroyed.
         * */
        var currentRunningInstance: FavoritesDynamicFeatureModuleMainActivity? = null
    }
}
