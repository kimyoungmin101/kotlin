package com.example.aop_part3_chaptor07


import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/be684b50-6883-4ca3-816d-64c7980c0738")
    fun getHouseList(): Call<HouseDto>
}