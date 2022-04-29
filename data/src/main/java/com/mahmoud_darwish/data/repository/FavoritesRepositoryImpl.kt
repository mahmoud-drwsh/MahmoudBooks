package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.di.AppIOCoroutineScope
import com.mahmoud_darwish.data.local.FavoriteEntityDao
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.domain.repository.IFavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteEntityDao,
    @AppIOCoroutineScope
    private val coroutineScope: CoroutineScope
) : IFavoritesRepository {
    override fun toggleFavoriteStatus(currentVolumeId: String) {
        coroutineScope.launch {
            val isFavorite: Boolean = latestIsFavoriteValue(currentVolumeId)

            if (isFavorite) {
                dao.delete(currentVolumeId)
            } else {
                dao.insert(Favorite(currentVolumeId))
            }
        }
    }

    private suspend fun latestIsFavoriteValue(currentVolumeId: String) =
        dao.isFavorite(currentVolumeId).take(1).firstOrNull() ?: false

    override fun isVolumeFavorite(id: String): Flow<Boolean> = dao.isFavorite(id)
}