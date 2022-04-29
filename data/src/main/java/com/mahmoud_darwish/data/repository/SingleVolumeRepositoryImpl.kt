package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.mapper.toVolume
import com.mahmoud_darwish.data.util.UiText
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.ISingleVolumeRepository
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleVolumeRepositoryImpl @Inject constructor(
    private val dao: VolumeEntityDao,
    private val uiText: UiText,
) : ISingleVolumeRepository {

    private val idString: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun setVolumeId(id: String) {
        idString.value = id
    }

    override val searchResult: Flow<Resource<Volume>> = idString.transform { it ->
        emit(Resource.Loading)
        val resource: Resource<Volume> =
            if (it != null) Resource.Success(dao.getVolumeEntity(it).toVolume(), Source.CACHE)
            else Resource.Error(uiText.noResultsFoundInCacheErrorMessage)
        emit(resource)
    }
}