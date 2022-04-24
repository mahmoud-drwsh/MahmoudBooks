package com.mahmoud_darwish.domain.model

data class Volume(
    val id: String,
    val title: String,
    val authors: List<String>,
    val aboutTheBook: String,
    val thumbnail: String,
    val thumbnailLarge: String,
    val price: Int,
    val pages: Int,
    val rating: Double = 0.0,
    val reviewsNumber: Int = 0
)