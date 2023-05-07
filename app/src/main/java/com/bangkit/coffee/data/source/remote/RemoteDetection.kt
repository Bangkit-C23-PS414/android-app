package com.bangkit.coffee.data.source.remote

import java.util.Date

data class RemoteDetection(
    val id: String,
    val userId: String,
    val imageUrl: String,
    val result: String?,
    val createdAt: Date,
    val detectedAt: Date,
)