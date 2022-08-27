package com.example.githubuserappyoungmin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubuserappyoungmin.model.TypeConverterOwner
import com.example.githubuserappyoungmin.model.User
import com.example.githubuserappyoungmin.model.UserDao


@Database(entities = [User::class], version = 1)
@TypeConverters(TypeConverterOwner::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object{
        private var INSTANCE : AppDatabase ?= null

        fun getDataBase(context: Context) : AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "task_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}