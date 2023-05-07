package com.bangkit.coffee.di

import com.bangkit.coffee.data.source.remote.RemoteDetectionDataSource
import com.bangkit.coffee.data.source.remote.RemoteDetectionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemoteDetectionDataSource(dataSource: RemoteDetectionDataSourceImpl): RemoteDetectionDataSource
}