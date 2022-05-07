package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IVolumeSearchRepository {
    fun setSearchQuery(title: String)
    val searchResult: Flow<CachedResource<List<Volume>>>
    val query: StateFlow<String>
    suspend fun getVolumeById(id: String): Volume
    fun forceLoadingFromServer()
}