package com.mahmoud_darwish.favorites

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesViewModel @Inject constructor(
    repo: IFavoritesRepository
) {
    val favorites: Flow<Resource<List<Volume>>> = repo.favorites
}