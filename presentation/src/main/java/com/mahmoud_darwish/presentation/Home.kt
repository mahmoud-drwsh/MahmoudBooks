package com.mahmoud_darwish.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IBooksRepository
import com.mahmoud_darwish.domain.util.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class VM @Inject constructor(
    private val books: IBooksRepository
) : ViewModel() {
    val s: Flow<Resource<List<Volume>>> = books.searchByTitle("kotlin")
}


@Destination(start = true)
@Composable
fun Home(
    navigator: DestinationsNavigator,
    vm: VM = hiltViewModel()
) {
    val books by vm.s.collectAsState(initial = Resource.Loading)

    when (books) {
        is Resource.Error -> CenteredText(((books as Resource.Error).message))
        Resource.Loading -> CenteredText(message = "Loading")
        is Resource.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    items((books as Resource.Success<out List<Volume>>).data) {
                        Text(text = it.title)
                        Divider(thickness = 5.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun CenteredText(message: String) = CenteredContent {
    Text(text = message)
}

@Composable
private fun CenteredContent(
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = content,
    )
}

@Destination
@Composable
fun Details(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = name)
    }
}