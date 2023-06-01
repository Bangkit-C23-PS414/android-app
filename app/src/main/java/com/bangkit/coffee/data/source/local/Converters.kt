package com.bangkit.coffee.data.source.local

import androidx.room.TypeConverter
import com.bangkit.coffee.shared.util.toEpochMilli
import com.bangkit.coffee.shared.util.toLocalDateTime
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochMilli()
    }
}