package com.bangkit.coffee.data

import java.util.Date

data class Detection(
    val id: String,
    val userId: String,
    val imageUrl: String,
    val result: String?,
    val createdAt: Date,
    val detectedAt: Date,
)
