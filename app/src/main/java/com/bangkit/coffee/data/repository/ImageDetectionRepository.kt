package com.bangkit.coffee.data.repository

import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.shared.util.parse
import com.bangkit.coffee.shared.wrapper.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionRepository @Inject constructor(
    private val localDataSource: ImageDetectionDao,
    private val remoteDataSource: ImageDetectionService,
) {

    suspend fun create(file: File): Resource<ImageDetection> {
        return try {
            val response = remoteDataSource.create(
                createFormData(
                    name = "image",
                    filename = file.name,
                    body = file.asRequestBody("image/jpg".toMediaType())
                )
            )

            Resource.Success(response.data.toExternal())
        } catch (e: HttpException) {
            Resource.Error(e.parse().message)
        }
    }
}