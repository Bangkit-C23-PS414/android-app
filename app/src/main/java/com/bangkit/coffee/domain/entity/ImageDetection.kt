package com.bangkit.coffee.domain.entity

import java.util.Date

data class ImageDetection(
    val id: String,
    val userId: String,
    val imageUrl: String,
    val result: String?,
    val createdAt: Date,
    val detectedAt: Date,
)