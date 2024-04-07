package com.example.practiceapp.addnewfragments

import android.content.Context
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

class LowFeedbackFragment(override val context1: Context) : BaseFeedbackFragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(getLayoutId(), container, false)

//        save_button = v.findViewById<Button>(R.id.backButtonLow)

        sound_button = v.findViewById<ImageButton>(R.id.sound_button)
        media_player = MediaPlayer.create(requireContext(), R.raw.lowfeedback_n)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
            }
        )
        return v
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_low_feedback
    }
}