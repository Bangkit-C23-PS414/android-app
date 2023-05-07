package com.bangkit.coffee.data.source.remote

import javax.inject.Inject

class RemoteDetectionDataSourceImpl @Inject constructor() : RemoteDetectionDataSource {
    override suspend fun getAll(): List<RemoteDetection> {
        TODO("Not yet implemented")
    }
}