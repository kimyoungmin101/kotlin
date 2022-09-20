package com.example.mvcmvpmvvmpattern.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvcmvpmvvmpattern.mvvm.model.Answer

class GetAnswerViewModel : ViewModel() {
    private val answer = Answer()
    private val _isSuccessFlag : MutableLiveData<Boolean> = MutableLiveData()

    val isSuccessFlag : LiveData<Boolean>
        get() = _isSuccessFlag
    val result : String
        get() = answer.result.toString()

    private fun setSuccessfulFlag(isSuccessFlag: Boolean) {
        _isSuccessFlag.postValue(isSuccessFlag)
    }

    fun getResult(result:String) {
        val isLoginSuccessful: Boolean = answer.get_result(result)
        if (isLoginSuccessful) {
            setSuccessfulFlag(true)
        } else {
            setSuccessfulFlag(false)
        }
    }

}