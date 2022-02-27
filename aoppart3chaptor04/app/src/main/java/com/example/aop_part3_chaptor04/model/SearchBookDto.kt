package com.example.aop_part3_chaptor04.model

import com.google.gson.annotations.SerializedName

data class SearchBooksDto(
    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<Book>
)
