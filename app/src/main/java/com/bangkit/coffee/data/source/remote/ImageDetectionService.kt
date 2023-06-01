package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.RemoteImageDetection
import com.bangkit.coffee.data.source.remote.response.ResponseWrapper
import com.bangkit.coffee.shared.const.DEFAULT_PER_PAGE
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageDetectionService {

    @Multipart
    @POST("/image-detections/create")
    suspend fun create(
        @Part file: MultipartBody.Part
    ): ResponseWrapper<RemoteImageDetection>

    /*@GET("/image-detections/fetch")*/
    @GET("/megatron")
    suspend fun fetch(
        @Query("after") after: String? = null,
        @Query("startDate") startDate: Long? = null,
        @Query("endDate") endDate: Long? = null,
        @Query("label") label: String? = null,
        @Query("perPage") perPage: Int = DEFAULT_PER_PAGE
    ): List<RemoteImageDetection>
}