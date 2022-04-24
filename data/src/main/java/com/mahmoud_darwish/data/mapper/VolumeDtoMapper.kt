package com.mahmoud_darwish.data.local.model

import com.mahmoud_darwish.data.remote.model.VolumeDto
import com.mahmoud_darwish.domain.model.Volume

/*
* Here we have only just a mapper from a DTO to a domain model and not the other way around as well because we'll not need in this app to map a domain model to a DTO.
* */

fun VolumeDto.toVolume(): Volume = Volume(
    id = this.id,
    title = volumeInfo.title,
    authors = volumeInfo.authors,
    aboutTheBook = volumeInfo.description,
    thumbnail = volumeInfo.imageLinks.smallThumbnail,
    thumbnailLarge = volumeInfo.imageLinks.thumbnail,
    price = saleInfo.retailPrice.amount,
    pages = volumeInfo.pageCount,
    rating = volumeInfo.averageRating,
    reviewsNumber = volumeInfo.ratingsCount,
)