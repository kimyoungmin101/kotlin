package com.example.memocleanarchitect.data.repository

import com.example.memocleanarchitect.data.entity.Memo

interface MemoRepository {
    suspend fun getMemoList() : List<Memo>

    suspend fun getMemoItem(id : Long) : Memo?

    suspend fun insertMemoItem(memo: Memo) : Long

    suspend fun updateToDoItem(memo: Memo)

    suspend fun deleteMemoItem(id: Long)

    suspend fun deleteAll()
}