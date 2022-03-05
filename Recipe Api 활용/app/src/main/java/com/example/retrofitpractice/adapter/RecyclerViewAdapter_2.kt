package com.example.retrofitpractice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpractice.R
import com.example.retrofitpractice.databinding.RecipeBoxBinding
import com.example.retrofitpractice.model.Recipe
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class RecyclerViewAdapter_2 : ListAdapter<Recipe, RecyclerViewAdapter_2.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: RecipeBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            if (recipe.recipePhoto != "") {
                putPoster(recipe)
            }

            val valueText = recipe.recipeData.dropLast(1)
            binding.recipeText.text = valueText

        }

        private fun putPoster(recipe: Recipe) {
            val builder = Picasso.Builder(binding.recipeImage.context)
            builder.downloader(OkHttp3Downloader(binding.recipeImage.context))
            builder.build().load(recipe.recipePhoto)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(binding.recipeImage)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecipeBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem // 일련번호 같은지
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

        }
    }
}