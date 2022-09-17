package com.example.booksearchapp.di

import android.content.Context
import androidx.room.Room
import com.example.booksearchapp.data.db.BookSearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    @Provides
    @Named("test_db") // Hilt가 객체 구분 가능하도록 Named 설정 해준거,
    fun provideInMemoryDb(@ApplicationContext context: Context): BookSearchDatabase =
        Room.inMemoryDatabaseBuilder( // Memoryㅇㅏㄴ에서만 생성 가능하도록 한 것
            context,
            BookSearchDatabase::class.java
        ).allowMainThreadQueries().build() // ANR 방지를 위해 MainThread에서의 쿼리 금지!!
    
}