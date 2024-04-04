package com.example.practiceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.practiceapp.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding

//    lateinit var userName: TextView
//    lateinit var userPhone: TextView
//    lateinit var physName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.nameinput.text.toString()
        physname = binding.physname.text.toString()
        physphone = binding.physphone.text.toString()

        val phone = binding.userphone
        phone.setText(
            SignInActivity.getValue()
        )

        binding.buttonsaveinfo.setOnClickListener(View.OnClickListener {
            intent = Intent(this@UserInfoActivity, SettingsActivity::class.java)
            startActivity(intent)
        })
    }

    companion object {
        var name: String? = null
        var physname: String? = null
        var physphone: String? = null
        fun getName(): String? {
            return name
        }
        fun getPhysName(): String? {
            return physname
        }

        fun getPhysPhone(): String? {
            return physphone
        }
    }
}