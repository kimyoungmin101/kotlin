package com.example.retrofitpractice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpractice.R
import com.example.retrofitpractice.databinding.RecyclerViewBinding
import com.example.retrofitpractice.model.PhotoEntity
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class RecyclerViewAdapter : ListAdapter<PhotoEntity, RecyclerViewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: RecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoentity: PhotoEntity) {
            binding.tvText.text = photoentity.title
            binding.tvId.text = photoentity.title.toString()


            val builder = Picasso.Builder(binding.ivPoster.context)
            builder.downloader(OkHttp3Downloader(binding.ivPoster.context))
            builder.build().load(photoentity.url)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivPoster)
            binding.progressBar.setVisibility(View.GONE)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PhotoEntity>() {
            override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}