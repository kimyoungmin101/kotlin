package com.example.customviewexample.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

// View Class를 상속 받고 생성자를 정의 해준다.
// View 클래스에는 4개의 생성자가 정의되어 있으며, 아래의 2개 생성자는 항상 정의해 두는 편이 좋다
class CustomView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    // Paint 객체를 생성해 뷰의 속성(색상, 크기 등)을 정의 한다.

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL

        // Canvas객체로 도형(원) 그리기 매개변수: 중심의 X좌표, 중심의 Y좌표, 반지름, Paint객체
        canvas?.drawCircle(CXSIZE, CYSIZE, 150f, paint)

        paint.color = Color.YELLOW
        paint.textSize = 40f

        canvas?.drawText("커스텀 뷰", (CXSIZE *0.7).toFloat(), CYSIZE, paint)

    }

    companion object{
        val CXSIZE = 300f
        val CYSIZE = 300f
    }
}

