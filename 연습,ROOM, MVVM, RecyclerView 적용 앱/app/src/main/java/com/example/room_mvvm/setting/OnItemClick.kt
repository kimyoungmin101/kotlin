package com.example.room_mvvm.setting

import com.example.room_mvvm.model.History


interface OnItemClick {
    fun deleteTodo(history: History)

    fun updateTodo(history: History)
}
