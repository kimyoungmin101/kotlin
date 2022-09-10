package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.domain.bookTodo.DeleteBookUseCase
import com.example.booksearchapp.domain.bookTodo.GetAllBookUseCase
import com.example.booksearchapp.domain.bookTodo.SaveBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getAllBookUseCase: GetAllBookUseCase,
    private val saveBookUseCase: SaveBookUseCase
) : ViewModel() {

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        deleteBookUseCase(book)
    }

    // val favoriteBooks: Flow<List<Book>> = getAllBookUseCase.getAllBooks()

    val favortieBooks: StateFlow<List<Book>> =
        getAllBookUseCase.getAllBooks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        saveBookUseCase(book)
    }

    // Paging
    val favoritePagingBooks: StateFlow<PagingData<Book>> =
        bookSearchRepository.getFavoritePagingBooks()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()

    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getSortMode()
            .first() // 설정 값 특성상 전체 데이터 스트림을 구독할 필요 없이 단일 스트림 값을 가져올 First를 가져오고 반드시 값을 가져오고 종료되게 w행thcontext로 진행
    }

    fun searchBooksPaging(query: String) {
        viewModelScope.launch {
            bookSearchRepository.searchBooksPaging(query, getSortMode())
                .cachedIn(viewModelScope)
                .collect {
                    _searchPagingResult.value = it
                }
        }
    }

}