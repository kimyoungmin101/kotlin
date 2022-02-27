package com.example.aop_part3_chaptor01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val resultTextView : TextView by lazy {
        findViewById(R.id.resultTextView)
    }

    private val firebaseTokenTextView : TextView by lazy {
        findViewById(R.id.firebaseTokenTextView)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFireBase() // FireBase 토큰 가져 오는 함수

        updateResult()
    }

    private fun initFireBase(){
        FirebaseMessaging.getInstance().token.
        addOnCompleteListener { // firebase 토큰 가져오고 리스너를 통해 완료를 알림받아야함
                task -> if(task.isSuccessful){
            firebaseTokenTextView.text = task.result
        }
        }
    }

    private fun updateResult(isNewIntent: Boolean = false){ // 앱이 켜져있었는데 갱신했느냐 아니면 앱이 꺼져 있었을때 실행됐나 ?
        resultTextView.text = (intent.getStringExtra("NotificationType") ?: "앱 런처") +
                if(isNewIntent){
                    "(으)로 갱신했습니다."
                }else{
                    "(으)로 실행했습니다."
        }
    }

    override fun onNewIntent(intent: Intent?) { // 현재창에서 NEW INTENT 실행시 !!
        super.onNewIntent(intent)

        setIntent(intent)

        updateResult(true)
    }
}