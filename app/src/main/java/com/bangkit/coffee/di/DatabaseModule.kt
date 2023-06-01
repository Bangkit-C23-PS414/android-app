package com.bangkit.coffee.di

import android.content.Context
import androidx.room.Room
import com.bangkit.coffee.data.source.local.AppDatabase
import com.bangkit.coffee.data.source.local.dao.ImageDetectionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "AppDatabase.db"
        ).build()
    }

    @Provides
    fun provideImageDetectionDao(database: AppDatabase): ImageDetectionDao {
        return database.imageDetectionDao()
    }
}