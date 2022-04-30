package com.mahmoud_darwish.domain.repository

import com.mahmoud_darwish.domain.model.Volume
import kotlinx.coroutines.flow.Flow

interface IFavoritesListRepository {
    val favorites: Flow<List<Volume>>
}