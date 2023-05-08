package com.bangkit.coffee.data.source.network

import com.bangkit.coffee.data.source.network.response.DetectionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetectionService {

    @GET("/detections")
    suspend fun getAll(): List<DetectionResponse>

    @GET("/detections/{id}")
    suspend fun getOne(
        @Path("id") id: String,
    ): DetectionResponse
}