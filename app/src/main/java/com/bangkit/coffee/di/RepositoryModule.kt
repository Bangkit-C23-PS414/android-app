package com.bangkit.coffee.di

import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.data.repository.ImageDetectionRepositoryImpl
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.data.repository.UserPreferencesRepositoryImpl
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
    abstract fun bindUserPreferencesRepository(repository: UserPreferencesRepositoryImpl): UserPreferencesRepository

    @Singleton
    @Binds
    abstract fun bindImageDetectionRepository(repository: ImageDetectionRepositoryImpl): ImageDetectionRepository
}