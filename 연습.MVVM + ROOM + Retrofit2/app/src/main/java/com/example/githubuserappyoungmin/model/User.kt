package com.example.githubuserappyoungmin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "User")
@Parcelize
data class User (
    @SerializedName("id")@PrimaryKey()
    var id: Int,

    @SerializedName("avatar_url")
    var avatar_url : String,

    @SerializedName("login")
    var title: String,

    @SerializedName("url")
    var url: String,

    var hart : Boolean
) : Parcelable
