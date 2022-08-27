package com.example.customviewexample.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviewexample.R

//커스텀 뷰를 사용할 때,
//커스텀 뷰를 위한 속성을 정의하여 사용해야할 때가 있다.
// 그 때 사용 하는 것이 attrs / res - values - attrs

class AttrCustomView : View {
    // 커스텀 속성을 참조하기 위한 변수
    private var myShapeColor: Int? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null && context != null) { // attr.xml파일 declare-styleable이 CustomView로 정의된 attr(속성)을 typeArray객체로 받아온다.
            val typedArr = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
            // format을 구분하여 속성값 참조
            myShapeColor = typedArr.getColor(R.styleable.CustomView_myShapeColor, Color.YELLOW)
        }
    }

    // context.obtainStyledAttributes() 메서드를 호출하면 attr.xml에 정의된 속성 정보들이 typedArray 객체로 반환된다.


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = myShapeColor ?: Color.BLACK

        canvas!!.drawRect(100f,100f,350f,350f,paint)
    }
}