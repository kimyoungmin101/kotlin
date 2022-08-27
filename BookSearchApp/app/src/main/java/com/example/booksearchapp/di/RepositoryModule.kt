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
    @Binds
    abstract fun bindBookSearchRepository(
        bookSearchRespositoryImp: BookSearchRespositoryImp
    ): BookSearchRepository
}