package com.example.aop_part3_chaptor06.chatDetail

data class ChatModel(
    val senderId: String,
    val message: String,
){
    constructor(): this("","")
}
