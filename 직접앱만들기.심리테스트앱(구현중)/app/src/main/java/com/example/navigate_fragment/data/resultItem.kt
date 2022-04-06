package com.example.navigate_fragment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class resultItem(
    val result_one : String,
    val result_two : String,
    val result_three : String,
): Parcelable