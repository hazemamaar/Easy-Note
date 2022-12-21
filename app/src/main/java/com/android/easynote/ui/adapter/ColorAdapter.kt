package com.android.easynote.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.easynote.R
import com.android.easynote.databinding.ColorRvItemBinding

val DRAWABLE_LIST = intArrayOf(R.drawable.notes_color_blue,
    R.drawable.notes_color_brown,
    R.drawable.notes_color_cyan,
    R.drawable.notes_color_green,
    R.drawable.notes_color_indigo,
    R.drawable.notes_color_orange,
    R.drawable.notes_color_purple,
    R.drawable.notes_color_red,
    R.drawable.notes_color_yellow
)

val COLOR_LIST  = arrayListOf(
    "#2196f3",
    "#5D4037",
    "#00e5ff",
    "#00c853",
    "#303F9F",
    "#ff6d00",
    "#aa00ff",
    "#d50000",
    "#ffeb3b"
)
class ColorAdapter(val context: Context) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(private val binding: ColorRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Int,position: Int){
            binding.circleView.setBackgroundResource(item)
            binding.fNoteColor.setOnClickListener {
                onItemClickListener?.let { it(COLOR_LIST[position]) }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
             ColorRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = DRAWABLE_LIST[position]
        holder.apply {
            bind(color,position)
        }
    }

    override fun getItemCount() = DRAWABLE_LIST.size

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}