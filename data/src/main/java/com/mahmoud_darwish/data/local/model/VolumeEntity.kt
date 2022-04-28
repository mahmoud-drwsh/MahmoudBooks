package com.mahmoud_darwish.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class VolumeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: List<String>,
    val aboutTheBook: String,
    val thumbnail: String,
    val thumbnailLarge: String,
    val price: Int,
    val pages: Int,
    val rating: Double = 0.0,
    val reviewsNumber: Int = 0,
    val categories: List<String>
)

