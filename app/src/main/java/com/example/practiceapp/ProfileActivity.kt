package com.example.practiceapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.data.Note
import com.example.practiceapp.presentation.BPViewModel
import com.example.practiceapp.presentation.BPViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

//import com.example.practiceapp.CalenderUtils.daysInMonthArray
//import com.example.practiceapp.CalenderUtils.monthYearFromDate


class ProfileActivity: AppCompatActivity() {
//    private val database by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            BPDatabase::class.java,
//            "bpevents.db"
//        ).build()
//    }
//
//    private val viewModel by viewModels<BPViewModel> (
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun<T: ViewModel> create(modelClass: Class<T>): T {
//                    return BPViewModel(database.dao) as T
//                }
//            }
//        }
//    )
    private lateinit var bottomNavigationView: BottomNavigationView

    private val bpViewModel: BPViewModel by viewModels {
        BPViewModelFactory((application as BPApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottomProfile;

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.bottomhome -> {
                    startActivity(
                        Intent(applicationContext, HomeActivity::class.java));
                    finish();
                    return@setOnItemSelectedListener true
                }
                R.id.bottomAddNew -> {
                    startActivity(
                        Intent(applicationContext, AddNewActivity::class.java));
                    finish();
                    return@setOnItemSelectedListener true
                }
                R.id.bottomProfile -> {
                    return@setOnItemSelectedListener true
                }
                R.id.bottomSettings -> {
                    startActivity(
                        Intent(applicationContext, SettingsActivity::class.java));
                    finish();
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.bpRecyclerView)
        val adapter = BPListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        bpViewModel.allbpevents.observe(this) { notes ->
            // Update the cached copy of the words in the adapter.
            notes.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AddNewActivity::class.java)
            startActivity(intent)
        }
    }

    private val newBPActivityRequestCode = 1
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newBPActivityRequestCode && requestCode == Activity.RESULT_OK) {
            data?.getStringArrayExtra(AddNewActivity.EXTRA_REPLY)?.let { reply ->
                val bpevent = Note(0,reply[0].toInt(),reply[1].toInt(),reply[2].toInt(),reply[3],System.currentTimeMillis())
                bpViewModel.upsert(bpevent)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}