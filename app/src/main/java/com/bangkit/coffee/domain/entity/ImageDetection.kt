package com.bangkit.coffee.domain.entity

import java.util.Date

data class ImageDetection(
    val id: String,
    val email: String,
    val imageUrl: String,
    val result: String?,
    val inferenceTime: Int?,
    val createdAt: Date,
    val detectedAt: Date?,
)
