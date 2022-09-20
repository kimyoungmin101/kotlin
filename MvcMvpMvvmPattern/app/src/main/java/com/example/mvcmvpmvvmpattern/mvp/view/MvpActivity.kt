package com.example.mvcmvpmvvmpattern.mvp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvcmvpmvvmpattern.databinding.ActivityMvcBinding
import com.example.mvcmvpmvvmpattern.databinding.ActivityMvpBinding
import com.example.mvcmvpmvvmpattern.mvc.model.Answer
import com.example.mvcmvpmvvmpattern.mvp.presentor.GetAnswerImp

class MvpActivity : AppCompatActivity(), MyAnswerView {
    private lateinit var binding: ActivityMvpBinding

    private lateinit var getAnswerImp: GetAnswerImp
    override val answer: String
        get() = binding.editTextView.text.toString()


    override fun getAnswercollect(isBoolean: Boolean) {
        if(isBoolean){
            Toast.makeText(this, "${answer}는 정답 입니다.", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "${answer}는 틀렸습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getAnswerImp = GetAnswerImp(this)

        binding.resultButton.setOnClickListener {
            getAnswerImp.getAnswer()
        }
    }
}