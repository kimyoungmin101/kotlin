package com.example.navigate_fragment.data

import android.os.Parcelable
import com.google.gson.JsonArray
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Parcelize
data class LoveTest(
    val title: String?,
    val description: String?,
    val imgurl: String?,
    val question: ArrayList<String>?,
    val answer: ArrayList<String>?,
    val result: ArrayList<String>?,
) : Parcelable