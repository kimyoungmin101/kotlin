package com.example.aop_part2_chaptor07

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

// RecordButton은 특정 Case마다 달라지므로 다른 class로 구현을 하여 state의 변경에 따라
// 레코드의 모양을 바꾸어 주는 식으로 진행을 시킨다.
// Button 커스터 마이징 !

class RecordButton(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {
    fun updateIconWithState(state: State) {
        when (state) {
            State.BEFORE_RECORDING -> { // 실행하기 전
                setImageResource(R.drawable.ic_record) // 이미지 모양을 바꾸는 함수 setImageResource
            }
            State.ON_RECORDING -> { // 녹화 중
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING -> { // 녹화 및 재생이 끝난 뒤
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING -> { // 녹화 한 것 을 재생 중
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}
