package com.example.lets_camping.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Header(
    @Json(name = "resultCode")
    val resultCode: Int?,
    @Json(name = "resultMsg")
    val resultMsg: String?
)