package com.bangkit.coffee.data.repository

import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.util.exception.NetworkErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionRepository @Inject constructor(
    private val localDataSource: ImageDetectionDao,
    private val remoteDataSource: ImageDetectionService,
) {

    fun getAllStream(): Flow<Result<List<ImageDetection>>> {
        return localDataSource.observeAll().map { imageDetections ->
            Result.success(imageDetections.toExternal())
        }.catch { e -> emit(Result.failure(e)) }
    }

    fun getOneStream(id: String): Flow<Result<ImageDetection>> {
        TODO("Not yet implemented")
    }

    suspend fun getOne(id: String): Result<ImageDetection> {
        try {
            remoteDataSource.getOne(id)
        } catch (e: HttpException) {
            throw NetworkErrorException.parseException(e)
        }

        TODO("Not yet implemented")
    }
}