package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.di.UseCaseModule.deleteBookProvide
import com.example.booksearchapp.di.UseCaseModule.getAllBooksProvide
import com.example.booksearchapp.di.UseCaseModule.saveBookProvide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
) : ViewModel() {

    // Paging
    val favoritePagingBooks: StateFlow<PagingData<Book>> =
        getAllBooksProvide(bookSearchRepository)
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    // Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        saveBookProvide(bookSearchRepository, book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        deleteBookProvide(bookSearchRepository, book)
    }

}
