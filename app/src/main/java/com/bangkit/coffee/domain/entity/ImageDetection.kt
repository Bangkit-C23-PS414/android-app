package com.bangkit.coffee.domain.entity

import java.time.LocalDateTime

data class ImageDetection(
    val id: String,
    val email: String,
    val imageUrl: String,
    val result: String?,
    val inferenceTime: Int?,
    val createdAt: LocalDateTime,
    val detectedAt: LocalDateTime?,
)
