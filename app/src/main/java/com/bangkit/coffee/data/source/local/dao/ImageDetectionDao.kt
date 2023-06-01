package com.bangkit.coffee.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.coffee.data.source.local.entity.LocalImageDetection

@Dao
interface ImageDetectionDao {

    @Query("SELECT * FROM image_detections ORDER BY createdAt DESC")
    fun pagingSource(): PagingSource<Int, LocalImageDetection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LocalImageDetection>)

    @Query("DELETE FROM image_detections")
    suspend fun deleteAll()
}