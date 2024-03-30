package com.example.practiceapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.practiceapp.data2.AppDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var textinstructions: TextView
    lateinit var boldtextinstructions: TextView
    lateinit var textwarningsymptoms: TextView
    private lateinit var button1: ImageButton
    private lateinit var button2: ImageButton
    private lateinit var button3: Button
    private lateinit var viewModel: SignInActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[SignInActivityViewModel::class.java]

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

        val userId = intent.getIntExtra(USER_ID, USER_ID_NOT_SET)
        if ( userId != USER_ID_NOT_SET) {
            val db = AppDataBase.getInstance(this)
            viewModel.updateUser(db.userDao().getUserById(userId))
        }
        val firstName = findViewById(R.id.text_welcome) as TextView
        firstName.text = "Welcome, ${viewModel.user.value?.firstName}"


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
            phone_intent.data = Uri.parse("tel: 2068325558")
            startActivity(phone_intent)
        })
    }
}