package com.example.navigate_fragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navigate_fragment.databinding.SplashViewBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }


}