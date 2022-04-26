package com.mahmoud_darwish.domain.repository

import com.mahmoud_darwish.domain.model.SearchAndResult
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface IBooksRepository {
    fun setSearchQuery(title: String)
    val searchAndResult: Flow<Resource<SearchAndResult<List<Volume>>>>
    fun getVolumeById(id: String): Flow<Resource<Volume>>
}

