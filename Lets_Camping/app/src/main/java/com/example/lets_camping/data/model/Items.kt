package com.example.lets_camping.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items(
    @Json(name = "item")
    val item: List<Item?>?
)