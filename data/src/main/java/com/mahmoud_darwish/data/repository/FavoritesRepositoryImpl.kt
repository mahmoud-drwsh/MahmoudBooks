package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.ResponseSource
import com.mahmoud_darwish.data.local.dao.FavoriteEntityDao
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.data.local.model.VolumeEntity
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single(binds = [IFavoritesRepository::class])
class FavoritesRepositoryImpl(
    private val favoriteEntityDao: FavoriteEntityDao,
    private val appIoScope: CoroutineScope,
    private val uiText: UiText
) : IFavoritesRepository {

    override val favoritesFlow: Flow<CachedResource<List<Volume>>> = favoriteEntityDao
        .getFavoriteVolumes()
        .map { list: List<VolumeEntity> ->
            val volumeList = list.toVolumeList()

            if (volumeList.isEmpty()) CachedResource.Error(uiText.noFavoritesFound)
            else CachedResource.Success(volumeList, ResponseSource.CACHE)
        }

    override fun toggleFavoriteStatus(currentVolumeId: String) {
        appIoScope.launch {
            val isFavorite: Boolean = latestFavoriteStatus(currentVolumeId)

            if (isFavorite) {
                favoriteEntityDao.delete(currentVolumeId)
            } else {
                favoriteEntityDao.insert(Favorite(currentVolumeId))
            }
        }
    }

    private suspend fun latestFavoriteStatus(currentVolumeId: String): Boolean =
        favoriteEntityDao
            .isVolumeFavorite(currentVolumeId)
            .firstOrNull() // gets the first value emitted by the flow or null if it's empty
            ?: false // the default value is one has not been set prior

    override fun isVolumeFavoriteFlow(id: String): Flow<Boolean> =
        favoriteEntityDao.isVolumeFavorite(id)
}