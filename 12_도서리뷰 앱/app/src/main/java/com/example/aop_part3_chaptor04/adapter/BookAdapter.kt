package com.example.aop_part3_chaptor04.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part3_chaptor04.databinding.ItemBookBinding
import com.example.aop_part3_chaptor04.model.Book

//Adapter의 역할
//데이터를 받아오고 이를 레이아웃에 직접 연결하는 함수를 실행시키는 클래스
//= ListView나 App의 특정 데이터와 RecyclerView의 view를 Bind하는 것을 도와준다

class BookAdapter(val clickListener: (Book) -> Unit) : ListAdapter<Book, BookAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(bookModel: Book) {
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description

            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)

            binding.root.setOnClickListener {
                clickListener(bookModel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            override fun areContentsTheSame(oldItem: Book, newItem: Book) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Book, newItem: Book) =
                oldItem.id == newItem.id
        }
    }

}
