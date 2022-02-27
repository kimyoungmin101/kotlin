package com.example.aop_part4_chaptor02.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part4_chaptor02.R
import com.example.aop_part4_chaptor02.model.MusicModel

class PlayListAdapter(private val callback: (MusicModel) -> Unit): androidx.recyclerview.widget.ListAdapter<MusicModel, PlayListAdapter.ViewHolder>(diffutill) {
    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        fun bind(item: MusicModel){
            val trackTextView = view.findViewById<TextView>(R.id.itemTrackTextView)
            val artistTextView = view.findViewById<TextView>(R.id.itemArtistTextView)
            val coverImageView = view.findViewById<ImageView>(R.id.itemCoverImageView)

            trackTextView.text = item.track
            artistTextView.text = item.artist

            Glide.with(coverImageView.context)
                .load(item.coverUrl)
                .into(coverImageView)

            if (item.isPlaying) {
                itemView.setBackgroundColor(Color.GRAY)
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                callback(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].also { musicModel ->
            holder.bind(musicModel)
        }
    }

    companion object{
        val diffutill = object : DiffUtil.ItemCallback<MusicModel>(){
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}