package com.bangkit.coffee.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bangkit.coffee.R
import com.bangkit.coffee.data.source.local.AppDatabase
import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.mediator.ImageDetectionRemoteMediator
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.data.source.remote.response.camera.CameraResponseData
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.domain.mapper.toLocal
import com.bangkit.coffee.shared.const.DEFAULT_PER_PAGE
import com.bangkit.coffee.shared.util.parse
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: ImageDetectionDao,
    private val remoteDataSource: ImageDetectionService,
    private val database: AppDatabase
) {

    suspend fun create(file: File): Resource<CameraResponseData> {
        return try {
            val response = remoteDataSource.create(
                MultipartBody.Part.createFormData(
                    name = "image",
                    filename = file.name,
                    body = file.asRequestBody("image/jpeg".toMediaType())
                )
            )

            Resource.Success(response.data)
        } catch (e: HttpException) {
            Resource.Error(e.parse().message)
        } catch (e: Exception) {
            Resource.Error(context.getString(R.string.generic_error_message))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(
        labels: List<String>,
        startDate: Long? = null,
        endDate: Long? = null
    ): Flow<PagingData<ImageDetection>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PER_PAGE),
            remoteMediator = ImageDetectionRemoteMediator(
                labels = labels,
                startDate = startDate,
                endDate = endDate,
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                database = database
            ),
            pagingSourceFactory = {
                localDataSource.pagingSource(
                    labels = labels,
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toExternal() }
        }
    }

    fun getOne(id: String): Flow<ImageDetection?> {
        return localDataSource.getOne(id).map { it?.toExternal() }
    }

    suspend fun refreshOne(id: String): Resource<ImageDetection> {
        return try {
            val response = remoteDataSource.getOne(id)
            val imageDetection = response.data.toExternal()

            localDataSource.insertOne(imageDetection.toLocal())

            Resource.Success(imageDetection)
        } catch (e: HttpException) {
            Resource.Error(e.parse().message)
        } catch (e: Exception) {
            Resource.Error(context.getString(R.string.generic_error_message))
        }
    }

    suspend fun deleteAll() {
        return localDataSource.deleteAll()
    }
}