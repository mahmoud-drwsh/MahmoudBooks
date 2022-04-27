package com.mahmoud_darwish.domain.repository

import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ISingleVolumeRepository {
    fun setVolumeId(id: String)
    val searchResult: Flow<Resource<Volume>>
}

