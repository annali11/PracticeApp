package com.example.practiceapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BP_measurements")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "systolic") val systolic: Int,
    @ColumnInfo(name = "diastolic") val diastolic: Int,
    @ColumnInfo(name = "heartRate") val heartRate: Int,
    @ColumnInfo(name = "category") val category: String,
//    @TypeConverters(DateTypeConverters::class)
    @ColumnInfo(name = "dateAdded") val dateAdded: Long
) {
    fun getFormattedDate(): String {
        return DateTypeConverters().fromTimestamp(dateAdded).toString()
    }

    override fun toString(): String {
        return "BloodPressureReading(id=$id, systolic=$systolic, diastolic=$diastolic, heartRate=$heartRate, dateAdded=${getFormattedDate()})"
    }
}
