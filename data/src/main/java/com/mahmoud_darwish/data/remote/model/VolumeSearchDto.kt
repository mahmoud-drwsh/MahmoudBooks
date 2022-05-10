package com.mahmoud_darwish.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * Even though there are many unused properties here, I left them purposefully so that later I would have them when needed.
 * */
data class VolumeSearchDto(
    @SerializedName("items")
    val items: List<VolumeDto> = listOf(),
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("totalItems")
    val totalItems: Int = 0
)