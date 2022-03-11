package com.example.motionlayout_practice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.motionlayout_practice.databinding.SplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private val splashDuration = 2500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Handler()를 통해서 UI 쓰레드를 컨트롤 한다.
        // Handler().postDelayed(딜레이 시간){딜레이 이후 동작}
        //      postDelayed()를 통해 일정 시간(딜레이 시간)동안 쓰레드 작업을 멈춘다.
        //      {딜레이 이후 동작}을 통해 딜레이 시간 이후, 동작을 정의해준다.
        Handler().postDelayed(splashDuration) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}