package com.example.aop_part3_chaptor04.api

import com.example.aop_part3_chaptor04.model.BestSellerDto
import com.example.aop_part3_chaptor04.model.SearchBooksDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {

    @GET("/api/bestSeller.api?categoryId=100&output=json")
    fun getBestSeller(@Query("key") apiKey: String): Call<BestSellerDto>

    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String
    ): Call<SearchBooksDto>
}
