package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.data.di.AppIoCoroutineScope
import com.mahmoud_darwish.data.local.FavoriteEntityDao
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.data.local.model.VolumeEntity
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteEntityDao: FavoriteEntityDao,
    @AppIoCoroutineScope
    private val appIoScope: CoroutineScope,
    private val uiText: UiText
) : IFavoritesRepository {

    override val favorites: Flow<Resource<List<Volume>>> = favoriteEntityDao
        .getFavorites()
        .map { list: List<VolumeEntity> ->
            val volumeList = list.toVolumeList()

            if (volumeList.isEmpty()) Resource.Error(uiText.noFavoritesFound)
            else Resource.Success(volumeList, Source.CACHE)
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

    private suspend fun latestFavoriteStatus(currentVolumeId: String) =
        favoriteEntityDao
            .isFavorite(currentVolumeId)
            .firstOrNull()
            ?: false // the default value is one has not been set prior

    override fun isVolumeFavorite(id: String): Flow<Boolean> = favoriteEntityDao.isFavorite(id)
}