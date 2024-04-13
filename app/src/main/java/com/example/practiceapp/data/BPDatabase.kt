package com.example.practiceapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practiceapp.data.model.LoggedInUser
import com.example.practiceapp.data.model.UserDao
import com.example.practiceapp.data2.migrations.MIGRATION_1_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

@Database(
    entities = [Note::class, LoggedInUser::class],
    version = 2
)

abstract class BPDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao
//    private class BPDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            instance?.let { database ->
//                scope.launch {
//                    populateDatabase(database.noteDao())
//                }
//            }
//        }
//
//
//        suspend fun populateDatabase(noteDao: NoteDao) {
//            var sample = Note(0,120,80,90,"Normal",System.currentTimeMillis())
//            noteDao.upsertNote(sample)
//        }
//    }
//
    companion object {
        private const val TAG = "BPDatabase"
        const val DATABASE_NAME = "blood_pressure.db"

        @Volatile
        private var instance: BPDatabase? = null

        fun getInstance(context: Context): BPDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private fun buildDatabase(appContext: Context): BPDatabase {
            val filesDir = appContext.getExternalFilesDir(null)
            val dataDir = File(filesDir, "data")
            val scope = CoroutineScope(SupervisorJob())

            if (!dataDir.exists())
                dataDir.mkdir()
            val builder =
                Room.databaseBuilder(
                    appContext,
                    BPDatabase::class.java,
                    File(dataDir, DATABASE_NAME).toString()
                )
                    .addMigrations(MIGRATION_1_2).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()

            // FIXME: Remove this block of code before submitting your project.
            builder.addCallback(BPDatabaseCallback(scope))
            return builder.build()
        }

        private fun BPDatabaseCallback(scope: CoroutineScope): Callback = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "Database has been created.")
                instance?.let { database ->
                    scope.launch {
                        var noteDao = database.noteDao()

                        populateDatabase(database)
                    }
                }
            }


            suspend fun populateDatabase(db: BPDatabase) {
                Log.d(TAG, "Populating the database.")

                db.noteDao().getAll().forEach {
                    Log.d(TAG, "Deleting BP Reading: $it")
                    db.noteDao().deleteNote(it)
                }

                val sample = Note(0,120,80,90,"Normal",System.currentTimeMillis())
                db.noteDao().insertAll(sample)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "Database has been opened.")
            }
        }

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): BPDatabase {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    BPDatabase::class.java,
                    "bp_database"
                )
                    .allowMainThreadQueries()
                    .addCallback(BPDatabaseCallback(scope))
                    .build()
                instance = inst
                inst
            }
        }
    }

}