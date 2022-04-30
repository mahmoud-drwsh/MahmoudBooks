package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import kotlinx.coroutines.flow.Flow

interface IFavoritesListRepository {
    val favorites: Flow<List<Volume>>
}