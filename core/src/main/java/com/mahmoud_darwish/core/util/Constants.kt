@file:Suppress("HardCodedStringLiteral")

package com.mahmoud_darwish.core.util

object Constants {
    const val EMPTY_STRING: String = ""

    // TODO: this value exists in two different places and this violates the single source of truth principle, thus, I need to find a way to make this declared in only one place and referenced where needed by its variable.
    const val favoritesFeatureModuleName = "ui_favorites"
    const val FeaturesMainActivityUriString = "mahmoud_books://favorites"
}