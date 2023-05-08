package com.bangkit.coffee.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bangkit.coffee.data.source.local.entity.DetectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionDao {
    @Query("SELECT * FROM detections")
    fun observeAll(): Flow<List<DetectionEntity>>

    @Query("SELECT * FROM detections")
    suspend fun getAll(): List<DetectionEntity>
}