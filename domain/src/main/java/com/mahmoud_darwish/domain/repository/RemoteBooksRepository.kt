package com.mahmoud_darwish.domain.repository

import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteBooksRepository {
    fun searchByTitle(title: String): Flow<Resource<List<Volume>>>
    fun getVolumeById(id: String): Flow<Resource<Volume>>
}

