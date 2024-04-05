package com.example.practiceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.practiceapp.databinding.ActivityAddNewBinding
import com.example.practiceapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val lanugages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this,R.layout.language_list_item, lanugages)

        binding.autoComplete.setAdapter(arrayAdapter)

    }
}