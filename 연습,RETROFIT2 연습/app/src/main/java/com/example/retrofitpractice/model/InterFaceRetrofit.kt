package com.example.retrofitpractice.model

import retrofit2.Call
import retrofit2.http.GET

interface InterFaceRetrofit {
    @GET("/photos")
    fun listPhotos(): Call<List<PhotoEntity>>
}