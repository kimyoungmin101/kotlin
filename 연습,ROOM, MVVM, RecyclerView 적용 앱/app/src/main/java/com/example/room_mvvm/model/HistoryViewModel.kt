package com.example.room_mvvm.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.room_mvvm.AppDatabase
import com.example.room_mvvm.setting.SetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application){
    private val historyDao = AppDatabase.getDataBase(application).historyDao()
    private val repository : SetRepository

    var getAllTasks: LiveData<List<History>>

    init {
        repository = SetRepository(historyDao)
        getAllTasks = repository.getAlltasks()
    }


    fun insert(history: History) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(history)
        }
    }

    fun delete(history: History){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(history)
        }
    }

    fun update(history: History){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(history)
        }
    }
}
