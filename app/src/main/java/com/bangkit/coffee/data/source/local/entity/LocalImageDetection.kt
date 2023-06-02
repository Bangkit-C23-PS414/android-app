package com.bangkit.coffee.data.source.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "image_detections",
    indices = [Index("createdAt"), Index("label"), Index("syncAt")]
)
data class LocalImageDetection(
    @PrimaryKey
    val id: String,
    val email: String,
    val fileURL: String,
    val isDetected: Boolean,
    val label: String,
    val inferenceTime: Int,
    val createdAt: LocalDateTime,
    val detectedAt: LocalDateTime,
    val syncAt: LocalDateTime,
)