package com.example.memocleanarchitect.data.db.dao

import androidx.room.*
import com.example.memocleanarchitect.data.entity.Memo

@Dao
interface MemoDao {
    @Query("Select * From Memo")
    suspend fun getAll() : List<Memo>

    @Query("SELECT * FROM Memo WHERE id=:id")
    suspend fun getById(id: Long): Memo?

    @Query("DELETE FROM Memo WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM Memo")
    suspend fun deleteAll()

    @Insert()
    suspend fun insertItem(memo : Memo) : Long

    @Update
    suspend fun update(memo: Memo)

}