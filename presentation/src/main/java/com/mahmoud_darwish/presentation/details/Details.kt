package com.mahmoud_darwish.presentation.details

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud_darwish.compose_components.ResourceComposable
import com.mahmoud_darwish.compose_components.theme.mediumPadding
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IFavoritesRepository
import com.mahmoud_darwish.domain.repository.IVolumeSearchRepository
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import com.mahmoud_darwish.compose_components.BookImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

private val priceFormatter = DecimalFormat.getInstance()

@Destination
@Composable
fun Details(
    navigator: DestinationsNavigator,
    volumeId: String,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    detailsViewModel.setId(volumeId)

    val isFavorite: Boolean by detailsViewModel.isFavorite.collectAsState(initial = false)

    LaunchedEffect(volumeId) {
        println(volumeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Book Details") },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "navigate back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { detailsViewModel.toggleFavoriteStatus() }) {

                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Default.Bookmark
                            else
                                Icons.Outlined.BookmarkBorder,
                            contentDescription = "toggle bookmark status"
                        )
                    }
                }
            )
        }
    ) {
        val collectAsState: Resource<Volume> by detailsViewModel.volumeResource.collectAsState(
            Resource.Loading
        )
        collectAsState.ResourceComposable() { volume: Volume, source: Source, message: String? ->
            Column(
                Modifier
                    .padding(it)
                    .padding(mediumPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = spacedBy(mediumPadding),
            ) {
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = spacedBy(mediumPadding),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BookImage(
                        imageUrl = volume.thumbnailLarge,
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        ContentScale.FillWidth
                    )
                    Column(
                        horizontalAlignment = Start,
                        verticalArrangement = spacedBy(mediumPadding),
                        modifier = Modifier.weight(1.5f),
                    ) {
                        Text(
                            text = volume.title,
                            style = MaterialTheme.typography.h6,
                            overflow = TextOverflow.Visible
                        )
                        Text(
                            text = volume.authors.joinToString(", "),
                            style = MaterialTheme.typography.subtitle1,
                            overflow = TextOverflow.Visible
                        )
                    }
                }

                Button(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = volume.infoLink.toUri()
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Buy ${priceFormatter.format(volume.price)} IDR",
                        style = MaterialTheme.typography.h6
                    )
                }

                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    VolumeInfoRowBlock(
                        "${volume.rating} ★", "${volume.reviewsNumber} reviews", Modifier
                            .weight(1f)
                    )

                    VolumeInfoRowDivider()

                    VolumeInfoRowBlock(
                        "${volume.pages}", "Pages", Modifier
                            .weight(1f)
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )

                Text(text = "About the book", style = MaterialTheme.typography.h5)

                Text(
                    text = AnnotatedString(volume.aboutTheBook),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun VolumeInfoRowDivider() = Divider(
    modifier = Modifier
        .height(64.dp)
        .width(1.dp),
    color = Color.LightGray
)

@Composable
private fun VolumeInfoRowBlock(topText: String, bottomText: String, modifier: Modifier) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(text = topText, style = MaterialTheme.typography.body1)
        Text(text = bottomText, style = MaterialTheme.typography.body1)
    }
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: IVolumeSearchRepository,
    private val favoritesRepo: IFavoritesRepository
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

                volumeResource.value = Resource.Error("The volume was not found")
            }
        }
    }

    fun toggleFavoriteStatus() = favoritesRepo.toggleFavoriteStatus(currentVolumeId)
}
