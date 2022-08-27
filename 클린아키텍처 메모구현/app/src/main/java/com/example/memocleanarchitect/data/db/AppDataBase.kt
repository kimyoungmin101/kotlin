package com.example.memocleanarchitect.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memocleanarchitect.data.db.dao.MemoDao
import com.example.memocleanarchitect.data.entity.Memo


@Database(
    entities = [Memo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun memoDao() : MemoDao
}