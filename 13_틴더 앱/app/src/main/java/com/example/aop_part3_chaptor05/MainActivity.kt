package com.example.aop_part3_chaptor05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null){ // 로그인이 안돼있다면,
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else{ // 로그인이 돼있다면
            startActivity(Intent(this, LikeActivity::class.java))
            finish()
        }
    }

}