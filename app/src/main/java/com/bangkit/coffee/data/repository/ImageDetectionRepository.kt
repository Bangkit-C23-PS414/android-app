package com.bangkit.coffee.data.repository

import com.bangkit.coffee.domain.entity.ImageDetection
import kotlinx.coroutines.flow.Flow

interface ImageDetectionRepository {
    fun getAllStream(): Flow<Result<List<ImageDetection>>>

    fun getOneStream(id: String): Flow<Result<ImageDetection>>
    suspend fun getOne(id: String): Result<ImageDetection>
}