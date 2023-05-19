package com.bangkit.coffee.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.data.repository.ImageDetectionRepositoryImpl
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.data.repository.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")

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