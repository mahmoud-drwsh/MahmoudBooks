package com.mahmoud_darwish.favorites.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mahmoud_darwish.compose_components.StatelessBooksHorizontalLazyRow
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IFavoritesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Composable
fun FavoritesFeatureScreen() {
    val viewModel: FavoritesScreenViewModel = hiltViewModel()

    val volumesList by viewModel.favorites.collectAsState(listOf())

    StatelessBooksHorizontalLazyRow(volumesList) {
        // TODO: complete!!!
    }
}

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val repo: IFavoritesListRepository
) : ViewModel() {
    val favorites: Flow<List<Volume>>
        get() = repo.favorites
}