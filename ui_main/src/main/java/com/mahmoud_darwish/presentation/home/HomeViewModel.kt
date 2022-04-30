package com.mahmoud_darwish.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val volumeSearchRepo: IVolumeSearchRepository
) : ViewModel() {

    val query: StateFlow<String> = volumeSearchRepo.query

    val searchResult: Flow<Resource<List<Volume>>> = volumeSearchRepo.searchResult

    private var homeUiState = MutableStateFlow<HomeUIState<List<Volume>>>(HomeUIState(query = ""))

    init {
        viewModelScope.launch {
            volumeSearchRepo.searchResult
                .combine(volumeSearchRepo.query) { resource: Resource<List<Volume>>, query: String ->
                    homeUiState.value = when (resource) {
                        is Resource.Error -> homeUiState.value.copy(
                            errorMessage = resource.message,
                            query = query
                        )
                        is Resource.Loading -> homeUiState.value.copy(
                            isLoading = true,
                            query = query
                        )
                        is Resource.Success -> {
                            homeUiState.value.copy(
                                isLoading = false,
                                data = resource.data,
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

sealed class HomeUIEvent() {
    class UpdateQuery(val query: String) : HomeUIEvent()
    class ForceLoadingFromServer : HomeUIEvent()
}