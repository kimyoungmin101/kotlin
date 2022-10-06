package com.example.booksearchapp.util

import com.example.lets_camping.BuildConfig

object Constants {
    const val BASE_URL = "https://apis.data.go.kr/B551011/GoCamping/basedList"
    const val API_KEY = BuildConfig.goCampApiKey
//    const val SEARRCH_BOOK_DELAY = 100L
//    const val DATASTORE_NAME = "preferences_datastore"
    const val PAGING_SIZE = 15
}