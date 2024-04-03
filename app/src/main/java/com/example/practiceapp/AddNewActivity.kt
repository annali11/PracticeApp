package com.example.practiceapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.example.practiceapp.data.BPDatabase
import com.example.practiceapp.databinding.ActivityAddNewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding
//    private lateinit var bottomNavigationView: BottomNavigationView
//    private lateinit var addnewbutton: Button
//    private lateinit var heartrate: EditText
//    private lateinit var bpsystolic: EditText
//    private lateinit var bpdiastolic: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stringsys = binding.bpsystolic.text.toString()
        val stringdia = binding.bpdiastolic.text.toString()
        val stringhr = binding.heartrate.text.toString()
        val category = ""
        val array = arrayListOf<String>(stringsys,stringdia,stringhr,category)

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

                    val intent = Intent(applicationContext, AddNewActivity::class.java);
                    intent.putExtra("measurement", array);
                    startActivity(intent);
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottomProfile -> {
                    startActivity(
                        Intent(applicationContext, SecondActivity::class.java)
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

        //addnewbutton = findViewById(R.id.addnewButton)

        binding.addnewButton.setOnClickListener(View.OnClickListener {
            val sys = stringsys.toInt()
            val dia = stringdia.toInt()
            val hr = stringhr.toInt()
            val i = Intent(this,ProfileActivity::class.java)


            if ( sys > 299 || dia > sys || hr > 500) {
                setResult(Activity.RESULT_CANCELED)
                Toast.makeText(this, "ERROR: Redo Entry", Toast.LENGTH_SHORT).show()
            } else if (sys >= 160 && dia >= 110) {
                setResult(Activity.RESULT_OK)
                replaceFragment(VeryHighFeedbackFragment())
                array[3] = "Very High"
                i.putExtra("array",array);
                startActivity(i)
            } else if (sys in 159 downTo 140 && dia in 109 downTo 90) {
                setResult(Activity.RESULT_OK)
                replaceFragment(HighFeedbackFragment())
                array[3] = "High"
                i.putExtra("array",array);
                startActivity(i)
            } else if (sys in 139 downTo 90 && dia in  89 downTo 60) {
                setResult(Activity.RESULT_OK)
                replaceFragment(NormalFeedbackFragment())
                array[3] = "Normal"
                i.putExtra("array",array);
                startActivity(i)
            } else if (sys < 90 && dia < 60) {
                setResult(Activity.RESULT_OK)
                replaceFragment(LowFeedbackFragment())
                array[3] = "Low"
                i.putExtra("array",array);
                startActivity(i)
            }
            finish()
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
    }

}