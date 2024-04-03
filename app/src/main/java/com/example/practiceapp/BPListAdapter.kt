package com.example.practiceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.data.Note
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class BPListAdapter : ListAdapter<Note, BPListAdapter.BPViewHolder>(BPComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BPViewHolder {
        return BPViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BPViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.systolic, current.diastolic)
    }

    class BPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val bpItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(sys: String?, dia: String?) {
            bpItemView.text = sys
            bpItemView.text = dia
        }

        companion object {
            fun create(parent: ViewGroup): BPViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.bp_recyclerview_item, parent, false)
                return BPViewHolder(view)
            }
        }
    }

    class BPComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.dateAdded == newItem.dateAdded
        }
    }
}