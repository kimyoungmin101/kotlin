package com.example.githubuserappyoungmin.model


import com.google.gson.annotations.SerializedName

data class ModelEntity(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("total_count")
    val totalCount: Int?
)