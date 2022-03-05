package com.example.retrofitpractice.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InterFaceRetrofit {
    @GET("/api/{API_KEY}/COOKRCP01/json/1/5/RCP_NM={RCP_NM}")
    fun getString(
        @Path("API_KEY") API_KEY : String,
        @Path("RCP_NM") RCP_NM: String
    ): Call<RecipeEntity>
}