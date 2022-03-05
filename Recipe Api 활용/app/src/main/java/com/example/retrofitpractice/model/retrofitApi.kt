package com.example.retrofitpractice.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitApi {
    private const val BASE_URL = "https://openapi.foodsafetykorea.go.kr/"


    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService : InterFaceRetrofit by lazy {
        retrofit.create(InterFaceRetrofit::class.java)
    }
}