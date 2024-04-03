package com.example.practiceapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction

class LowFeedbackFragment : Fragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_low_feedback, container, false)

        val backbutton = v.findViewById<Button>(R.id.backaddButtonLow)
        backbutton.setOnClickListener{
            activity?.finish()
        }

        sound_button = v.findViewById<ImageButton>(R.id.sound_button)
        media_player = MediaPlayer.create(requireContext(), R.raw.LowFeedback_N)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
            }
        )
        return v
    }

}