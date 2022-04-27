package com.mahmoud_darwish.domain.repository

import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IVolumeSearchRepository {
    fun setSearchQuery(title: String)
    val searchResult: Flow<Resource<List<Volume>>>
    val query: StateFlow<String>
    fun getVolumeById(id: String): Flow<Resource<Volume>>
}