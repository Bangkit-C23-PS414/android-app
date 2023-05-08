package com.bangkit.coffee.data.repository

import com.bangkit.coffee.data.model.Detection
import com.bangkit.coffee.data.model.toExternal
import com.bangkit.coffee.data.source.local.dao.DetectionDao
import com.bangkit.coffee.data.source.network.DetectionService
import com.bangkit.coffee.di.ApplicationScope
import com.bangkit.coffee.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetectionRepositoryImpl @Inject constructor(
    private val localDataSource: DetectionDao,
    private val remoteDataSource: DetectionService,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : DetectionRepository {

    override fun getAllStream(): Flow<Result<List<Detection>>> {
        return localDataSource.observeAll().map { detections ->
            withContext(dispatcher) {
                Result.success(detections.toExternal())
            }
        }.catch { e -> emit(Result.failure(e)) }
    }

    override fun getOneStream(id: String): Flow<Result<Detection>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOne(id: String): Result<Detection> {
        TODO("Not yet implemented")
    }
}