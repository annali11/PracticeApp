package com.example.practiceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import com.example.practiceapp.data2.AppDataBase
import com.example.practiceapp.data2.BloodPressureReading
import com.example.practiceapp.databinding.ActivityAddNewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addnewbutton: Button
    private lateinit var heartrate: EditText
    private lateinit var bpsystolic: EditText
    private lateinit var bpdiastolic: EditText
    private var userid: Int = USER_ID_NOT_SET


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
//        val navController = navHostFragment.navController

        userid = intent.getIntExtra(USER_ID, USER_ID_NOT_SET)

        // bottomNavigationView = findViewById(R.id.bottomNavigationView)
        binding.bottomNavigationView.selectedItemId = R.id.bottomAddNew;

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.bottomhome -> {
                    startActivity(
                        Intent(applicationContext, SecondActivity::class.java)
                    );
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottomAddNew -> {
                    val stringsys = binding.bpsystolic.text.toString()
                    val stringdia = binding.bpdiastolic.text.toString()
                    val stringhr = binding.heartrate.text.toString()
                    val array = arrayListOf<String>(stringsys,stringdia,stringhr)

                    val intent = Intent(applicationContext, AddNewActivity::class.java);
                    intent.putExtra("measurement", array);
                    startActivity(intent);
                    finish()
                    return@setOnItemSelectedListener true
                }
                //R.id.bottomProfile -> {
                //    replaceFragment(ProfileFragment())
                //    true
                //}
                R.id.bottomSettings -> {
                    startActivity(
                        Intent(applicationContext, SettingsActivity::class.java));
                    finish();
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }

        //addnewbutton = findViewById(R.id.addnewButton)
        val dp = AppDataBase.getInstance(this)

        binding.addnewButton.setOnClickListener(View.OnClickListener {
            val stringsys = binding.bpsystolic.text.toString()
            val sys = stringsys.toInt()
            val stringdia = binding.bpdiastolic.text.toString()
            val dia = stringdia.toInt()
            val stringhr = binding.heartrate.text.toString()
            val hr = stringhr.toInt()


            val bp = BloodPressureReading(0, sys, dia, hr, userid, System.currentTimeMillis())
            dp.bloodPressureReadingDao().insertAll(bp)
            Log.d("Add New BP Button Click", dp.bloodPressureReadingDao().getByUserId(userid).toString())
            dp.userDao().getUserWithBloodPressure(userid)?.toString()
                ?.let { it1 -> Log.d("Add New BP Get All User", it1) }

            if ( sys > 299 || dia > sys || hr > 500) {
                Toast.makeText(this, "ERROR: Redo Entry", Toast.LENGTH_SHORT).show()
            } else if (sys >= 160 && dia >= 110) {
                replaceFragment(VeryHighFeedbackFragment())
            } else if (sys in 159 downTo 140 && dia in 109 downTo 90) {
                replaceFragment(HighFeedbackFragment())
            } else if (sys in 139 downTo 90 && dia in  89 downTo 60) {
                replaceFragment(NormalFeedbackFragment())
            } else if (sys < 90 && dia < 60) {
                replaceFragment(LowFeedbackFragment())
            }
        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}