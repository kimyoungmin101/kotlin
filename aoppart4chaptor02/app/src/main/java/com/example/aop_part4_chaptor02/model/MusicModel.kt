package com.example.aop_part4_chaptor02.model

data class MusicModel(
    val id : Long,
    val track : String,
    val streamUrl : String,
    val artist : String,
    val coverUrl : String,
    val isPlaying : Boolean = false
)
