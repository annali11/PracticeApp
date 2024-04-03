package com.example.practiceapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.practiceapp.data.Note

data class BPState(
    val bpevents: List<Note> = emptyList(),
    val systolic: MutableState<String> = mutableStateOf(""),
    val diastolic: MutableState<String> = mutableStateOf(""),
    val category: MutableState<String> =  mutableStateOf(""),
    val heartrate: MutableState<String> = mutableStateOf("")
)