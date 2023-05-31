package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.RemoteImageDetection
import com.bangkit.coffee.data.source.remote.response.ResponseWrapper
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageDetectionService {

    @Multipart
    @POST("/image-detections/create")
    suspend fun create(
        @Part file: MultipartBody.Part
    ): ResponseWrapper<RemoteImageDetection>
}