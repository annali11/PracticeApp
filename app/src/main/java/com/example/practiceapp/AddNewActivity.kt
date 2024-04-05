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
import com.example.practiceapp.data.Note
import com.example.practiceapp.databinding.ActivityAddNewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val patientname = UserInfoActivity.getName().toString()
        val stringsys = binding.bpsystolic.text.toString()
        val stringdia = binding.bpdiastolic.text.toString()
        val stringhr = binding.heartrate.text.toString()
        val category = ""
        var time = ""
        val array = arrayListOf<String>(patientname,stringsys,stringdia,stringhr,category,time)

        // bottomNavigationView = findViewById(R.id.bottomNavigationView)
        binding.bottomNavigationView.selectedItemId = R.id.bottomAddNew;

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.bottomhome -> {
                    startActivity(
                        Intent(applicationContext, HomeActivity::class.java)
                    );
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottomAddNew -> {
                    return@setOnItemSelectedListener true
                }
                R.id.bottomProfile -> {
                    startActivity(
                        Intent(applicationContext, ProfileActivity::class.java)
                    );
                    finish()
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
        val applicationScope = CoroutineScope(SupervisorJob())

        //addnewbutton = findViewById(R.id.addnewButton)
        val dp = BPDatabase.getDatabase(this,applicationScope)

        binding.addnewButton.setOnClickListener(View.OnClickListener {
            val datelong = System.currentTimeMillis()
            time = fromTimestamp(datelong).toString()
            val sys = stringsys.toInt()
            val dia = stringdia.toInt()
            val hr = stringhr.toInt()
            var category = "Not Available"

            if ( sys > 299 || dia > sys || hr > 500) {
                setResult(Activity.RESULT_CANCELED)
                Toast.makeText(this, "ERROR: Redo Entry", Toast.LENGTH_SHORT).show()
            } else {
                var bp = Note(0, sys, dia, hr, category, System.currentTimeMillis())
                dp.noteDao().insertAll(bp)

                if (sys >= 160 && dia >= 110) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(VeryHighFeedbackFragment())
                    category = "Very High"
                    bp = Note(0, sys, dia, hr, category, System.currentTimeMillis())
                    dp.noteDao().update(bp)
                    array[4] = category
                    array[5] = time
                    bpstring = toString(array)
                } else if (sys in 159 downTo 140 && dia in 109 downTo 90) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(HighFeedbackFragment())
                    category = "High"
                    bp = Note(0, sys, dia, hr, category, System.currentTimeMillis())
                    dp.noteDao().update(bp)
                    array[4] = category
                    array[5] = time
                    bpstring = toString(array)
                } else if (sys in 139 downTo 90 && dia in  89 downTo 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(NormalFeedbackFragment())
                    category = "Normal"
                    bp = Note(0, sys, dia, hr, category, System.currentTimeMillis())
                    dp.noteDao().update(bp)
                    array[4] = category
                    array[5] = time
                    bpstring = toString(array)
                } else if (sys < 90 && dia < 60) {
                    setResult(Activity.RESULT_OK)
                    replaceFragment(LowFeedbackFragment())
                    category = "Low"
                    bp = Note(0, sys, dia, hr, category, System.currentTimeMillis())
                    dp.noteDao().update(bp)
                    array[4] = category
                    array[5] = time
                    bpstring = toString(array)
                }
            }
        })
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun fromTimestamp(value: Long): LocalDateTime {
        return value.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it), ZoneId.systemDefault()
            )
        }
    }
    private fun toString(array: ArrayList<String>): String {
        val patientname = array[0]
        val sys = array[1]
        val dia = array[2]
        val hr = array[3]
        val cat= array[4]
        val time= array[5]
        return "BP Reading:\nId= $patientname\n$sys, $dia\nHR: $hr\nCategory: $cat\n Time: $time"
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.bplistsql.REPLY"
        var bpstring: String? = null

        fun getBPmessage(): String? {
            return bpstring
        }
    }

}