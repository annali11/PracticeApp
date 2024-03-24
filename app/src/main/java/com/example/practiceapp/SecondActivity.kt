package com.example.practiceapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.practiceapp.databinding.ActivitySecondBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecondActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var textinstructions: TextView
    private lateinit var boldtextinstructions: TextView
    private lateinit var textwarningsymptoms: TextView
    private lateinit var button1: ImageButton
    private lateinit var button2: ImageButton
    private lateinit var button3: Button
    //INITIALIZE LATER
    //lateinit var phone_number: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottomhome;
        textinstructions = findViewById(R.id.text_instructions)
        boldtextinstructions = findViewById(R.id.boldtext_instructions)
        textwarningsymptoms = findViewById(R.id.text_warning_symptoms)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.bottomhome -> {
                    return@setOnItemSelectedListener true
                }
                R.id.bottomAddNew -> {
                    startActivity(
                        Intent(applicationContext, AddNewActivity::class.java));
                    finish();
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

        button1.setOnClickListener(View.OnClickListener {
            if (textinstructions.getVisibility() == View.GONE) {
                textinstructions.setVisibility(View.VISIBLE);
                boldtextinstructions.setVisibility(View.VISIBLE);
                button1.setImageResource(R.drawable.arrow_up);
            } else {
                textinstructions.setVisibility(View.GONE);
                boldtextinstructions.setVisibility(View.GONE);
                button1.setImageResource(R.drawable.arrow_down);
            }
        })

        button2.setOnClickListener(View.OnClickListener {
            if (textwarningsymptoms.getVisibility() == View.GONE) {
                textwarningsymptoms.setVisibility(View.VISIBLE);
                button2.setImageResource(R.drawable.arrow_up);
            } else {
                textwarningsymptoms.setVisibility(View.GONE);
                button2.setImageResource(R.drawable.arrow_down);
            }
        })

        button3.setOnClickListener(View.OnClickListener {
            //val dr_phone_number = phone_number.text.toString()

            val phone_intent = Intent(Intent.ACTION_DIAL)

            // REPLACE PHONE NUMBER LATER VIA THIS NOTATION "tel: $dr_phone_number"
            phone_intent.data = Uri.parse("tel: 7034994045")

            //start activity
            startActivity(phone_intent)
        })

        //replaceFragment(HomeFragment())
    }



}