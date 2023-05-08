package com.bangkit.coffee.di

import com.bangkit.coffee.data.DetectionRepository
import com.bangkit.coffee.data.DetectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDetectionRepository(repository: DetectionRepositoryImpl): DetectionRepository
}