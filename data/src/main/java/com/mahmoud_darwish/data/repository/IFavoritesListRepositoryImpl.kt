package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.FavoriteEntityDao
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IFavoritesListRepositoryImpl @Inject constructor(
    dao: FavoriteEntityDao,
) : IFavoritesListRepository {
    override val favorites: Flow<List<Volume>> = dao
        .getFavorites()
        .map { it.toVolumeList() }
}