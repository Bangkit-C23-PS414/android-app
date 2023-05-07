package com.bangkit.coffee.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalDetection::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun detectionDao(): DetectionDao
}