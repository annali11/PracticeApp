package com.example.practiceapp.addnewfragments

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.practiceapp.R

class VeryHighFeedbackFragment : Fragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_very_high_feedbac, container, false)

        val backbutton = v.findViewById<Button>(R.id.backButtonVHigh)
        backbutton.setOnClickListener{
            //This code to check if permissions are granted (used in fragment) than request them if not
            if (activity?.checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.requestPermissions(
                    arrayOf(android.Manifest.permission.RECEIVE_SMS,
                        android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.READ_SMS), PackageManager.PERMISSION_GRANTED
                )
            }
            activity?.finish()
        }

        val sound_button = v.findViewById<ImageButton>(R.id.sound_button)
        val media_player = MediaPlayer.create(requireContext(), R.raw.veryhighfeedback_n)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
        }

        )

        return v
    }



}