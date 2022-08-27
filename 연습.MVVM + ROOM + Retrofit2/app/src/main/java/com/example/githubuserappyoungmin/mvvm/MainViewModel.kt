package com.example.githubuserappyoungmin.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserappyoungmin.model.User
import com.example.githubuserappyoungmin.model.UserDao
import com.example.githubuserappyoungmin.room.MainRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {
    fun getAllTasks(): LiveData<List<User>> {
        return mainRepository.getAllRecords()
    }

    suspend fun getUpdateTakst(user: User){
        return mainRepository.updateRecord(user)
    }

    fun makeApiCall() {
        mainRepository.makeApiCall()
    }

    fun selectUser(id : Int) : LiveData<User>{
        return mainRepository.selectUser(id)
    }

}


