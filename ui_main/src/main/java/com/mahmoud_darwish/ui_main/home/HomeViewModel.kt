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
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@KoinViewModel
class HomeViewModel constructor(
    private val volumeSearchRepo: IVolumeSearchRepository
) : ViewModel() {

    val query: StateFlow<String> = volumeSearchRepo.query

    val searchResult: Flow<CachedResource<List<Volume>>> = volumeSearchRepo.searchResult

    private var homeUiState = MutableStateFlow<HomeUIState<List<Volume>>>(HomeUIState(query = Constants.EMPTY_STRING))

    init {
        viewModelScope.launch {
            volumeSearchRepo.searchResult
                .combine(volumeSearchRepo.query) { cachedResource: CachedResource<List<Volume>>, query: String ->
                    homeUiState.value = when (cachedResource) {
                        is CachedResource.Error -> homeUiState.value.copy(
                            errorMessage = cachedResource.message,
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
        is HomeUIEvent.ForceLoadingFromServer -> volumeSearchRepo.forceLoadingFromServer()
    }
}

data class HomeUIState<T>(
    val query: String,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val data: T? = null,
)

sealed class HomeUIEvent {
    data class UpdateQuery(val query: String) : HomeUIEvent()
    object ForceLoadingFromServer : HomeUIEvent()
}

@Module
@ComponentScan("com.mahmoud_darwish.ui_main")
class MainUiModule