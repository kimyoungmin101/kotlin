package com.example.aop_part2_chaptor07

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.sql.Timestamp

class countUpTextView( // 중요한 작업! countUpTextView로 return 하는 recordTimeTextView를 Main에서 생성해 주었음,
    context: Context,
    attrs: AttributeSet? = null
): AppCompatTextView(context,attrs) {
    private var startTimeStamp: Long = 0L

    private val countUpAction: Runnable = object: Runnable{
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()
            // 부팅된 시점부터 현재까지의 시간을 millisecond로 리턴합니다. 즉, 부팅 직후에는 0을 리턴하고 10초가 지났다면 10000이 리턴됩니다.
            //  중요한 것은 디바이스가 Sleep 상태에 있어도 시간은 측정이 됩니다. 만약 부팅된지 10초가 지났고, 이 중에 5초가 Sleep 상태였어도 API는 10초를 리턴합니다.
            // 따라서, 시간 간격(Interval)을 측정할 때는 이 API를 사용하면 좋습니다.

            val countTimeSeconds = ((currentTimeStamp - startTimeStamp) / 1000L).toInt()

            updateCountTime(countTimeSeconds)

            handler?.postDelayed(this,1000L)
        }
    }

    fun clearCountTimer(){
        updateCountTime(0)
    }

    fun startCountUp(){
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countUpAction)
    }

    fun stopCountUp(){
        handler?.removeCallbacks(countUpAction)
        // 특정상황에 stopcountup을 하고 싶으면 postDelayed한 시간 또한 멈춰야 하므로 removeCallbacks을 해준다.
    }

    private fun updateCountTime(countTimeSeconds: Int){
        val minutes = countTimeSeconds / 60
        val seconds = countTimeSeconds % 60

        text = "%02d:%02d".format(minutes, seconds)
    }
}