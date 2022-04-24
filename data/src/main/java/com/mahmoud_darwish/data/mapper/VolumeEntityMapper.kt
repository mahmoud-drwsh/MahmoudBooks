package com.mahmoud_darwish.data.mapper

import com.mahmoud_darwish.data.local.model.VolumeEntity
import com.mahmoud_darwish.domain.model.Volume

fun Volume.toVolumeEntity(): VolumeEntity = VolumeEntity(
    id = this.id,
    title = this.title,
    authors = this.authors,
    aboutTheBook = this.aboutTheBook,
    thumbnail = this.thumbnail,
    thumbnailLarge = this.thumbnailLarge,
    price = this.price,
    pages = this.pages,
    rating = this.rating,
    reviewsNumber = this.reviewsNumber,
)


fun VolumeEntity.toVolume(): Volume = Volume(
    id = this.id,
    title = this.title,
    authors = this.authors,
    aboutTheBook = this.aboutTheBook,
    thumbnail = this.thumbnail,
    thumbnailLarge = this.thumbnailLarge,
    price = this.price,
    pages = this.pages,
    rating = this.rating,
    reviewsNumber = this.reviewsNumber,
)











