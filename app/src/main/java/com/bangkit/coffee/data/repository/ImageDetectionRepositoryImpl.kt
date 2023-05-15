package com.bangkit.coffee.data.repository

import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.network.ImageDetectionService
import com.bangkit.coffee.di.ApplicationScope
import com.bangkit.coffee.di.DefaultDispatcher
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.domain.entity.toExternal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionRepositoryImpl @Inject constructor(
    private val localDataSource: ImageDetectionDao,
    private val remoteDataSource: ImageDetectionService,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ImageDetectionRepository {

    override fun getAllStream(): Flow<Result<List<ImageDetection>>> {
        return localDataSource.observeAll().map { imageDetections ->
            withContext(dispatcher) {
                Result.success(imageDetections.toExternal())
            }
        }.catch { e -> emit(Result.failure(e)) }
    }

    override fun getOneStream(id: String): Flow<Result<ImageDetection>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOne(id: String): Result<ImageDetection> {
        TODO("Not yet implemented")
    }
}