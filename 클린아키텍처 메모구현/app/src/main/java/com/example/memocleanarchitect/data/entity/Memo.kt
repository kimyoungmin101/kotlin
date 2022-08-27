package com.example.memocleanarchitect.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    val title : String,
    val decription : String,
    val hasCompleted : Boolean = false
)