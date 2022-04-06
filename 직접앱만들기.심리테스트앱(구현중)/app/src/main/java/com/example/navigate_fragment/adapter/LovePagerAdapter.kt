package com.example.navigate_fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.navigate_fragment.R
import com.example.navigate_fragment.data.LoveTest

class LovePagerAdapter(private val test: List<LoveTest>, val onItemClicked: (LoveTest) -> Unit) :
    RecyclerView.Adapter<LovePagerAdapter.LoveViewHolder>() {
    inner class LoveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val descriptTextView: TextView = itemView.findViewById(R.id.description)

        @SuppressLint("SetTextI18n")
        fun bind(love: LoveTest) {
            titleTextView.text = "\"${love.title}\""
            descriptTextView.text = "\"${love.description}\""

            itemView.setOnClickListener {
                onItemClicked(love)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveViewHolder {
        return LoveViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoveViewHolder, position: Int) {
        val actualPosition = position % test.size
        holder.bind(test[actualPosition])
    }

    override fun getItemCount() = Int.MAX_VALUE

}
