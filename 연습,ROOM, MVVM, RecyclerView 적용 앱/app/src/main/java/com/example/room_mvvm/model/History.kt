package com.example.room_mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "History")
data class History (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var songName: String,
    var singerName: String
)
