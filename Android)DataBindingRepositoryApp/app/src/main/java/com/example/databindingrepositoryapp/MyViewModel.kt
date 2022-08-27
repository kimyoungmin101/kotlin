package com.example.databindingrepositoryapp

import androidx.lifecycle.*

class MyViewModel(
    private val myRepositoryImpl : MyRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(){
    val countFromRepository : MutableLiveData<Int> = myRepositoryImpl.getCounter()

    fun increaseCounter(){
        myRepositoryImpl.increase()
    }

    val hasChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    companion object{
        private const val SAVE_STATE_KEY = "counter"
    }

//    var counter : Int = savedStateHandle.get<Int>(SAVE_STATE_KEY) ?: _counter
//
//    val liveCounter : MutableLiveData<Int> = MutableLiveData(_counter)

//    val modifiedCounter : LiveData<String> = Transformations.map(liveCounter) {
//        counter -> "${counter} 입니다"
//    }



//    fun saveState(){
//        savedStateHandle.set(SAVE_STATE_KEY, counter)
//    }

}