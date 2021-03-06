package com.example.truffol.network.model

import com.google.gson.annotations.SerializedName

// TODO: 30.12.20 : Improve DTO Model 
data class TruffleDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val tartufoName: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("rating") val rating: Long,
    @SerializedName("price") val price: Long
)