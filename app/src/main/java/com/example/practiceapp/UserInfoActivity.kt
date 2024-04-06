package com.example.practiceapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practiceapp.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phone = binding.userphone
        phone.text = SignInActivity.getValue()
        binding.buttonsaveinfo.setOnClickListener {
            // Update the fields in the companion object before navigating to the SettingsActivity
            Companion.name = binding.nameinput.text.toString()
            Companion.physname = binding.physname.text.toString()
            Companion.physphone = binding.physphone.text.toString()

            intent = Intent(this@UserInfoActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Retrieve the updated values from the companion object
        binding.nameinput.setText(name)
        binding.physname.setText(physname)
        binding.physphone.setText(physphone)
    }

    companion object {
        var name: String? = null
        var physname: String? = null
        var physphone: String? = null
    }
}