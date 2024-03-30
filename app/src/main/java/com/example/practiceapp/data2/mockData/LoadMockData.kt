package com.example.practiceapp.data2.mockData

import android.util.Log
import com.example.practiceapp.data2.AppDataBase
import com.example.practiceapp.data2.BloodPressureReading
import com.example.practiceapp.data2.DateTypeConverters
import com.example.practiceapp.data2.User
import java.time.LocalDateTime

class LoadMockData {
    fun load(db: AppDataBase) {
        db.userDao().getAll().forEach {
            db.userDao().delete(it)
        }
        db.bloodPressureReadingDao().getAll().forEach {
            db.bloodPressureReadingDao().delete(it)
        }
        db.userDao().insertAll(
            User(
                0,
                "John",
                "Doe",
                "jdoe@example.com",
                "password",
                "(123) 456-7890",
                DateTypeConverters().toTimestamp(LocalDateTime.now())
            ),
            User(
                0,
                "Miranda",
                "Bagar",
                "mbagar@example.com",
                "password",
                "(716) 123-4567",
                DateTypeConverters().toTimestamp(LocalDateTime.now())
            ),
            User(
                0,
                "Anna",
                "Li",
                "ali@example.com",
                "password",
                "(206) 832-5558",
                DateTypeConverters().toTimestamp(LocalDateTime.now())
            )
        )
        db.userDao().getAll().forEach() {
            insertBloodPressureReadings(db, it.userId)

            val readings = db.userDao().getUserWithBloodPressure( it.userId)
            Log.e("LoadMockData", readings.toString())
        }

    }

    private fun insertBloodPressureReadings(db: AppDataBase, userId: Int) {
        for (i in 1..2) {

            db.bloodPressureReadingDao().insertAll(
                BloodPressureReading(
                    0,
                    (120 until 140).random(),
                    (80 until 90).random(),
                    (60 until 100).random(),
                    userId,
                    DateTypeConverters().toTimestamp(LocalDateTime.now())
                )
            )
        }
    }
}
