package com.example.navigate_fragment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class LoveTest(
    val title: String,
    val description: String,
) : Parcelable