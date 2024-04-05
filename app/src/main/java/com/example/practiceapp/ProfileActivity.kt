package com.example.practiceapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.room.Room
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.presentation.BPViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.ArrayList

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
}