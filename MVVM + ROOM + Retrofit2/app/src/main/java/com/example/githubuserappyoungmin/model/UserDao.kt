package com.example.githubuserappyoungmin.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao{
    @Query("SELECT * FROM User")
    fun getAllRecords(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    fun selectUser(id : Int) : LiveData<User>

    @Update
    fun update(taskEntry: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(taskEntry: User)

    @Query("DELETE FROM User")
    suspend fun deleteAllRecords()
}

