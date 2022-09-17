package com.example.booksearchapp.data.db

import androidx.test.filters.SmallTest
import com.example.booksearchapp.data.model.Book
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
@ExperimentalCoroutinesApi // Corountine도 설정 가능 하도록 !!
class BookSearchDaoTest {
    @Inject
    @Named("test_db")
    lateinit var database: BookSearchDatabase

    private lateinit var dao: BookSearchDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.bookSearchDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_book_to_db() = runTest {
        val book = Book(listOf("A"), "B", "C", "D", 1000, "E", 0, "F", "G", "H", listOf("ME"), "Hi")

        dao.insertBook(book)

        val favoriteBooks = dao.getFavoriteBooks().first()
        assertThat(favoriteBooks).contains(book)
    }

    @Test
    fun delete_book_to_do() = runTest {
        val book = Book(listOf("A"), "B", "C", "D", 1000, "E", 0, "F", "G", "H", listOf("ME"), "Hi")

        dao.insertBook(book)
        dao.deleteBook(book)

        val favoriteBooks = dao.getFavoriteBooks().first()

        assertThat(favoriteBooks).doesNotContain(book)
    }
}