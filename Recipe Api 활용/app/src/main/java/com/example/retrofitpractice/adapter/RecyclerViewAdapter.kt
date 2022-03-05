package com.example.retrofitpractice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpractice.R
import com.example.retrofitpractice.databinding.RecyclerViewBinding
import com.example.retrofitpractice.model.Row
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val clickListener : (Row) -> Unit) : ListAdapter<Row, RecyclerViewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: RecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(row: Row) {
            binding.RCPNM.text = row.rCPNM
            binding.INFOENG.text = "열량: ${row.iNFOENG}Kcal"

            putPoster(row)


            binding.root.setOnClickListener {
                clickListener(row)
            }

        }

        private fun putPoster(row : Row) {
            val builder = Picasso.Builder(binding.ivPoster.context)
            builder.downloader(OkHttp3Downloader(binding.ivPoster.context))
            builder.build().load(row.aTTFILENOMAIN)
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
        val diffUtil = object : DiffUtil.ItemCallback<Row>() {
            override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem.rCPSEQ == newItem.rCPSEQ // 일련번호 같은지
            }

            override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem == newItem
            }

        }
    }
}