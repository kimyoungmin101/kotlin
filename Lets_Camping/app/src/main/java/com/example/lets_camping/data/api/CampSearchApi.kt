package com.example.lets_camping.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CampSearchApi {
    @GET("v3/search/book")
    suspend fun searchCamps(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<Camp>
}
