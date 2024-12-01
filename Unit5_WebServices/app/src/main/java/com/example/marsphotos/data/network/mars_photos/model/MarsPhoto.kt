package com.example.marsphotos.data.network.mars_photos.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MarsPhoto(
    val id: String,

    @SerializedName(value = "img_src")
    val image: String
)
