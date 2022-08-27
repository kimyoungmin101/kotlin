package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.domain.bookTodo.DeleteBookUseCase
import com.example.booksearchapp.domain.bookTodo.GetAllBookUseCase
import com.example.booksearchapp.domain.bookTodo.SaveBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getAllBookUseCase: GetAllBookUseCase,
    private val saveBookUseCase: SaveBookUseCase
) : ViewModel() {

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        deleteBookUseCase(book)
    }

    val favoriteBooks: LiveData<List<Book>> = getAllBookUseCase.getAllBooks()

    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        saveBookUseCase(book)
    }

}