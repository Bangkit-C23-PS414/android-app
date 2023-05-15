package com.bangkit.coffee.di

import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.data.repository.ImageDetectionRepositoryImpl
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
    abstract fun bindImageDetectionRepository(repository: ImageDetectionRepositoryImpl): ImageDetectionRepository
}