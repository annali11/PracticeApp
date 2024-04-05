package com.example.practiceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.view.View.OnClickListener
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_settings, container, false)

        val items = listOf<String>("English","Hausa")

        val autoComplete : AutoCompleteTextView = v.findViewById(R.id.auto_complete)

        //val adapter = ArrayAdapter<String>(getActivity(), R.layout.language_list_item)

        //autoComplete.setAdapter(adapter)

        //autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
        //        adapter, view, i, l ->
//
        //    val itemSelected = AdapterView.
        //    Toast.makeText(getActivity(),"Language: $itemSelected",Toast.LENGTH_SHORT).show()
        //}
    return v
    }

}