package com.android.easynote.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.ItemRvNotesBinding
import com.android.easynote.domain.GetNotesModel

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var noteList: List<GetNotesModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    inner class NotesViewHolder(private val binding: ItemRvNotesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:GetNotesModel){
         binding.titleTv.text=item.title
            binding.notesDescriptionTv.text=item.noteText
            binding.card.setCardBackgroundColor(Color.parseColor(item.color))
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<GetNotesModel>() {
        override fun areItemsTheSame(oldItem: GetNotesModel, newItem: GetNotesModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: GetNotesModel, newItem: GetNotesModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemRvNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    private var onItemClickListener: ((GetNotesModel) -> Unit)? = null

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note=noteList[position]
        holder.apply {
            bind(note)
        }
    }

    override fun getItemCount() = noteList.size

    fun setOnItemClickListener(listener: (GetNotesModel) -> Unit) {
        onItemClickListener = listener
    }


}