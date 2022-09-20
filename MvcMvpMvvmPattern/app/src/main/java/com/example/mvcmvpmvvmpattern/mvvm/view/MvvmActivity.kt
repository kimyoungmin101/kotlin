package com.example.mvcmvpmvvmpattern.mvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvcmvpmvvmpattern.databinding.ActivityMvvmBinding
import com.example.mvcmvpmvvmpattern.mvc.model.Answer
import com.example.mvcmvpmvvmpattern.mvvm.viewmodel.GetAnswerViewModel

class MvvmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvvmBinding
    private lateinit var getAnswerViewModel: GetAnswerViewModel
    private lateinit var answer: Answer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.resultButton.setOnClickListener {
            val resultAnswer = binding.editTextView.text.toString()
            getAnswerViewModel.getResult(resultAnswer)
        }

        getAnswerViewModel = ViewModelProvider(this).get(GetAnswerViewModel::class.java)
        getAnswerViewModel.isSuccessFlag.observe(this, loginObserver)
    }

    private val loginObserver = Observer<Boolean> { successful ->
        if (successful) {
            Toast.makeText(this, "${getAnswerViewModel.result} 정답 입니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${getAnswerViewModel.result} 정답이 아닙니다.", Toast.LENGTH_SHORT).show()
        }
    }

}