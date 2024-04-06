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

    enum class Category(val value: String) {
        VeryHigh("Very High"), High("Hign"), Normal("Normal"), Low("Low"), NotAvailable("Not Available")
    }
    data class BPData(var patientName: String, var sys: Int, var dia: Int, var hr: Int, var category: String, var time: String) {
        override fun toString(): String {
            return "BP Reading:\nId= $patientName\n$sys, $dia\nHR: $hr\nCategory: $category\n Time: $time"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val patientname = UserInfoActivity.name.toString()

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomhome -> navigateTo(HomeActivity::class.java)
                R.id.bottomProfile -> navigateTo(ProfileActivity::class.java)
                R.id.bottomSettings -> navigateTo(SettingsActivity::class.java)
            }
            true
        }
        val applicationScope = CoroutineScope(SupervisorJob())

        //addnewbutton = findViewById(R.id.addnewButton)
        val db = BPDatabase.getDatabase(this,applicationScope)
        val bpSystolicField = binding.bpsystolic
        val bpDiastolicField = binding.bpdiastolic
        val heartRateField = binding.heartrate
        val name = UserInfoActivity.name

        binding.addnewButton.setOnClickListener(View.OnClickListener {
            val now = System.currentTimeMillis()
            val nowInt = converters.fromTimestamp(now).toString()
            val systolic = bpSystolicField.text.toString().toInt()
            val diastolic = bpDiastolicField.text.toString().toInt()
            val heartRate = heartRateField.text.toString().toInt()

            val bpData = BPData(patientname, systolic, diastolic, heartRate, Category.NotAvailable.value, nowInt)
            val id: Long
            var bp: Note

            if ( systolic > 299 || diastolic > systolic || heartRate > 500) {
                setResult(Activity.RESULT_CANCELED)
                Toast.makeText(this, "ERROR: Redo Entry", Toast.LENGTH_SHORT).show()
            } else {
                bp = Note(0, systolic, diastolic, heartRate,  Category.NotAvailable.value, now)
                id = db.noteDao().insert(bp)
                if (systolic >= 160 && diastolic >= 110) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(VeryHighFeedbackFragment())
                    bpData.category = Category.VeryHigh.value
                } else if (systolic in 159 downTo 140 && diastolic in 109 downTo 90) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(HighFeedbackFragment())
                    bpData.category = Category.High.value
                } else if (systolic in 139 downTo 90 && diastolic in  89 downTo 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(NormalFeedbackFragment())
                    bpData.category = Category.Normal.value
                } else if (systolic < 90 && diastolic < 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(LowFeedbackFragment())
                    bpData.category = Category.Low.value
                }
                bp = Note(id.toInt(), systolic, diastolic, heartRate, bpData.category, now)
                db.noteDao().update(bp)
                bpstring = bpData.toString()
            }
        })
    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(applicationContext, destination))
        finish()
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