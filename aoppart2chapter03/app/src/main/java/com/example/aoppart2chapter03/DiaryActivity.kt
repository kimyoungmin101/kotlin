package com.example.aoppart2chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener


class DiaryActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diraryEditText = findViewById<EditText>(R.id.diary_Edit)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diraryEditText.setText(detailPreferences.getString("detail",""))

        // 잠깐 멈칫할때 저장되는 기능! 한글자 한글자 말고
        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit {
                putString("detail",diraryEditText.text.toString())
            }
        }

        diraryEditText.addTextChangedListener{
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }
        // 핸들러 몇초 뒤에 실행시키는 방법!

    }

}