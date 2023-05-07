package com.bangkit.coffee.data.source.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionDao {
    @Query("SELECT * FROM detections")
    fun observeAll(): Flow<List<LocalDetection>>

    @Query("SELECT * FROM detections")
    suspend fun getAll(): List<LocalDetection>
}