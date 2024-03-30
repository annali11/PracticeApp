package com.example.practiceapp.data2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BloodPressureReadingDao {
    @Query("SELECT * FROM BloodPressureReading")
    fun getAll(): List<BloodPressureReading>

    @Query("SELECT * FROM BloodPressureReading WHERE userId = :userId")
    fun getByUserId(userId: Int): List<BloodPressureReading>

    @Insert
    fun insertAll(vararg readings: BloodPressureReading)

    @Delete
    fun delete(reading: BloodPressureReading)
}