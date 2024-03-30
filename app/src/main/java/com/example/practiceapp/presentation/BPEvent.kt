package com.example.practiceapp.presentation

import com.example.practiceapp.data.Note

sealed interface BPEvent{
    data object SortBPEvents: BPEvent

    data class BPCategory(val category: String): BPEvent

    data class DeleteBP(val note: Note): BPEvent

    data class SaveBP(
        val systolic: String,
        val diastolic: String,
        val heartrate: String,
    ): BPEvent
}
