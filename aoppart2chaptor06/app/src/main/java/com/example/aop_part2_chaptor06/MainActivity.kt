package com.example.aop_part2_chaptor06

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainSecondsTextView: TextView by lazy { // 남은 초를 확인하는 TextView !
        findViewById<TextView>(R.id.remainSecondsTextView)
    }

    private val remainMinutesTextView: TextView by lazy { // 남은 분을 확인하는 TextView
        findViewById<TextView>(R.id.remainMinutesTextView)
    }

    private val soundPool = SoundPool.Builder().build() // 벨소리 바로 빌드 하는 형식으로 가져올 수 있다.
    // 경로는 res -> raw 에 넣어준다.
    // 생명주기 : onCreate() onStart() onResume() activity onPause() onStop() onDestory()

    private var currentCountDownTimer: CountDownTimer? = null // Timer 설정을 위한 변수

    // 1. CountDownTimer를 상속받는 객체 생성
//    public TimerRest(long millisInFuture, long countDownInterval)
//    - millisInFuture : 타이머를 수행할 시간( 1/1000초 기준 )
//    - countDownInterval : 타이머를 수행할 간격(  1/1000초 기준 )
//    public void onTick(long millisUntilFinished)
//    - 수행 간격마다 호출되는 함수
//    - millisUntilFinished : 남은 시간( 1/1000초 단위로 표기 )
//    public void onFinish()
//    - millisInFuture 시간까지 모두 종료시 호출되는 함수


    // 사운드 값을 넣어 주기 위해서는 Int형 아이디 값을 넣어줘야함
    private var tickingSoundId: Int? = null // 항상 울리는 벨 틱톡틱톡~~
    private var bellsoundId: Int? = null // 0초 됐을 때 울리는 벨

    private val seekBar: SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
        initSound()
    }

    override fun onResume() { // display 화면을 나갔을때 소리 멈춤,
        super.onResume()
        soundPool.autoResume() // 재생 중인 모든 사운드를 다시 재생한다.
    }

    // 예를들어 앱을 실행 중인데 전화가 오는 상황이면 기존 실행 하던 앱은 OnPause()를 실행하게 되고 다시 되돌 아왔을때
    // OnResum을 실행시켜 원래 사용하던 앱이 동작하도록 한다. 이때 onPause()가 됐으나 사운드벨이 계속 울리고 있으면
    // 안되는 상황이므로  onResume()에서 리소스를 초기화 하고, onPause()에서 리소스를 해제해줘야 한다.
    // onResume()과 onPause()의 상관관계에 대해 잘 기억해두자,

    override fun onPause() {
        super.onPause()
        soundPool.autoPause() // 활성화된 sound 모두 종료
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release() // 메모리 최적화, 사운드 풀 반환한다.
    }


//    시크바 이벤트 리스너를 활용하면, 사용자가 시크바에서 선택한 값을 알 수 있습니다.
//    (1) onProgressChanged: 시크바를 조작하고 있는 중에 발생
//    (2) onStartTrackingTouch: 시크바를 처음 터치했을 때 발생
//    (3) onStopTrackingTouch: 시크바 터치가 끝났을 때 발생
    private fun bindView() { // object를 통해 view에 바로 접근가능 !
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        updateRemainingTime(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    stopCountDown()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar ?: return

                    if(seekBar.progress == 0){
                        stopCountDown()
                    }else {
                        startCountDown()
                    }
                }

            }
        )
    }

    private fun stopCountDown(){
        currentCountDownTimer?.cancel()
        currentCountDownTimer = null
        soundPool.autoPause()
    }
    private fun startCountDown(){
        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer?.start()

        tickingSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
        } // 1F 정상적으로 한다~!
        // null이 아닐때만 let을 호출해서 Soundid에 넣어줌
    }

    private fun initSound() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellsoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(p0: Long) {
                updateRemainingTime(p0)
                updateSickBar(p0)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun completeCountDown() {
        updateRemainingTime(0)
        updateSickBar(0)

        soundPool.autoPause()
        bellsoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun updateRemainingTime(remaindMillis: Long) {
        val remainSeconds = remaindMillis / 1000

        remainMinutesTextView.text = "%02d'".format(remainSeconds / 60) // "%02D'" 는 예를들어 56분이면 56' << 이렇게 표현됨,
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSickBar(remaindMillis: Long) {
        seekBar.progress = (remaindMillis / 1000 / 60).toInt()
    }
}