package com.example.githubuserappyoungmin.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserappyoungmin.OnItemClick
import com.example.githubuserappyoungmin.R
import com.example.githubuserappyoungmin.databinding.RecyclerViewBinding
import com.example.githubuserappyoungmin.model.User
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(listener: OnItemClick) : ListAdapter<User, RecyclerViewAdapter.ViewHolder>(diffUtil) {
    private val mCallback = listener

    var items: List<User>? = null

    inner class ViewHolder(private val binding: RecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

            binding.title.text = user.title
            binding.url.text = user.url

            putPoster(user)
            setHartColor(user)

            binding.root.setOnClickListener {
                mCallback.intentAcitivity(user)
            }

            binding.hart.setOnClickListener {
                if(user.hart == true){
                    binding.hart.setBackgroundResource(R.drawable.ic_favorite_grey)
                }
                else{
                    binding.hart.setBackgroundResource(R.drawable.ic_favorite_pink)
                }
                mCallback.updateUser(user)

            }
        }

        private fun setHartColor(user:User){
            if(user.hart == true){
                binding.hart.setBackgroundResource(R.drawable.ic_favorite_pink)
            }
            else{
                binding.hart.setBackgroundResource(R.drawable.ic_favorite_grey)
            }
        }

        private fun putPoster(user : User) {
            val builder = Picasso.Builder(binding.photoImage.context)
            builder.downloader(OkHttp3Downloader(binding.photoImage.context))
            builder.build().load(user.avatar_url)
                .error(R.drawable.ic_launcher_background)
                .into(binding.photoImage)
        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    fun setList(items: List<User>?) {
        this.items = items
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items?.get(position)!!)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id // 일련번호 같은지
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }
}