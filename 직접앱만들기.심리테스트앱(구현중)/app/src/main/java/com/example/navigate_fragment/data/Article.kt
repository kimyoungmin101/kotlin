package com.example.navigate_fragment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val article : List<LoveTest>
) : Parcelable

