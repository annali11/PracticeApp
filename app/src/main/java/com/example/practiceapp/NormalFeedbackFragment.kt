package com.example.practiceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.practiceapp.databinding.FragmentNormalFeedbackBinding

class NormalFeedbackFragment : Fragment() {

    private var _binding: FragmentNormalFeedbackBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNormalFeedbackBinding.inflate(inflater, container, false)

        binding.backaddButtonNormal.setOnClickListener {
            findNavController().navigate(R.id.action_normalFeedbackFragment_to_addNewActivity)
        }
        return binding.root
    }


}