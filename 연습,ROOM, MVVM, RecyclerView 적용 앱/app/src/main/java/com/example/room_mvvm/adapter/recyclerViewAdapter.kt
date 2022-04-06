package com.example.room_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.room_mvvm.databinding.ListItemBinding
import com.example.room_mvvm.model.History
import com.example.room_mvvm.setting.OnItemClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class recyclerViewAdapter(listener: OnItemClick) : RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>() {

    private val mCallback = listener
    private val items = ArrayList<History>()

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songModel: History) {
            binding.title.text = songModel.songName
            binding.description.text = songModel.singerName
            binding.deleteButton.setOnClickListener {
                mCallback.deleteTodo(songModel)
            }
            binding.updateButton.setOnClickListener {
                mCallback.updateTodo(songModel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setList(history: List<History>) {
        items.clear()
        items.addAll(history)
    }


    override fun getItemCount(): Int {
        return items.size
    }

}