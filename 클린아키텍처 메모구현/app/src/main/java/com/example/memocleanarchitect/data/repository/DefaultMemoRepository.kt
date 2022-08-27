package com.example.memocleanarchitect.data.repository

import com.example.memocleanarchitect.data.db.dao.MemoDao
import com.example.memocleanarchitect.data.entity.Memo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultMemoRepository(
    private val memoDao: MemoDao,
    private val ioDispatcher : CoroutineDispatcher
) : MemoRepository {
    override suspend fun getMemoList(): List<Memo> = withContext(ioDispatcher) {
        memoDao.getAll()
    }

    override suspend fun getMemoItem(id: Long): Memo? = withContext(ioDispatcher) {
        memoDao.getById(id)
    }

    override suspend fun insertMemoItem(memo: Memo): Long = withContext(ioDispatcher) {
        memoDao.insertItem(memo)
    }

    override suspend fun updateToDoItem(memo: Memo) = withContext(ioDispatcher){
        memoDao.update(memo)
    }

    override suspend fun deleteMemoItem(id: Long) = withContext(ioDispatcher){
        memoDao.delete(id)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        memoDao.deleteAll()
    }


}