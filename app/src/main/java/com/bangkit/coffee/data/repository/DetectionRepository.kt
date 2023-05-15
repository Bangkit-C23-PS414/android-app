package com.bangkit.coffee.data.repository

import com.bangkit.coffee.domain.entity.Detection
import kotlinx.coroutines.flow.Flow

interface DetectionRepository {
    fun getAllStream(): Flow<Result<List<Detection>>>

    fun getOneStream(id: String): Flow<Result<Detection>>
    suspend fun getOne(id: String): Result<Detection>
}