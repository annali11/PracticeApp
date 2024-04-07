package com.example.practiceapp.data

import android.app.Application
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow

class BPRepository(private val noteDao: NoteDao) {

    val allbpevents: Flow<List<Note>> = noteDao.getNotesOrderedByDateAdded()

    @WorkerThread
    suspend fun upsert(note: Note) {
        noteDao.upsertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}
