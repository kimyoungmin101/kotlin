package com.example.booksearchapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchViewHolder(private val binding: ItemBookPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        val author = book.authors.toString().removeSurrounding("[", "]") // List 형식이기 때문에 좌우 꺽쇠를 삭제
        val publisher = book.publisher
        val data = if (book.datetime!!.isNotEmpty()) book.datetime.substring(
            0,
            10
        ) else "" // 데이터는 Null 인경우도 있기 때문에 !

        itemView.apply {
            binding.ivArticleImage.load(book.thumbnail)
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = "$author | $publisher"
            binding.tvDatetime.text = data
        }
    }
}