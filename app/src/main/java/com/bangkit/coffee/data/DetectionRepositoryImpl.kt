package com.bangkit.coffee.data

import com.bangkit.coffee.data.source.local.DetectionDao
import com.bangkit.coffee.data.source.remote.RemoteDetectionDataSource
import com.bangkit.coffee.di.ApplicationScope
import com.bangkit.coffee.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetectionRepositoryImpl @Inject constructor(
    private val localDataSource: DetectionDao,
    private val remoteDataSource: RemoteDetectionDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : DetectionRepository {

    override fun getAllStream(): Flow<List<Detection>> {
        TODO("Not yet implemented")
    }

    override fun getOneStream(id: String): Flow<Detection> {
        TODO("Not yet implemented")
    }

    override suspend fun getOne(id: String): Detection {
        TODO("Not yet implemented")
    }
}