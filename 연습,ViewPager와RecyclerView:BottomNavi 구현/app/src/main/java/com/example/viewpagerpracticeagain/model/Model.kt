package com.example.viewpagerpracticeagain.model

data class Model(
    val sellerId: String,
    val title: String,
    val createAt : String,
    val price: String,
    val imageUrl: String
){
    constructor(): this("","","","","")
}