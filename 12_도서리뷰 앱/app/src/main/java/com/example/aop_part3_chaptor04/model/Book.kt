package com.example.aop_part3_chaptor04.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    @SerializedName("itemId") val uid : Long, // 서버에서는 itemId로 가져오고 여기서는 id라는 val로 사용가능
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("coverSmallUrl") val coverSmallUrl : String,
    @SerializedName("mobileLink") val mobileLink: String
): Parcelable

