package com.example.room_mvvm.setting

import androidx.lifecycle.LiveData
import com.example.room_mvvm.dao.HistoryDao
import com.example.room_mvvm.model.History

class SetRepository(val historyDao : HistoryDao) {
    suspend fun insert(history: History) = historyDao.insert(history)

    suspend fun update(history: History) = historyDao.update(history)

    suspend fun delete(history: History) = historyDao.delete(history)

    fun getAlltasks() : LiveData<List<History>> = historyDao.getAll()
}