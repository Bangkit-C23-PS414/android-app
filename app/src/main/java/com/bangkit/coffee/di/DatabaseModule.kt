package com.bangkit.coffee.di

import android.content.Context
import androidx.room.Room
import com.bangkit.coffee.data.source.local.MainDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MainDatabase::class.java,
            "MainDatabase.db"
        ).build()
    }

    @Provides
    fun provideImageDetectionDao(database: MainDatabase): ImageDetectionDao {
        return database.imageDetectionDao()
    }
}