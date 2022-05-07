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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.data.R.string
import com.mahmoud_darwish.ui_core.BookImage
import com.mahmoud_darwish.ui_core.ResourceComposable
import com.mahmoud_darwish.ui_core.theme.mediumPadding
import com.mahmoud_darwish.ui_core.util.priceFormatter
import com.mahmoud_darwish.ui_main.compose_nav_graph.MainUiNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@MainUiNavGraph
@Destination
@Composable
fun Details(
    navigator: DestinationsNavigator,
    volumeId: String,
    detailsViewModel: DetailsViewModel = getViewModel(),
    context: Context = LocalContext.current
) {
    detailsViewModel.initialize(volumeId)

    val isFavorite: Boolean by detailsViewModel.isFavorite.collectAsState(initial = false)

    LaunchedEffect(volumeId) {
        println(volumeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(string.book_details)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(string.navigate_back_icon_content_description)
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = {
                        detailsViewModel.onEvent(DetailsUiEvent.ToggleFavoriteStatus)
                    }) {

                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Default.Bookmark
                            else
                                Icons.Outlined.BookmarkBorder,
                            contentDescription = stringResource(string.toggle_bookmark_status_icon_content_description)
                        )
                    }
                }
            )
        }
    ) {
        val collectAsState: CachedResource<Volume> by detailsViewModel.volumeCachedResource.collectAsState(
            CachedResource.Loading
        )

        collectAsState.ResourceComposable { volume: Volume, _ ->
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
                        text = stringResource(
                            id = string.buy_with_price_idr,
                            priceFormatter.format(volume.price).toString()
                        ),
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
                        stringResource(id = string.rating_with_star, volume.rating.toString()),
                        stringResource(id = string.reviews_number, volume.reviewsNumber.toString()),
                        Modifier.weight(1f)
                    )

                    VolumeInfoRowDivider()

                    VolumeInfoRowBlock(
                        volume.pages.toString(), stringResource(string.pages), Modifier
                            .weight(1f)
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )

                Text(
                    text = stringResource(string.about_the_book),
                    style = MaterialTheme.typography.h5
                )

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

