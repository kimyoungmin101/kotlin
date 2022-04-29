package com.example.navigate_fragment.adapter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigate_fragment.R
import com.example.navigate_fragment.data.AnswerModel
import com.example.navigate_fragment.databinding.AnswerItemBinding


class AnswerAdapter(val onItemClicked : (AnswerModel) -> Unit) : ListAdapter<AnswerModel, AnswerAdapter.ViewHolder>(diffUtil){
    inner class ViewHolder(private val binding: AnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AnswerModel, index : Int){
            binding.answerTextView.text = "${index+1}. ${model.answertext}"

            binding.root.setOnClickListener {
                onItemClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AnswerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position], position)
    }


    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<AnswerModel>(){
            override fun areItemsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean {
                return oldItem.answertext == newItem.answertext
            }

            override fun areContentsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}
