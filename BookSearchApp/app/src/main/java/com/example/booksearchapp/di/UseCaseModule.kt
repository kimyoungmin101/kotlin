package com.example.booksearchapp.di

import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.domain.bookTodo.GetAllBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun getAllBooksProvide(repository: BookSearchRepository) = GetAllBookUseCase(repository)
}
