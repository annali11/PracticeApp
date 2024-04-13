package com.example.practiceapp.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(user: LoggedInUser): Long

    @Delete
    fun delete(use: LoggedInUser)

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUser(id: Int): LoggedInUser

}