package com.example.practiceapp.data2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BloodPressureReading(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "systolic") val systolic: Int,
    @ColumnInfo(name = "diastolic") val diastolic: Int,
    @ColumnInfo(name = "heartRate") val heartRate: Int,
//    @TypeConverters(DateTypeConverters::class)
    @ColumnInfo(name = "dateAdded") val dateAdded: Long
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(): String {
        return DateTypeConverters().fromTimestamp(dateAdded).toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun toString(): String {
        return "BloodPressureReading(id=$id, systolic=$systolic, diastolic=$diastolic, heartRate=$heartRate, dateAdded=${getFormattedDate()})"
        }
}
