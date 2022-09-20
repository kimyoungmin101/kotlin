package com.example.mvcmvpmvvmpattern.mvp.presentor

import com.example.mvcmvpmvvmpattern.mvp.model.Answer
import com.example.mvcmvpmvvmpattern.mvp.view.MyAnswerView

class GetAnswerImp(private val myAnswerView: MyAnswerView) : GetAnswer {
    override val answer: Answer
        get() = Answer()

    override fun getAnswer() {
        val answerName = myAnswerView.answer
        val isCollect : Boolean = answer.get_result(answerName)

        myAnswerView.getAnswercollect(isCollect)
    }
}