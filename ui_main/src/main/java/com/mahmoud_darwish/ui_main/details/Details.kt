package com.mahmoud_darwish.ui_main.details

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
import com.mahmoud_darwish.ui_core.BookImage
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.Resource
import com.mahmoud_darwish.core.util.Source
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.DecimalFormat

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

        collectAsState.ResourceComposable { volume: Volume, _: Source, _: String? ->
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
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BookImage(
                        imageUrl = volume.thumbnailLarge,
                        modifier = Modifier.weight(1f),
                        ContentScale.FillWidth
                    )
                    Column(
                        horizontalAlignment = Start,
                        verticalArrangement = spacedBy(mediumPadding),
                        modifier = Modifier
                            .weight(1.5f),
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

