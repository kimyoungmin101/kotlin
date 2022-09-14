package com.example.booksearchapp.di

import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.domain.bookTodo.DeleteBookUseCase
import com.example.booksearchapp.domain.bookTodo.GetAllFavoriteBookUseCase
import com.example.booksearchapp.domain.bookTodo.SaveBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun getAllBooksProvide(repository: BookSearchRepository) =
        GetAllFavoriteBookUseCase(repository).getFavoritePagingBooks()


    @Provides
    @Singleton
    suspend fun saveBookProvide(repository: BookSearchRepository, book: Book) =
        SaveBookUseCase(repository).invoke(book)


    @Provides
    @Singleton
    suspend fun deleteBookProvide(repository: BookSearchRepository, book: Book) =
        DeleteBookUseCase(repository).invoke(book)
}
