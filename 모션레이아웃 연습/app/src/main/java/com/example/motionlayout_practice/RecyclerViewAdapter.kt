package com.example.motionlayout_practice


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.motionlayout_practice.databinding.RecyclerviewLayoutBinding
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(val onItemClicked : (Model) -> Unit) : ListAdapter<Model, RecyclerViewAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: RecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Model){
            binding.titleTextView.text = model.title
            binding.dateTextView.text = model.createAt
            binding.priceTextView.text = model.price

            if (model.imageUrl != "") {
                putPoster(model)
            }

            binding.root.setOnClickListener {
                onItemClicked(model)
            }
        }
        private fun putPoster(model: Model) {
            val builder = Picasso.Builder(binding.thumbnailImageView.context)
            builder.downloader(OkHttp3Downloader(binding.thumbnailImageView.context))
            builder.build().load(model.imageUrl)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(binding.thumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
