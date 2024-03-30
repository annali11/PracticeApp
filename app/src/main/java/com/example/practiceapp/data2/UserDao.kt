package com.example.practiceapp.data2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserById(userId: Int): User

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM User WHERE phoneNumber = :phoneNumber")
    fun getUserByPhoneNumber(phoneNumber: String): User
    
    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    fun getUserWithBloodPressure(userId: Int): UserWithBloodPressureReadings

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
