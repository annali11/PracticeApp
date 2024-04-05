package com.example.practiceapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practiceapp.addnewfragments.HighFeedbackFragment
import com.example.practiceapp.addnewfragments.LowFeedbackFragment
import com.example.practiceapp.addnewfragments.NormalFeedbackFragment
import com.example.practiceapp.addnewfragments.VeryHighFeedbackFragment
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.data.DateTypeConverters
import com.example.practiceapp.data.Note
import com.example.practiceapp.databinding.ActivityAddNewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding
    private val converters = DateTypeConverters()
    data class BPData(var patientname: String, var sys: Int, var dia: Int, var hr: Int, var category: String, var time: String) {
        override fun toString(): String {
            return "BP Reading:\nId= $patientname\n$sys, $dia\nHR: $hr\nCategory: $category\n Time: $time"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val patientname= UserInfoActivity.name.toString()

        // bottomNavigationView = findViewById(R.id.bottomNavigationView)
        binding.bottomNavigationView.selectedItemId = R.id.bottomAddNew

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.bottomhome -> {
                    startActivity(
                        Intent(applicationContext, HomeActivity::class.java)
                    )
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottomAddNew -> {
                    return@setOnItemSelectedListener true
                }
                R.id.bottomProfile -> {
                    startActivity(
                        Intent(applicationContext, ProfileActivity::class.java)
                    )
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottomSettings -> {
                    startActivity(
                        Intent(applicationContext, SettingsActivity::class.java))
                    finish()
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
        val applicationScope = CoroutineScope(SupervisorJob())

        //addnewbutton = findViewById(R.id.addnewButton)
        val db = BPDatabase.getDatabase(this,applicationScope)
        val bpSystolicField = binding.bpsystolic
        val bpDiasolicField = binding.bpdiastolic
        val heartRateField = binding.heartrate

        binding.addnewButton.setOnClickListener(View.OnClickListener {
            val now = System.currentTimeMillis()
            val nowInt = converters.fromTimestamp(now).toString()
            val systolic = bpSystolicField.text.toString().toInt()
            val diastolic = bpDiasolicField.text.toString().toInt()
            val heartRate = heartRateField.text.toString().toInt()

            var category = "Not Available"
            val bpData = BPData(patientname, systolic, diastolic, heartRate, category, nowInt)

            if ( systolic > 299 || diastolic > systolic || heartRate > 500) {
                setResult(Activity.RESULT_CANCELED)
                Toast.makeText(this, "ERROR: Redo Entry", Toast.LENGTH_SHORT).show()
            } else {
                var bp = Note(0, systolic, diastolic, heartRate, category, now)
                db.noteDao().insertAll(bp)

                if (systolic >= 160 && diastolic >= 110) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(VeryHighFeedbackFragment())
                    bpData.category = "Very High"
                } else if (systolic in 159 downTo 140 && diastolic in 109 downTo 90) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(HighFeedbackFragment())
                    bpData.category = "High"
                } else if (systolic in 139 downTo 90 && diastolic in  89 downTo 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(NormalFeedbackFragment())
                    bpData.category = "Normal"
                } else if (systolic < 90 && diastolic < 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(LowFeedbackFragment())
                    bpData.category = "Low"
                }
                bp = Note(0, systolic, diastolic, heartRate, category, now)
                db.noteDao().update(bp)
                bpstring = bpData.toString()
            }
        })
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.bplistsql.REPLY"
        var bpstring: String? = null

        fun getBPmessage(): String? {
            return bpstring
        }
    }
}