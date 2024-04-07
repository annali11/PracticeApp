package com.example.practiceapp.addnewfragments

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import com.example.practiceapp.AddNewActivity
import com.example.practiceapp.R
import com.example.practiceapp.UserInfoActivity


class HighFeedbackFragment(override val context1: Context) : BaseFeedbackFragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer
    //lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(getLayoutId(), container, false)

        sound_button = v.findViewById<ImageButton>(R.id.sound_button)
        media_player = MediaPlayer.create(requireContext(), R.raw.highfeedback_n)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
        })

//        save_button = v.findViewById(R.id.backButtonHigh)
//
//        save_button.setOnClickListener{
//            //This code to check if permissions are granted (used in fragment) than request them if not
//            if (activity?.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
//            {
//                activity?.requestPermissions(arrayOf(Manifest.permission.SEND_SMS), PackageManager.PERMISSION_GRANTED)
//            }
//
//            val phoneNumber = UserInfoActivity.physphone.toString()
//            val message = AddNewActivity.getBPmessage()
//
//            if (phoneNumber.isNotBlank()) {
//                btn_sendSMS_OnClick()
//            } else {
//                Toast.makeText(context, "Phone number is empty", Toast.LENGTH_LONG).show()
//            }
//        }
        return v
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_high_feedback
    }

//    override val context1: Context
//        get() = context

}