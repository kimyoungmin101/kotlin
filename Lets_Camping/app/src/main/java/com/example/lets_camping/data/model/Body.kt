package com.example.lets_camping.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Body(
    @Json(name = "items")
    val items: Items?,
    @Json(name = "numOfRows")
    val numOfRows: Int?,
    @Json(name = "pageNo")
    val pageNo: Int?,
    @Json(name = "totalCount")
    val totalCount: Int?
)