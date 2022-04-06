package com.example.livedataex

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ActionType{
    PLUS, MINUS
}

// 데이터의 변경
// 뷰모델은 데이터의 변경사항을 알려주는 라이브 데이터를 가지고 있다.
class MyNumberViewModel : ViewModel(){

    private val _mutableLiveData = MutableLiveData<Int>()

    val liveData : LiveData<Int>
        get() = _mutableLiveData

    init {
        _mutableLiveData.value = 0
    }

    fun updateValue(actionType: ActionType, input : Int){
        when(actionType){
            ActionType.PLUS ->{
                _mutableLiveData.value = _mutableLiveData.value?.plus(input)
            }
            ActionType.MINUS ->{
                _mutableLiveData.value = _mutableLiveData.value?.minus(input)
            }
        }

    }
}

