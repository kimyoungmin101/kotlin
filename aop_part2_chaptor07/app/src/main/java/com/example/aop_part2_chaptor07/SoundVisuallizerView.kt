package com.example.aop_part2_chaptor07

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SoundVisuallizerView( // Sound가 계속 움직이는 그런 모습을 보이기 위한 View
    context : Context, // context와 attrs를 받는다.
    attrs: AttributeSet? = null
) : View(context, attrs){

    var onRequestCurrentAmplitude: (() -> Int)? = null

    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH // 가로 폭
        strokeCap = Paint.Cap.ROUND // 양쪽 끝을 동그랗게 라운드 처리 !
    } // 조금더 곡선이 부드럽게 그려진다.
//    paint는 색상을 집어 넣어줌 !
//    Kotlin 표준 라이브러리는 몇 가지 객체의 Context 내에서 코드 블록{}을 실행하는 것이 유일한 목적인 몇 가지 함수가 포함되어 있다.
//    객체에서 이 람다 함수를 호출하면 해당 함수는 일시적인 Scope를 생성하고, 해당 Scope 안에서는 객체의 이름 없이도 접근이 가능하다.
//    이러한 함수를 Scope Function(범위 지정 함수)이라고 하며, let, run, with, apply, also가 있다.


    private var drawingWidth : Int = 0
    private var drawingHeight : Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()
    private var isReplaying : Boolean = false
    private var replayingPosition : Int = 0

    private val visualizeRepeatAction : Runnable = object : Runnable{
        override fun run() {
            if(!isReplaying) {
                // Amplitude를 가져오고 Draw를 요청 !!

                val currentAmpletude = onRequestCurrentAmplitude?.invoke() ?: 0
                // Amplietude 값 가져오기

                drawingAmplitudes = listOf(currentAmpletude) + drawingAmplitudes
                // 오른쪽부터 순차적 처리
            }
            else{
                replayingPosition++
            }
            // Ampletude, Draw
            invalidate() // 드로잉 처리

            handler?.postDelayed(this, ACTION_INTERVAL)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { // 새로운 가로와 세로가 들어오면 ?!
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        val centerY = drawingHeight / 2f // 뷰의 중앙 높이
        var offsetX = drawingWidth.toFloat() // 뷰의 오른 쪽 끝 위치

        // 어떤 것을 그릴지
        // 뷰의 오른쪽 끝부터 보이기 시작해야함.
        drawingAmplitudes.let {
            amplitudes ->
            if(isReplaying){
                amplitudes.takeLast(replayingPosition) // 가장 뒤부터 리플레이 포지션까지
                // 재생시에는 역으로 보여줘야 하기 때문에 takeLast를 사용 한것 !
            }else{
                amplitudes
            }
        }.forEach {
            // 높이 대비 몇 % 로 그릴지 80%로 그림,
            amplitue -> val lineLength = amplitue / MAX_AMPLITUDE * drawingHeight * 0.8F
            // offsetX 다시 계산
            // 뷰는 오른쪽 부터 그린다.

            offsetX -= LINE_SPACE
            if(offsetX < 0){
                return@forEach
            } // 뷰의 왼쪽영역보다 밖에 있다면

            // 좌상단(0,0) 우하단(w,h)
            canvas.drawLine(
                offsetX,
                centerY - lineLength / 2F,
                offsetX,
                centerY + lineLength / 2F,
                amplitudePaint
            )
        }
    }

    fun startVisualizing(isReplaying : Boolean){
        this.isReplaying = isReplaying
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing(){
        replayingPosition = 0
        handler?.removeCallbacks(visualizeRepeatAction)
    }

    fun clearVisualization(){
        drawingAmplitudes = emptyList()
        invalidate()
    }

    companion object{ // 별도 상수로 빼주기위한 companion
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat() // 32767
        private const val ACTION_INTERVAL = 20L
    }
}

