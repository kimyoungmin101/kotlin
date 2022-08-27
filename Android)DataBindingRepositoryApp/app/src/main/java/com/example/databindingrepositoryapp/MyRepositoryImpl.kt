package com.example.databindingrepositoryapp

import androidx.lifecycle.MutableLiveData

class MyRepositoryImpl(counter : Int) : MyRepository{

    private val liveCounter = MutableLiveData<Int>(counter)

    override fun getCounter(): MutableLiveData<Int> {
        return liveCounter
    }

    override fun increase() {
        liveCounter.value = liveCounter.value?.plus(1)
    }
}