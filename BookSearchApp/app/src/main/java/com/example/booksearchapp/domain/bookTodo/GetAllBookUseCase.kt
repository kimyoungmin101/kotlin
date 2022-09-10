package com.example.booksearchapp.domain.bookTodo

import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.domain.Usecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllBookUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) : Usecase {
    fun getAllBooks(): Flow<List<Book>> {
        return bookSearchRepository.getFavortieBooks()
    }
}