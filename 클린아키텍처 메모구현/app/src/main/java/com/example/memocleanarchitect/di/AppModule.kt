package com.example.memocleanarchitect.di

import android.content.Context
import androidx.room.Room
import com.example.memocleanarchitect.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.prefs.Preferences
import javax.inject.Singleton


@Module
@InstallIn
object AppModule {
    @Singleton
    @Provides
    fun provideMemoDataBase(@ApplicationContext context : Context): AppDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "MEMO_DATABASE"
        ).build()

}