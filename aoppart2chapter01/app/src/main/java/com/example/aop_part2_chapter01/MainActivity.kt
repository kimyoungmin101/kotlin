package com.example.aop_part2_chapter01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        // Layout에 있는 view를 가져 올수 있는 방법 두번째를 많이사용함,

        val resultButton = findViewById<Button>(R.id.resultButton)

        resultButton.setOnClickListener{
            Log.d("mainActivity","ButtonIsClicked!") // 로그를 찍어보는건 아주 좋은일 !! 자주 사용하도록 하자

            if (heightEditText.text.isEmpty() || weightEditText.text.isEmpty()){
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Toast바가 나오게 할려면 Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show() 이런형식 !

            val height:Int = heightEditText.text.toString().toInt()
            val weight:Int = weightEditText.text.toString().toInt()

            Log.d("mainActivity","height : $height")
            Log.d("mainActivity","weight : $weight")

            val intent = Intent(this, ResultActivity::class.java)
            // 핵심기능 Intent !! 현재 위치에서 java클래스로 이동할 수있도록 하는 기능

            intent.putExtra("height", height) // 인텐트에 Extra 집어 넣어준다. putExtra!
            intent.putExtra("weight", weight)

            startActivity(intent) // 순서 val intent = Intent(this, ResultActivity::class.java), PutExtra, startActivity()
        }
    }
}