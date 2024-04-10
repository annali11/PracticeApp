package com.example.practiceapp.addnewfragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.practiceapp.R
import java.util.Locale

class VeryHighFeedbackFragment(override val context1: Context) : BaseFeedbackFragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer
    //lateinit var context: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(getLayoutId(), container, false)

//        save_button = v.findViewById<Button>(R.id.backButtonVHigh)

        sound_button = v.findViewById<ImageButton>(R.id.sound_button)

        val language = Locale.getDefault().language

        val audioClip = when (language) {
            "en" -> R.raw.veryhighfeedback_en
            "yo-rNG" -> R.raw.veryhighfeedback_n
            else -> R.raw.veryhighfeedback_en
        }

        media_player = MediaPlayer.create(requireContext(), audioClip)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
        })

        return v
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_very_high_feedbac
    }


}