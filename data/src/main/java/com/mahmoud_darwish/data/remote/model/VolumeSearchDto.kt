package com.mahmoud_darwish.data.remote.model


import com.google.gson.annotations.SerializedName

data class VolumeSearchDto(
    @SerializedName("items")
    val items: List<VolumeDto> = listOf(),
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("totalItems")
    val totalItems: Int = 0
)