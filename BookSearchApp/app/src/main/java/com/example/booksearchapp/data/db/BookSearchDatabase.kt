package com.example.booksearchapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booksearchapp.data.model.Book

@Database(
    entities = [Book::class], version = 1, exportSchema = false
)
@TypeConverters(OrmConverter::class)
abstract class BookSearchDatabase : RoomDatabase() {

    abstract fun bookSearchDao(): BookSearchDao

    companion object {
        @Volatile
        private var INSTANCE: BookSearchDatabase? = null

        private fun buildDatabse(context: Context): BookSearchDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                BookSearchDatabase::class.java,
                "favorite-books"
            ).build()

        fun getInstacne(context: Context): BookSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabse(context).also { INSTANCE = it }
            }
    }
}