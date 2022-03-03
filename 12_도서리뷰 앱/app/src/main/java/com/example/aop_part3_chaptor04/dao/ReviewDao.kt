package com.example.aop_part3_chaptor04.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aop_part3_chaptor04.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review WHERE id == :id")
    fun getOne(id: Int): Review

    @Insert(onConflict = OnConflictStrategy.REPLACE) //같은 책의 리뷰가 있으면 새로운 아이로 REPLACE
    fun saveReview(review: Review)
}
