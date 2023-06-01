package com.bangkit.coffee.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bangkit.coffee.data.source.local.AppDatabase
import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.mediator.ImageDetectionRemoteMediator
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.shared.const.DEFAULT_PER_PAGE
import com.bangkit.coffee.shared.util.parse
import com.bangkit.coffee.shared.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
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
    private val database: AppDatabase
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

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    fun getPager(): Flow<PagingData<ImageDetection>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PER_PAGE),
            remoteMediator = ImageDetectionRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                database = database
            ),
            pagingSourceFactory = { localDataSource.pagingSource() }
        ).flow.mapLatest { pagingData -> pagingData.map { it.toExternal() } }
    }
}