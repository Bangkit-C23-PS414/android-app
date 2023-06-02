package com.bangkit.coffee.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.coffee.data.source.local.entity.LocalImageDetection
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDetectionDao {

    /* Paging functionality */
    @Query(
        "SELECT * FROM image_detections " +
                "WHERE label IN (:labels) " +
                "AND (:startDate IS NULL OR createdAt >= :startDate) " +
                "AND (:endDate IS NULL OR createdAt <= :endDate) " +
                "ORDER BY createdAt DESC"
    )
    fun pagingSource(
        labels: List<String>,
        startDate: Long? = null,
        endDate: Long? = null
    ): PagingSource<Int, LocalImageDetection>

    @Query(
        "SELECT * FROM image_detections " +
                "WHERE label IN (:labels) " +
                "AND (:startDate IS NULL OR createdAt >= :startDate) " +
                "AND (:endDate IS NULL OR createdAt <= :endDate) " +
                "ORDER BY createdAt ASC " +
                "LIMIT 1"
    )
    fun getLastItem(
        labels: List<String>,
        startDate: Long? = null,
        endDate: Long? = null
    ): LocalImageDetection?

    @Query("SELECT MIN(syncAt) FROM image_detections")
    fun getLastUpdated(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LocalImageDetection>)

    @Query("DELETE FROM image_detections")
    suspend fun deleteAll()

    // Get one
    @Query("SELECT * FROM image_detections WHERE id = :id")
    fun getOne(id: String): Flow<LocalImageDetection?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(imageDetection: LocalImageDetection)
}