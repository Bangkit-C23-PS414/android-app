package com.bangkit.coffee.data

import kotlinx.coroutines.flow.Flow

interface DetectionRepository {
    fun getAllStream(): Flow<List<Detection>>

    fun getOneStream(id: String): Flow<Detection>
    suspend fun getOne(id: String): Detection
}