package com.example.practiceapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.data.BPRepository
import com.example.practiceapp.data.Note
import com.example.practiceapp.data.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis

class BPViewModel(private val repository: BPRepository):ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    val allbpevents: LiveData<List<Note>> = repository.allbpevents.asLiveData()

    fun upsert(note: Note) = viewModelScope.launch {
        repository.upsert(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

//    private var bpevents =
//        isSortedByDateAdded.flatMapLatest { sort ->
//            repository.getNotesOrderedByDateAdded()
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    val _state = MutableStateFlow(BPState())
//    val state = combine(_state, isSortedByDateAdded, bpevents) { state, isSortedByDateAdded, bpevents ->
//            state.copy(
//                bpevents = bpevents
//            )
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BPState())
//
//    fun onEvent(event: BPEvent) {
//        when (event) {
//            is BPEvent.DeleteBP -> {
//                viewModelScope.launch {
//                    dao.deleteNote(event.note)
//                }
//            }
//
//            is BPEvent.SaveBP -> {
//                val note = Note(
//                        systolic  = state.value.systolic.value,
//                        diastolic = state.value.diastolic.value,
//                        heartrate = state.value.heartrate.value,
//                        category = state.value.category.value,
//                        dateAdded = currentTimeMillis()
//                )
//
//                viewModelScope.launch {
//                    dao.upsertNote(note)
//                }
//
//                _state.update {
//                    it.copy(
//                        systolic = mutableStateOf(""),
//                        diastolic = mutableStateOf(""),
//                        heartrate = mutableStateOf(""),
//                        category = mutableStateOf("")
//                    )
//                }
//
//            }
//
//            BPEvent.SortBPEvents -> {
//                isSortedByDateAdded.value = !isSortedByDateAdded.value
//            }
//
//            else -> {}
//        }
//    }
}

class BPViewModelFactory(private val repository: BPRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BPViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BPViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}