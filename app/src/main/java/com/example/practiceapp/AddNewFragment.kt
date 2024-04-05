package com.example.practiceapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction


class AddNewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_add_new, container, false)

        val addbutton = v.findViewById<Button>(R.id.addnewButton)
        val sys = v.findViewById<EditText>(R.id.bpsystolic)
        val dia = v.findViewById<EditText>(R.id.bpdiastolic)

        addbutton.setOnClickListener {
            val highFeedback = HighFeedbackFragment()
            val lowFeedback = LowFeedbackFragment()
            val normalFeedback = NormalFeedbackFragment()
            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        if (sys.text.toString() > "90" && dia.text.toString() > "60") {
            transaction.replace(R.id.secondActivity,lowFeedback)
            transaction.commit()
        } else if (sys.text.toString() < "139" && dia.text.toString() < "89") {
            transaction.replace(R.id.secondActivity,highFeedback)
            transaction.commit()
        } else {
            transaction.replace(R.id.secondActivity,normalFeedback)
            transaction.commit()
        }
        }
        return v
    }
}

