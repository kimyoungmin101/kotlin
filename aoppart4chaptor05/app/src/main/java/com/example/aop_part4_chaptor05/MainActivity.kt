package com.example.aop_part4_chaptor05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aop_part4_chaptor05.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    val job : Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    // Intent Filter를 통해 Data 받아오는 것 해보기
    private fun initView() = with(binding) {
    }

    private fun loginGithub() {

    }


}