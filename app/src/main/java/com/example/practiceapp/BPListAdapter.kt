package com.example.practiceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.data.Note

class BPListAdapter(private val onClick: (Note) -> Unit) : ListAdapter<Note, BPListAdapter.BPViewHolder>(BPComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BPViewHolder {
        return BPViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: BPViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.systolic.toString(), current.diastolic.toString(), current.getFormattedDate())
    }

    class BPViewHolder(itemView: View, val onClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val bpItemView: TextView = itemView.findViewById(R.id.textView)
        private var currentNote: Note? = null

        init {
            itemView.setOnClickListener {
                currentNote?.let { onClick(it) }
            }
        }

        fun bind(sys: String?, dia: String?, date: String?) {
            bpItemView.text = "$sys / $dia \n\n $date"
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Note) -> Unit): BPViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.bp_recyclerview_item, parent, false)
                return BPViewHolder(view, onClick)
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