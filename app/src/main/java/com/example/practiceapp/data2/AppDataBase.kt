package com.example.practiceapp.data2

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practiceapp.data2.migrations.MIGRATION_1_2
import java.io.File
import java.time.LocalDateTime

@Database(entities = [BloodPressureReading::class, User::class], version = AppDataBase.VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bloodPressureReadingDao(): BloodPressureReadingDao
    abstract fun userDao(): UserDao

    companion object {
        private const val TAG = "AppDatabase"
        const val DATABASE_NAME = "blood_pressure.db"
        const val VERSION = 1

        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(appContext: Context): AppDataBase {
            val filesDir = appContext.getExternalFilesDir(null)
            val dataDir = File(filesDir, "data")
            if (!dataDir.exists())
                dataDir.mkdir()
            val builder =
                Room.databaseBuilder(
                    appContext,
                    AppDataBase::class.java,
                    File(dataDir, DATABASE_NAME).toString()
                )
                    .addMigrations(MIGRATION_1_2).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()

            // FIXME: Remove this block of code before submitting your project.
            builder.addCallback(appDatabaseCallback())
            return builder.build()
        }

        /**
         * Creates and returns the callback object to execute when the database is first created.
         * @return The callback object to execute when the database is first created.
         */
        private fun appDatabaseCallback(): Callback = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "Database has been created.")

                // Throws exception
                //instance?.let { populateDbAsync(it) }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "Database has been opened.")
            }
        }

        private fun populateDbAsync(db: AppDataBase) {

            Log.d(TAG, "Populating the database.")

            db.userDao().getAll().forEach {
                Log.d(TAG, "Deleting user: $it")
                db.userDao().delete(it)
            }
            db.bloodPressureReadingDao().getAll().forEach {
                Log.d(TAG, "Deleting BP Reading: $it")
                db.bloodPressureReadingDao().delete(it)
            }
            Log.d(TAG, "Inserting users.")
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
                    "(312) 123-4567",
                    DateTypeConverters().toTimestamp(LocalDateTime.now())
                )
            )
            Log.d(TAG, "Inserting BP readings.")
            db.userDao().getAll().forEach() {
                insertBloodPressureReadings(db, it.userId)
                val readings = db.userDao().getUserWithBloodPressure(it.userId)
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
}
