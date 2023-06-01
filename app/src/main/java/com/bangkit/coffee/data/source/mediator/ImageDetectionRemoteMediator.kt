package com.bangkit.coffee.data.source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bangkit.coffee.data.source.local.AppDatabase
import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import com.bangkit.coffee.data.source.local.entity.LocalImageDetection
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.domain.mapper.toLocal
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageDetectionRemoteMediator @Inject constructor(
    val labels: List<String>,
    val startDate: Long? = null,
    val endDate: Long? = null,
    private val localDataSource: ImageDetectionDao,
    private val remoteDataSource: ImageDetectionService,
    private val database: AppDatabase
) : RemoteMediator<Int, LocalImageDetection>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalImageDetection>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: database.withTransaction {
                        localDataSource.getLastItem(labels, startDate, endDate)
                    }

                    lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Fetch API
            val response = remoteDataSource.fetch(
                after = loadKey,
                startDate = startDate,
                endDate = endDate,
                labels = labels,
                perPage = state.config.pageSize
            )
            // Convert result
            val data = response.toExternal()
            // Save result
            database.withTransaction {
                // Delete
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAll()
                }

                // Save
                localDataSource.insertAll(data.toLocal())
            }

            MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}