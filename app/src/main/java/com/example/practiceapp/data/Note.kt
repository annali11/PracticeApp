package com.example.practiceapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BP_measurements")
data class Note(
    val systolic: String,
    val diastolic: String,
    val heartrate: String,
    val category: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 1
) {
}
