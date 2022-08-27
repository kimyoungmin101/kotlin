package com.example.booksearchapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "is_end") // Json 만으로는 코틀린에서 변환 불가능
    val isEnd: Boolean?,
    @field:Json(name = "pageable_count")
    val pageableCount: Int?,
    @field:Json(name = "total_count")
    val totalCount: Int?
)