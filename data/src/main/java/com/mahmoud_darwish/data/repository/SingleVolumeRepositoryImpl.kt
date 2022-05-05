package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.ISingleVolumeRepository
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.mapper.toVolume
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform
import org.koin.core.annotation.Single

@Single
class SingleVolumeRepositoryImpl constructor(
    private val dao: VolumeEntityDao,
    private val uiText: UiText,
) : ISingleVolumeRepository {

    private val volumeIdString: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun setVolumeId(id: String) {
        volumeIdString.value = id
    }

    override val searchResult: Flow<Resource<Volume>> = volumeIdString.transform {
        emit(Resource.Loading)

        val resource: Resource<Volume> =
            if (it != null) Resource.Success(dao.getVolumeEntity(it).toVolume(), Source.CACHE)
            else Resource.Error(uiText.noResultsFoundInCacheErrorMessage)

        emit(resource)
    }
}