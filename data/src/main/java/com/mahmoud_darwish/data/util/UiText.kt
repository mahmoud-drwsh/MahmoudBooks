package com.mahmoud_darwish.data.util

import android.app.Application
import com.mahmoud_darwish.data.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiText @Inject constructor(app: Application) {

    val initialSearchTerm: String = app.getString(R.string.initial_search_term)

    val unknownErrorMessage: String = app.getString(R.string.unknown_error_message)

    val noResultsFoundError: String = app.getString(R.string.no_results_found_error)

    val searchTermNotEnteredErrorMessage: String =
        app.getString(R.string.search_term_not_entered_error_message)

    val noResultsFoundInCacheErrorMessage: String =
        app.getString(R.string.no_results_found_in_cache_error_message)
}