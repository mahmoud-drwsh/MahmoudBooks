package com.mahmoud_darwish.ui_main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val volumeSearchRepo: IVolumeSearchRepository
) : ViewModel() {

    val query: StateFlow<String> = volumeSearchRepo.queryStringFlow

    val searchResult: Flow<CachedResource<List<Volume>>> =
        volumeSearchRepo.searchResultsResourceFlow

    private var homeUiState =
        MutableStateFlow<HomeUIState<List<Volume>>>(HomeUIState(query = Constants.EMPTY_STRING))

    init {
        viewModelScope.launch {
            volumeSearchRepo.searchResultsResourceFlow
                .combine(volumeSearchRepo.queryStringFlow) { cachedResource: CachedResource<List<Volume>>, query: String ->
                    homeUiState.value = when (cachedResource) {
                        is CachedResource.Error -> homeUiState.value.copy(
                            errorMessage = cachedResource.errorMessage,
                            query = query
                        )
                        is CachedResource.Loading -> homeUiState.value.copy(
                            isLoading = true,
                            query = query
                        )
                        is CachedResource.Success -> {
                            homeUiState.value.copy(
                                isLoading = false,
                                data = cachedResource.data,
                                errorMessage = null,
                                query = query
                            )
                        }
                    }
                }
        }
    }

    fun onEvent(homeUIEvent: HomeUIEvent) = when (homeUIEvent) {
        is HomeUIEvent.UpdateQuery -> {
            volumeSearchRepo.setSearchQuery(homeUIEvent.query)
        }
        is HomeUIEvent.ForceLoadingFromServer -> volumeSearchRepo.forceLoadingFromRemoteSource()
    }
}

/**
 * This is used to model the various states of the Home UI
 * */
data class HomeUIState<T>(
    val query: String,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val data: T? = null,
)

/**
 * This is used to model the various interactions between the UI and the ViewModel
 * */
sealed class HomeUIEvent {
    data class UpdateQuery(val query: String) : HomeUIEvent()
    object ForceLoadingFromServer : HomeUIEvent()
}

