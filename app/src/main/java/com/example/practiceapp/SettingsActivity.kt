package com.example.practiceapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.practiceapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.bottomSettings

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
                    val intent = Intent(applicationContext, AddNewActivity::class.java);
                    startActivity(intent)
                    finish()
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
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }

        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this,R.layout.language_list_item, languages)

        binding.autoComplete.setAdapter(arrayAdapter)

        binding.patientphone.text = SignInActivity.getValue()
        binding.patientname.text = UserInfoActivity.getName()
        binding.physname.text = UserInfoActivity.getPhysName()
        binding.physphone.text = UserInfoActivity.getPhysPhone()
    }
}