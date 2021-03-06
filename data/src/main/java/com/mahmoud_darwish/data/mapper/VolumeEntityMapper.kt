package com.mahmoud_darwish.data.mapper

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.data.local.model.VolumeEntity

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
    categories = this.categories,
    infoLink = this.infoLink,
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
    categories = this.categories,
    infoLink = this.infoLink,
)


fun List<VolumeEntity>.toVolumeList(): List<Volume> = mapNotNull { volumeEntity ->
    // a try clause is used in order to avoid crashing the app in case a volume entity can't be mapped to a volume model
    try {
        volumeEntity.toVolume()
    } catch (e: Exception) {
        null
    }
}


fun List<Volume>.toVolumeEntityList(): List<VolumeEntity> = mapNotNull { volumeEntity ->
    // a try clause is used in order to avoid crashing the app in case a volume can't be mapped to a volume entity
    try {
        volumeEntity.toVolumeEntity()
    } catch (e: Exception) {
        null
    }
}