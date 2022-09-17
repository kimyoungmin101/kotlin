package com.example.booksearchapp

import com.example.booksearchapp.data.db.BookSearchDaoTest
import com.example.booksearchapp.ui.view.MainActivityTest_one
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@ExperimentalCoroutinesApi // Corountine도 설정 가능 하도록 !!
@Suite.SuiteClasses(
    MainActivityTest_one::class,
    BookSearchDaoTest::class
)
class InstrumentedTestSuite

// suite로 한번에 테스트 가능하다!!