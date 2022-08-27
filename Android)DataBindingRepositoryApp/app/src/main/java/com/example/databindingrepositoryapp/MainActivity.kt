package com.example.databindingrepositoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.databindingrepositoryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val myRepositoryImpl = MyRepositoryImpl(10)
        val factory = MyViewModelFactory(myRepositoryImpl,this)
        val myViewModel : MyViewModel by viewModels<MyViewModel>() { factory }

        binding.lifecycleOwner = this
        binding.viewmodel = myViewModel

        binding.button.setOnClickListener {
            myViewModel.increaseCounter()
            //myViewModel.liveCounter.value = myViewModel.liveCounter.value?.plus(1)
        }

//        myViewModel.liveCounter.observe(this) {
//            counter -> binding.numberTextView.text = counter.toString()
//        }
//
//        myViewModel.modifiedCounter.observe(this){
//                counter -> binding.numberTextView.text = counter
//        }
    }


}