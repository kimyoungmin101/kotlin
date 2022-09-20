package com.example.mvcmvpmvvmpattern.mvp.model

data class Answer(
    var result : String? = null
){
    fun get_result(result: String?) : Boolean{
        if(result == ans){
            this.result = result
            return true
        }
        else{
            this.result = result
            return false
        }
    }

    companion object{
        const val ans = "베이징"
    }
}
