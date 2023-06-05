package com.bangkit.coffee.domain.entity

import java.time.LocalDateTime

data class ImageDetection(
    val id: String,
    val email: String,
    val fileURL: String,
    val isDetected: Boolean,
    val label: String,
    val confidence: Float,
    val inferenceTime: Int,
    val createdAt: LocalDateTime,
    val detectedAt: LocalDateTime,
) {
    val cacheKey = "image-detection-$id"
}