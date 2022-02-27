package com.example.aoppart2chaptor4

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aoppart2chaptor4.dao.HistoryDao
import com.example.aoppart2chaptor4.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun historyDao(): HistoryDao
}