package com.example.practiceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Button
import android.net.Uri
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var textinstructions: TextView
    lateinit var boldtextinstructions: TextView
    lateinit var textwarningsymptoms: TextView
    private lateinit var button1: ImageButton
    private lateinit var button2: ImageButton
    private lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottomhome;

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
                R.id.bottomProfile -> {
                    startActivity(
                        Intent(applicationContext, ProfileActivity::class.java));
                    finish();
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

        textinstructions = findViewById(R.id.text_instructions)
        boldtextinstructions = findViewById(R.id.boldtext_instructions)
        textwarningsymptoms = findViewById(R.id.text_warning_symptoms)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        val physnumber = UserInfoActivity.getPhysPhone().toString()

        button1.setOnClickListener(View.OnClickListener {
            if (textinstructions.getVisibility() == View.GONE) {
                textinstructions.setVisibility(View.VISIBLE);
                button1.setImageResource(R.drawable.arrow_up);
            } else {
                textinstructions.setVisibility(View.GONE);
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

            val phone_intent = Intent(Intent.ACTION_CALL)

            // REPLACE PHONE NUMBER LATER VIA THIS NOTATION "tel: $dr_phone_number"
            phone_intent.data = Uri.parse("tel: $physnumber")
            startActivity(phone_intent)
        })
    }
}