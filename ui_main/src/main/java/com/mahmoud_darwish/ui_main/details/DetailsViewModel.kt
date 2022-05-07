package com.mahmoud_darwish.ui_main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.Source
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * The use of this viewModel requires setting the ID of the volume to show the details of using the initialize function before it can function as expected
 * */
@KoinViewModel
class DetailsViewModel constructor(
    private val repo: IVolumeSearchRepository,
    private val favoritesRepo: IFavoritesRepository,
    private val uiText: UiText
) : ViewModel() {
    private var currentVolumeId: String = ""

    var isFavorite: Flow<Boolean> = MutableStateFlow(false)
    val volumeCachedResource: MutableStateFlow<CachedResource<Volume>> =
        MutableStateFlow(CachedResource.Loading)

    fun initialize(id: String) {
        currentVolumeId = id

        isFavorite = favoritesRepo.isVolumeFavorite(currentVolumeId)

        loadVolume()
    }

    private fun loadVolume() {
        volumeCachedResource.value = CachedResource.Loading

        viewModelScope.launch {
            try {
                val volumeById: Volume = repo.getVolumeById(currentVolumeId)
                volumeCachedResource.value = CachedResource.Success(volumeById, Source.CACHE)
            } catch (e: Exception) {
                e.printStackTrace()

                volumeCachedResource.value =
                    CachedResource.Error(uiText.theVolumeWasNotFoundErrorMessage)
            }
        }
    }

    fun onEvent(detailsUiEvent: DetailsUiEvent) = when (detailsUiEvent) {
        DetailsUiEvent.ToggleFavoriteStatus -> favoritesRepo.toggleFavoriteStatus(currentVolumeId)
    }
}

sealed class DetailsUiEvent {
    object ToggleFavoriteStatus : DetailsUiEvent()
}