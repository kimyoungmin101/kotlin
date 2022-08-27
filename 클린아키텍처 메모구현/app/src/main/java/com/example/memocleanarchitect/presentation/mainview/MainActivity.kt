package com.example.memocleanarchitect.presentation.mainview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.memocleanarchitect.R
import com.example.memocleanarchitect.databinding.ActivityMainBinding
import com.example.memocleanarchitect.presentation.adapter.memoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels<MainViewModel>()
    private lateinit var memoAdapter: memoAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}