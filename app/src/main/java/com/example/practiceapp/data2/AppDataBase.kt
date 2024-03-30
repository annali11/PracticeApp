package com.example.practiceapp.data2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BloodPressureReading::class], version = 1)
abstract class AppDataBase: RoomDatabase()  {
    abstract fun bloodPressureReadingDao(): BloodPressureReadingDao

}
