package com.example.databindingrepositoryapp

import androidx.lifecycle.MutableLiveData

interface MyRepository {
    fun getCounter(): MutableLiveData<Int>
    fun increase()
}