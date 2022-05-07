package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    fun toggleFavoriteStatus(currentVolumeId: String)
    fun isVolumeFavorite(id: String): Flow<Boolean>
    val favorites: Flow<CachedResource<List<Volume>>>
}
