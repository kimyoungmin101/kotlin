package com.example.booksearchapp.data.api

import com.example.booksearchapp.data.model.SearchResponse
import com.example.booksearchapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookSearchApi { // Restrofit에 필요한 Service , Api Key를 받아 api 요청하는 서비스
    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<SearchResponse>
}