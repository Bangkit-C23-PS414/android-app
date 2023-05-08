package com.bangkit.coffee.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DetectionService {

    @GET("/detections")
    suspend fun getAll(): List<RemoteDetection>

    @GET("/detections/{id}")
    suspend fun getOne(
        @Path("id") id: String,
    ): RemoteDetection
}