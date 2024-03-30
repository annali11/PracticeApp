package com.example.practiceapp.data2

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithBloodPressureReadings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val bloodPressureReadings: List<BloodPressureReading>
)
