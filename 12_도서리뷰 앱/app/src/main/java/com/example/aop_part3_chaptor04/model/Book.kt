package com.example.aop_part3_chaptor04.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// 여러 데이터가 하나의 꾸러미안에 담겨져 있다는 parcelize, INTENT에서 한번에 전달이 가능하다.
@Parcelize
data class Book(
    @SerializedName("itemId") val uid : Long, // 서버에서는 itemId로 가져오고 여기서는 id라는 val로 사용가능
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("coverSmallUrl") val coverSmallUrl : String,
    @SerializedName("mobileLink") val mobileLink: String
): Parcelable

