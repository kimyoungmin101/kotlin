package com.example.booksearchapp.di

import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.data.repository.BookSearchRespositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds // BookSearchRepository는 인터페이스이기 때문에 Binds를 사용해서 힐트가 의존성 객체를 생성할 수 있게해줌
    abstract fun bindBookSearchRepository(
        bookSearchRespositoryImp: BookSearchRespositoryImp
    ): BookSearchRepository
}