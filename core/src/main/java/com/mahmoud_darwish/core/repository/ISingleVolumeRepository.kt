package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ISingleVolumeRepository {
    fun setVolumeId(id: String)
    val searchResult: Flow<Resource<Volume>>
}

