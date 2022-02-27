package com.example.aop_part2_chaptor05

import android.content.ContentValues.TAG
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer


class photoFrameActivity : AppCompatActivity() {
    private val photoList = mutableListOf<Uri>()

    private var currentPosition = 0

    private var timer : Timer? = null

    private val photoImageView : ImageView by lazy {
        findViewById<ImageView>(R.id.photoImageView)
    }

    private val backgroundPhotoImageView : ImageView by lazy {
        findViewById<ImageView>(R.id.backgroundPhotoImageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_frame)

        getPhotoUriFromIntent()

    }

    private  fun getPhotoUriFromIntent(){
        val size = intent.getIntExtra("photoSize", 0)
        for (i in 0..size){
            intent.getStringExtra("photo$i")?.let{
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer(){
        timer = timer(period = 5 * 1000){
            runOnUiThread{
                val current = currentPosition

                val next = if(photoList.size <= currentPosition + 1) {
                    0
                } else currentPosition + 1

                backgroundPhotoImageView.setImageURI(photoList[current])

                photoImageView.alpha = 0f // 투명도를 0으로 준다..! 그럼 보이지 않음
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            } // 메인 쓰레드로 변경 해주어야 한다.
        }
    }

    override fun onStop(){
        super.onStop()

        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()

        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        timer?.cancel()
    }
}
