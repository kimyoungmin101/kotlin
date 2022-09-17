package com.example.booksearchapp.ui.view

import androidx.test.filters.MediumTest
import com.example.booksearchapp.FakeBookSearchRepository
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


// 안드로이드의존서잉 없기 때문에 로컬환경에서 해야함 UI , DATA 두층 통합 테스트 이기 때문에 Medium Test로 분리해주면좋음
@MediumTest
@ExperimentalCoroutinesApi
class BookViewModelTest {

    private lateinit var viewmodel: BookViewModel

    @Before
    fun setUp() {
        viewmodel = BookViewModel(FakeBookSearchRepository())
    }

    @Test
    fun save_book_test() = runTest {
        val book = Book(listOf("A"), "B", "C", "D", 1000, "E", 0, "F", "G", "H", listOf("ME"), "Hi")

        viewmodel.saveBook(book)

        val favoriteBooks = viewmodel.favoriteBooks().first()
        Truth.assertThat(favoriteBooks).contains(book)
    }
}