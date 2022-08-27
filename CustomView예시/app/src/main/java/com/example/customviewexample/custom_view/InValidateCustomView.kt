package com.example.customviewexample.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class InValidateCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    // 터치 X좌표 값을 저장할 변수
    var coords: PointF? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas) // 텍스트 사이즈 설정
        val paint = Paint()
        paint.textSize = 70f // coordX 변수에 저장된 값을 텍스트로 그린다.
        canvas?.drawText("${coords?.x} / ${coords?.y}", 100f, 600f, paint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 1. 터치 좌표 취득
        coords = PointF(event!!.x, event!!.y)
        var action = ""

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
            }
        }

        Log.d("this", "Action : ${action}")
        // 화면 다시그리기 !! 중요
        invalidate()

        return true

    }
}