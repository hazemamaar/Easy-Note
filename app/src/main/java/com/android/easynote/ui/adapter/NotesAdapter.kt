package com.android.easynote.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.ItemRvNotesBinding


class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var noteList: List<NoteDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class NotesViewHolder(private val binding: ItemRvNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation")
        fun bind(item: NoteDto) {
            binding.titleTv.text = item.title
            if (item.lock != null && checkLock(item.lock.toString()))
                binding.notesDescriptionTv.text = ""
            else
                binding.notesDescriptionTv.text = item.noteText
            if (checkPin(item.pin))
                binding.pinItem.visibility = View.VISIBLE
            binding.card.setCardBackgroundColor(Color.parseColor(item.color))
            binding.card.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }

        }
    }

    private var onItemClickListener: ((NoteDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (NoteDto) -> Unit) {
        onItemClickListener = listener
    }


    private val differCallBack = object : DiffUtil.ItemCallback<NoteDto>() {
        override fun areItemsTheSame(oldItem: NoteDto, newItem: NoteDto): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: NoteDto, newItem: NoteDto): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemRvNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = noteList[position]
        holder.apply {
            bind(note)
        }
    }

    override fun getItemCount() = noteList.size


    fun checkLock(lock: String): Boolean {
        return lock.length >= 4
    }

    fun checkPin(pin: Int): Boolean {
        return pin >= 1
    }


}