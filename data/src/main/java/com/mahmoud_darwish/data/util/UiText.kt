package com.mahmoud_darwish.data.util

import android.app.Application
import androidx.annotation.StringRes
import com.mahmoud_darwish.data.R
import org.koin.core.annotation.Single

@Single
class UiText(val app: Application) {
    private fun getStringForId(@StringRes id: Int): String = app.getString(id)

    val pleaseProvideModuleNameToInstall: String
        get() = getStringForId(R.string.provide_module_name_to_install_error_message)
    val noFavoritesFound: String
        get() = getStringForId(R.string.no_favorites_found_error_message)
    val theVolumeWasNotFoundErrorMessage: String
        get() = getStringForId(R.string.the_volume_was_not_found_error_message)
    val initialSearchTerm: String
        get() = getStringForId(R.string.initial_search_term)
    val unknownErrorMessage: String
        get() = getStringForId(R.string.unknown_error_message)
    val noResultsFoundError: String
        get() = getStringForId(R.string.no_results_found_error)
    val searchTermNotEnteredErrorMessage: String
        get() = getStringForId(R.string.search_term_not_entered_error_message)
}