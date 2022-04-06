package com.example.livedataex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.livedataex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myNumberViewModel: MyNumberViewModel
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)

        myNumberViewModel.liveData.observe(this, {
            binding.resultTextView.text = it.toString()
        })

        binding.addButton.setOnClickListener(this)
        binding.minusButton.setOnClickListener(this)

    }

    override fun onClick(View: View?) {
        val inputNumber = binding.updateValue.text.toString().toInt()

        when(View){
            binding.addButton ->{
                myNumberViewModel.updateValue(ActionType.PLUS, inputNumber)
            }
            binding.minusButton ->{
                myNumberViewModel.updateValue(ActionType.MINUS, inputNumber)
            }
        }
    }

}