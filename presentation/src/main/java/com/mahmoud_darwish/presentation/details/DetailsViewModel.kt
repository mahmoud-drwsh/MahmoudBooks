package com.mahmoud_darwish.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.data.util.UiText
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IFavoritesRepository
import com.mahmoud_darwish.domain.repository.IVolumeSearchRepository
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: IVolumeSearchRepository,
    private val favoritesRepo: IFavoritesRepository,
    private val uiText: UiText
) : ViewModel() {
    private lateinit var currentVolumeId: String
    lateinit var isFavorite: Flow<Boolean>


    val volumeResource: MutableStateFlow<Resource<Volume>> =
        MutableStateFlow(Resource.Loading)

    fun setId(id: String) {
        currentVolumeId = id

        isFavorite = favoritesRepo.isVolumeFavorite(currentVolumeId)

        loadVolume()
    }

    private fun loadVolume() {
        volumeResource.value = Resource.Loading

        viewModelScope.launch {
            try {
                val volumeById: Volume = repo.getVolumeById(currentVolumeId)
                volumeResource.value = Resource.Success(volumeById, Source.CACHE)
            } catch (e: Exception) {
                e.printStackTrace()

                volumeResource.value =
                    Resource.Error(uiText.theVolumeWasNotFoundErrorMessage)
            }
        }
    }

    fun toggleFavoriteStatus() = favoritesRepo.toggleFavoriteStatus(currentVolumeId)
}