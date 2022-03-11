package com.example.motionlayout_practice

data class Model(
    val sellerId: String,
    val title: String,
    val createAt : String,
    val price: String,
    val imageUrl: String
){
    constructor(): this("","","","","")
}
