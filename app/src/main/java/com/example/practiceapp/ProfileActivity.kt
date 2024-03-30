package com.example.practiceapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.presentation.BPEvent
import com.example.practiceapp.presentation.BPState
import com.example.practiceapp.presentation.BPViewModel
import kotlin.reflect.KFunction1

//import com.example.practiceapp.CalenderUtils.daysInMonthArray
//import com.example.practiceapp.CalenderUtils.monthYearFromDate


class ProfileActivity: AppCompatActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            BPDatabase::class.java,
            "bpevents.db"
        ).build()
    }

    private val viewModel by viewModels<BPViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass: Class<T>): T {
                    return BPViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            val navController = rememberNavController()

            NavHost(navController= navController, startDestination = "BPEventsScreen") {
                composable("BPEventsScreen") {
                    BPEventsScreen(
                        state = state,
                        navController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }

    private fun BPEventsScreen(
        state: BPState,
        navController: NavHostController,
        onEvent: KFunction1<BPEvent, Unit>
    ) {
        TODO("Not yet implemented")
    }
}