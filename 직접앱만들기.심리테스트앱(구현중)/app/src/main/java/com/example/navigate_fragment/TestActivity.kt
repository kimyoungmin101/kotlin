package com.example.navigate_fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.navigate_fragment.data.LoveTest
import com.example.navigate_fragment.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val love : LoveTest? = intent.getParcelableExtra<LoveTest>("dataItem")
        Log.d("this,", "${love?.title}")

        if (intent.hasExtra("dataItem")) {
            binding.titleTextview.text = love?.title
        }
    }
}