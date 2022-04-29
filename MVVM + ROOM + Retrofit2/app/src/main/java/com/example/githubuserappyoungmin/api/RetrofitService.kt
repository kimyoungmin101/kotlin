package com.example.githubuserappyoungmin.api

import com.example.githubuserappyoungmin.model.ModelEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {
    @GET("/search/users?q=shop")
    fun getAllTasks() : Call<ModelEntity>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
