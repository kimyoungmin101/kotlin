package com.example.mvcmvpmvvmpattern.mvc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvcmvpmvvmpattern.databinding.ActivityMvcBinding
import com.example.mvcmvpmvvmpattern.mvc.model.Answer

class MvcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvcBinding

    private lateinit var answer : Answer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        answer = Answer()

        binding.answerButton.setOnClickListener {
            val answerResult = binding.editTextView.text.toString()

            if(answer.get_result(answerResult)){
                Toast.makeText(this, "${answerResult}는 정답 입니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "${answerResult}는 정답이 아닙니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

