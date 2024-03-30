package com.example.practiceapp.data2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ProvidedTypeConverter
class DateTypeConverters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return value.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it), ZoneId.systemDefault()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toTimestamp(date: LocalDateTime): Long {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
