package com.mahmoud_darwish.ui_main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.data.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The use of this viewModel requires setting the ID of the volume to show the details of using the initialize function before it can function as expected
 * */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: IVolumeSearchRepository,
    private val favoritesRepo: IFavoritesRepository,
    private val uiText: UiText
) : ViewModel() {
    private var currentVolumeId: String = ""

    var isFavorite: Flow<Boolean> = MutableStateFlow(false)
    val volumeResource: MutableStateFlow<Resource<Volume>> = MutableStateFlow(Resource.Loading)

    fun initialize(id: String) {
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