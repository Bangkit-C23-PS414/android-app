package com.bangkit.coffee.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "image_detections")
data class LocalImageDetection(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val result: String?,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "detected_at") val detectedAt: Date,
)