package com.example.practiceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.presentation.BPViewModel


class ProfileFragment : Fragment() {

    //private TextView monthYearText;
    // RecyclerView calenderRecyclerView;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun previousMonthAction(view: View) {}

    fun nextMonthAction(view: View) {
    }

}