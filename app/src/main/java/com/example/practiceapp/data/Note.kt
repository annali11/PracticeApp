package com.example.practiceapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val systolic: String,
    val diastolic: String,
    val heartrate: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 1
)
