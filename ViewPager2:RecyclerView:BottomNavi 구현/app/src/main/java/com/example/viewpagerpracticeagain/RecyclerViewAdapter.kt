package com.example.viewpagerpracticeagain


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewpagerpracticeagain.databinding.ItemArticleBinding
import com.example.viewpagerpracticeagain.model.Model

class RecyclerViewAdapter(val onItemClicked : (Model) -> Unit) : ListAdapter<Model, RecyclerViewAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(model: Model){
                binding.titleTextView.text = model.title
                binding.dateTextView.text = model.createAt
                binding.priceTextView.text = model.price

                if (model.imageUrl.isNotEmpty()) {
                    Glide.with(binding.thumbnailImageView)
                        .load(model.imageUrl)
                        .into(binding.thumbnailImageView)
                }

                binding.root.setOnClickListener {
                    onItemClicked(model)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }


    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Model>(){
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem.createAt == newItem.createAt
            }

            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem == newItem
            }

        }
    }

}