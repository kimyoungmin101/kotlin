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
import com.example.customviewexample.Box

private const val TAG = "BoxDrawingView"

class BoxDrawingView : View {
// 1. 코드에서 View 객체를 생성할 때 주로 호출하는 생성자
    constructor(context: Context?) : super(context)
    // 2. 레이아웃xml에 등록한 View가 안드로이드에 의해 Inflate될 때 호출되는 생성자 // 매개변수 AttributeSet 객체를 통해 attr.xml 에 정의한 커스텀 속성을 사용할 수 있다.
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }
    private var currentBox: Box? = null
    private var boxen = mutableListOf<Box>()

    override fun onDraw(canvas: Canvas) {
        // 배경을 채운다
        canvas.drawPaint(backgroundPaint)

        boxen.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // 그리기 상태를 재설정한다
                currentBox = Box(current).also { boxen.add(it) }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")

        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }


}
