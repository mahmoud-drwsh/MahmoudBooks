package com.mahmoud_darwish.favorites.home

import androidx.lifecycle.ViewModel
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.util.CachedResource
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel(repo: IFavoritesRepository) : ViewModel() {
    val favoritesFlow: Flow<CachedResource<List<Volume>>> = repo.favoritesFlow
}