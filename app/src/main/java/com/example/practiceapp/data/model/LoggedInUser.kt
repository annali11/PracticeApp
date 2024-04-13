package com.example.practiceapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

@Entity(tableName = "Users")
data class LoggedInUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user phone") val phone: String,
    @ColumnInfo(name = "physician name") val physname: String,
    @ColumnInfo(name = "physician phone") val physphone: String,
)