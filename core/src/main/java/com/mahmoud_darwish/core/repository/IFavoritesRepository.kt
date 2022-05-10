package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    fun toggleFavoriteStatus(currentVolumeId: String)

    /**
     * This flow is consumed by the UI layer for showing the corresponding icon to the value emitted
     * by this flow.
     * */
    fun isVolumeFavoriteFlow(id: String): Flow<Boolean>

    /**
     * This flow contains all the favorite books, which are retrieved from the local database.
     * */
    val favoritesFlow: Flow<CachedResource<List<Volume>>>
}
