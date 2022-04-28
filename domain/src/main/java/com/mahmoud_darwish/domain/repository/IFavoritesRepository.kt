package com.mahmoud_darwish.domain.repository

import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    fun toggleFavoriteStatus(currentVolumeId: String)
    fun isVolumeFavorite(id: String): Flow<Boolean>
}
