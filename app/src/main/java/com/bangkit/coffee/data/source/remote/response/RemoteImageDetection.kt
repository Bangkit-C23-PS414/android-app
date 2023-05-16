package com.bangkit.coffee.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RemoteImageDetection(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("result")
    val result: String?,

    @field:SerializedName("createdAt")
    val createdAt: Date,

    @field:SerializedName("detectedAt")
    val detectedAt: Date
)