package com.example.mvcmvpmvvmpattern

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvcmvpmvvmpattern.databinding.ActivityMainBinding
import com.example.mvcmvpmvvmpattern.mvc.MvcActivity
import com.example.mvcmvpmvvmpattern.mvp.view.MvpActivity
import com.example.mvcmvpmvvmpattern.mvvm.view.MvvmActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goToMvc.setOnClickListener {
            val intent = Intent(applicationContext, MvcActivity::class.java)
            startActivity(intent)
        }

        binding.goToMvp.setOnClickListener {
            val intent = Intent(applicationContext, MvpActivity::class.java)
            startActivity(intent)
        }

        binding.goToMvvm.setOnClickListener {
            val intent = Intent(applicationContext, MvvmActivity::class.java)
            startActivity(intent)
        }

    }

}