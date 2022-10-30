package com.example.mvcmvpmvvmpattern.mvc.model

data class Answer(
    var result : String? = null
){
    fun get_result(result: String?) : Boolean{
        if(result == ans){
            return true
        }
        else{
            return false
        }
    }

    companion object{
        const val ans = "서울"
    }
}
