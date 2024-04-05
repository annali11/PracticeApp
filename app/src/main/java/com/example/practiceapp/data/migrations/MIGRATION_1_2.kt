package com.example.practiceapp.data2.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `User` (`userId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `firstName` TEXT, `lastname` TEXT, `email` TEXT, `password` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `dateAdded` INTEGER NOT NULL)")
        db.execSQL("ALTER TABLE `BloodPressureReading` ADD COLUMN `userId` INTEGER NOT NULL DEFAULT 0")
//        db.execSQL("CREATE INDEX `index_BloodPressureReading_userId` ON `BloodPressureReading` (`userId`)")
    }
}