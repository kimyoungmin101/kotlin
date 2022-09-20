package com.example.mvcmvpmvvmpattern.mvp.presentor

import com.example.mvcmvpmvvmpattern.mvp.model.Answer

interface GetAnswer {
    val answer : Answer
    fun getAnswer()
}