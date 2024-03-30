package com.example.practiceapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.data.Note
import com.example.practiceapp.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BPViewModel(
    private val dao:NoteDao
):ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    private var bpevents =
        isSortedByDateAdded.flatMapLatest { sort ->
            if (sort) {
                dao.getNotesOrderedByDateAdded()
            } else {
                dao.getNotes()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(BPState())
    val state = combine(_state, isSortedByDateAdded, bpevents) { state, isSortedByDateAdded, bpevents ->
            state.copy(
                bpevents = bpevents
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BPState())

    fun onEvent(event: BPEvent) {
        when (event) {
            is BPEvent.DeleteBP -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }

            is BPEvent.SaveBP -> {
                val note = Note(
                        systolic = state.value.systolic.value,
                        diastolic  = state.value.diastolic.value,
                        heartrate = state.value.heartrate.value,
                        dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                        systolic = mutableStateOf(""),
                        diastolic = mutableStateOf(""),
                        heartrate = mutableStateOf("")
                    )
                }

            }
            BPEvent.SortBPEvents -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value

            }
            else -> {
                // this is the BPCategory event
                throw IllegalArgumentException("Event $event is not recognized.")
            }
        }
    }
}