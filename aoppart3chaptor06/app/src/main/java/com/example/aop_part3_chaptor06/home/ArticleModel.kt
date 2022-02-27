package com.example.aop_part3_chaptor06.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createAt : Long,
    val price: String,
    val imageUrl: String
){
    constructor(): this("","",0,"","")
}
