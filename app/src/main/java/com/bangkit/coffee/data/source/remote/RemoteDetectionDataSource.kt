package com.bangkit.coffee.data.source.remote

interface RemoteDetectionDataSource {
    suspend fun getAll(): List<RemoteDetection>
}