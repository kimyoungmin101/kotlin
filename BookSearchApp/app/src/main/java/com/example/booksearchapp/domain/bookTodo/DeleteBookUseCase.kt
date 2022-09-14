package com.example.booksearchapp.domain.bookTodo

import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.domain.Usecase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteBookUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) : Usecase {

    suspend operator fun invoke(book: Book) {
        return bookSearchRepository.deleteBooks(book)
    }

}