package com.example.practiceapp

import android.app.Application
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.data.BPRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BPApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BPDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { BPRepository(database.noteDao()) }
}