package com.example.booksearchapp.util

import com.example.booksearchapp.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARRCH_BOOK_DELAY = 100L
    const val DATASTORE_NAME = "preferences_datastore"
    const val PAGING_SIZE = 15
}