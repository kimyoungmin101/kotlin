package com.example.room_mvvm.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room_mvvm.model.History

@Dao
interface HistoryDao{
    @Delete
    suspend fun delete(taskEntry: History)

    @Update
    suspend fun update(taskEntry: History)

    @Insert
    suspend fun insert(taskEntry: History)

    @Query("DELETE FROM History")
    suspend fun deleteAll()

    @Query("SELECT * FROM History")
    fun getAll(): LiveData<List<History>>

}