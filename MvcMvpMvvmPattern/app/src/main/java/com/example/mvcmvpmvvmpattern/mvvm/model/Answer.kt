package com.example.mvcmvpmvvmpattern.mvvm.model

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
        const val ans = "워싱턴"
    }
}
