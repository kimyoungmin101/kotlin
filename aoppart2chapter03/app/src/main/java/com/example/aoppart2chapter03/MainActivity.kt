package com.example.aoppart2chapter03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val findPasswordButton : AppCompatButton by lazy {
        findViewById(R.id.findPasswordButton)
    }

    private val initTextView : TextView by lazy {
        findViewById<TextView>(R.id.initTextView)
    }

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker_01).apply {
            this.minValue = 0
            this.maxValue = 9
        }
    } // NumberPicker는 총 0 ~ 999 까지 경우의수가 있으니 세개를 선언하고 pack으로 chain Style을 주어준다.

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker_02).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private var changePasswordMode = false

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker_03).apply {
            this.minValue = 0
            this.maxValue = 9
        }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            // sharedPreferences는 기기 내부 storage에 저장 되는 값 앱을 껏을 때도 사라지지 않음,

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                initTextView.isVisible = false
                initTextView.setText("")

                startActivity(Intent(this,DiaryActivity::class.java))
                // TODO 다이어리 작성 페이지 만들고 실행해야함!
            } else {
                initTextView.isVisible = true
                initTextView.setText("비밀번호가 틀립니다.")
                initTextView.setTextColor(Color.RED)

                showAlertDialog()
            }
        }

        findPasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val realPassword = passwordPreferences.getString("password", "000")

            initTextView.isVisible = true
            initTextView.setText("비밀번호는 : ${realPassword} 입니다.")
            initTextView.setTextColor(Color.GREEN)

        }

        changePasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) {
                passwordPreferences.edit(commit = true) {
                    putString("password", passwordFromUser)
                }
                initTextView.setText("비밀번호 변경 완료")
                initTextView.setTextColor(Color.BLACK)

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()

                    initTextView.isVisible = true
                    initTextView.setText("비밀번호 변경중")
                    initTextView.setTextColor(Color.RED)
                    changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    showAlertDialog()
                }
            }

        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못 됐습니다.")
            .setPositiveButton("확인") { _, _ ->
            }.create().show()
        // Alert Dialog
    }
}