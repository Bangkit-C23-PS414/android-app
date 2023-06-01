package com.bangkit.coffee.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteImageDetection(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("result")
    val result: String?,

    @field:SerializedName("inferenceTime")
    val inferenceTime: Int?,

    @field:SerializedName("createdAt")
    val createdAt: Long,

    @field:SerializedName("detectedAt")
    val detectedAt: Long?,
)