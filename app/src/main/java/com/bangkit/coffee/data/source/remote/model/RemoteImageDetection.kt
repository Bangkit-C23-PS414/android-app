package com.bangkit.coffee.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteImageDetection(
    @field:SerializedName("filename")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("fileURL")
    val fileURL: String,

    @field:SerializedName("isDetected")
    val isDetected: Boolean,

    @field:SerializedName("label")
    val label: String,

    @field:SerializedName("accuracy")
    val accuracy: Float,

    @field:SerializedName("inferenceTime")
    val inferenceTime: Int,

    @field:SerializedName("createdAt")
    val createdAt: Long,

    @field:SerializedName("detectedAt")
    val detectedAt: Long,
)