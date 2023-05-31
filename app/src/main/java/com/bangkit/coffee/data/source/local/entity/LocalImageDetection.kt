package com.bangkit.coffee.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "image_detections")
data class LocalImageDetection(
    @PrimaryKey
    val id: String,
    val email: String,
    val imageUrl: String,
    val result: String?,
    val inferenceTime: Int?,
    val createdAt: Date,
    val detectedAt: Date?,
)