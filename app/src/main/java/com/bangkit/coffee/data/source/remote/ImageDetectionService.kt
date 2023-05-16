package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.response.RemoteImageDetection
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageDetectionService {

    @GET("/image-detections")
    suspend fun getAll(): List<RemoteImageDetection>

    @GET("/image-detections/{id}")
    suspend fun getOne(
        @Path("id") id: String,
    ): RemoteImageDetection
}