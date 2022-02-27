package com.example.aop_part2_chapter01

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.*


class ResultActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy_result)

        val height = intent.getIntExtra("height", 0) // put한 것을 name을 통해 가져올수 있다.
        val weight = intent.getIntExtra("weight",0)

        Log.d("ResultActivity", "height : $height weight : $weight")


        val resultText = when { // when Expression
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중등도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체즁"
            bmi >= 18.5 -> "정상 체중"
            else -> "저체중"
        }

        var bmi = weight / (height / 100.0).pow(2.0)
        bmi = round (bmi * 1000 / 1000)

        val resultValueTextView = findViewById<TextView>(R.id.bmiResultTextView)
        val resultStringTextView = findViewById<TextView>(R.id.resultTextView)

        resultValueTextView.text = bmi.toString()
        resultStringTextView.text = resultText
    }
}