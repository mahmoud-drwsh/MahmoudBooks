package com.mahmoud_darwish.favorites

import androidx.lifecycle.ViewModel
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.util.Resource
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel constructor(
    repo: IFavoritesRepository
) : ViewModel() {
    val favorites: Flow<Resource<List<Volume>>> = repo.favorites
}