package com.example.aoppart2chaptor4.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aoppart2chaptor4.model.History

// DAO는 인터페이스 또는 추상 클래스일 수 있습니다. 추상 클래스라면 RoomDatabase를 유일한 매개변수로
// 사용하는 생성자를 선택적으로 가질 수 있습니다. Room은 컴파일 시간에 각 DAO 구현을 생성합니다.

@Dao
interface HistoryDao{

    @Query(value = "SELECT * From history")
    fun getAll() : List<History>

    @Insert
    fun insertHistory(history: History)

    @Query(value = "DELETE From history")
    fun deleteAll()

    @Delete
    fun delete(history: History)

    // @Query(value = "SELECT * From history WHERE :result LIKE result LIMIT 1")
    // fun findByResult(result: String) : History
}