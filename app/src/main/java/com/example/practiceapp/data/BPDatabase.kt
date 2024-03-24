package com.example.practiceapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
)

abstract class BPDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}