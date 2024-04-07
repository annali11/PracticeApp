package com.example.practiceapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)

    @Query("SELECT * FROM BP_measurements")
    fun getAll(): List<Note>

    @Query("SELECT * FROM BP_measurements ORDER BY dateAdded ASC LIMIT 1")
    fun getMostRecentNote(): Note

    @Insert
    fun insertAll(vararg readings: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM BP_measurements ORDER BY dateAdded")
    fun getNotesOrderedByDateAdded(): Flow<List<Note>>

}
