package com.example.practiceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class],
    version = 1
)

abstract class BPDatabase: RoomDatabase() {
    abstract val dao: NoteDao
    private class BPDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dao)
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            var sample = Note("120","80","90","Normal",System.currentTimeMillis())
            noteDao.upsertNote(sample)
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: BPDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): BPDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BPDatabase::class.java,
                    "bp_database"
                )
                    .addCallback(BPDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}