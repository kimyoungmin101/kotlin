package com.example.aop_part4_chaptor01.service

import com.example.aop_part4_chaptor01.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/cbb293eb-e8b2-4079-ba82-472d1c0419d1")
    fun listVideos(): Call<VideoDto>
}

// Retrofi을 사용하기 위함