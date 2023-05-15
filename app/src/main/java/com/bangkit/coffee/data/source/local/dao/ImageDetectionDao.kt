package com.bangkit.coffee.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bangkit.coffee.data.source.local.entity.LocalImageDetection
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDetectionDao {
    @Query("SELECT * FROM image_detections")
    fun observeAll(): Flow<List<LocalImageDetection>>

    @Query("SELECT * FROM image_detections")
    suspend fun getAll(): List<LocalImageDetection>
}