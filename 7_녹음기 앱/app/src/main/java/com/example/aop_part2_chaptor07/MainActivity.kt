package com.example.aop_part2_chaptor07

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val soundVisualizerView : SoundVisuallizerView by lazy {
        findViewById<SoundVisuallizerView>(R.id.soundVisualizerView)
    }

    private val recordTimeTextView : countUpTextView by lazy {
        findViewById(R.id.recordTimeTextView)
    }

    private val resetButton : Button by lazy {
        findViewById<Button>(R.id.resetButton)
    }

    private val recordButton : RecordButton by lazy {
        findViewById<RecordButton>(R.id.recordButton)
    }

    private val recordingFilePath : String by lazy { // ("--외부 앱 전용 공간--")
        "${externalCacheDir?.absolutePath}/recording.3gp"
    } // /storage/emulated/0/Android/data/com.tistory.blackjin.storeapplicaion/cache/recording.3gp 이런 형

    private var state = State.BEFORE_RECORDING
        set(value) { // set(value)를 이용하여 resetButton을 누를 수 있는 시기 설정 해준다.
            field = value
            resetButton.isEnabled = (value == State.AFTER_RECORDING) || (value == State.ON_PLAYING) // 리셋 버튼을 누를수 있는 시기를 설
            recordButton.updateIconWithState(value)
        }

    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO) // RECORD_AUDIO의 Permission 값
    private var player : MediaPlayer? = null // player는 녹음을 한 것을 실행 시키기 위한 변수
    private var recorder : MediaRecorder? = null // recorder는 녹음을 하기 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission()
        initViews()
        bindViews()
        initVariables()
    }

    override fun onRequestPermissionsResult( // 가장 중요한 부분 !! Permission을 받아야 실행이 가능하다.
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
        // requesetCode가 201과 같은지 확인 하고 / GrantResults: 인가한 결과 값의 [0]번째 인덱스가 PERMISSION_GRANTED 이면 True 반환 !

        if(!audioRecordPermissionGranted){
            finish()
        }
    }

    private fun requestAudioPermission(){
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initViews(){
        recordButton.updateIconWithState(state)
    }

    private fun bindViews(){
        soundVisualizerView.onRequestCurrentAmplitude = { // sound 진폭 모습 설정 !
            recorder?.maxAmplitude ?: 0
        } // 콜백함수를 통해 진폭값을 계속 받아와야한다.

        recordButton.setOnClickListener{
            when(state){
                State.BEFORE_RECORDING -> { // 녹음 시작 전일 때 클릭하면 -> 녹음 시작이 된다
                    startRecord()
                }
                State.ON_RECORDING -> { // 녹음 중일때 클릭하면 -> 녹음이 멈춤
                    stopRecording()
                }
                State.AFTER_RECORDING -> { // 녹음 완료 됐을때 클릭 -> 녹음한 것이 실행됨
                    startPlaying()
                }
                State.ON_PLAYING -> { // 녹음을 한 것을 싱행 중일 때 클릭 -> 녹음 실행이 멈춤
                    stopPlaying()
                }
            }
        }

        resetButton.setOnClickListener{
            stopPlaying()
            soundVisualizerView.clearVisualization()
            recordTimeTextView.clearCountTimer()
            state = State.BEFORE_RECORDING
        }

    }

    private fun initVariables(){
        state = State.BEFORE_RECORDING
    }

    private fun startRecord(){ // 오디오 녹음 시작 상태 변경 (State)
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC) // MIC : Microphone audio source
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 비디오 포맷으로 가장 많이 사용되는 THREE_GPP
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // Audio Recording 시 Audio Encoder 를 설정해 주는 함수 입니다.
            setOutputFile(recordingFilePath) // 파일을 저장하는 chache 위치
            prepare() // prepare 단계는 앞서 설정한 설정값들로 recording 을 준비하는 단계 입니다.
        }
//        Android Developer 의 MediaRecorder API Guide 를 참고하면, MediaRecorder 는 아래와 같은 state machine 을 갖습니다.
//        따라서 MediaRecorder API 를 사용할 때 State 를 잘 따라서 코딩해야 합니다.
//        예를들어 Initial 상태에서는 바로 Prepared 상태로 갈 수 없고, Initailized 와 DataConfigured 상태를 거쳐야지 Prepared 상태가 될 수 있습니다.
//        이를 어기게 되면 StateIllegalException 이 발생하고, 원하는 동작을 얻을 수 없으니 조심해야 합니다.

        recorder?.start()
        soundVisualizerView.startVisualizing(false)
        recordTimeTextView.startCountUp()
        state = State.ON_RECORDING // 녹음 중으로 변경 시킴,
    }

    private fun stopRecording(){ // 오디오 멈추기
        recorder?.run {
            stop() // 간단하다 stop()을 주면 된다.
            release()
        }
        recorder = null // recorder는 stop이 됐으니깐 null 값을 준다.
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING
    }

    private fun startPlaying(){ // 오디오 녹음 된 파일 재생
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath) // 캐시에 저장돼있는 DataSource를 가져옴
            prepare()
        }
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING // 재생이 다됐으니깐 !
        } // 현재 전달된 파일이 전부다 재생 됐을 때 발생하는 리스너

        player?.start() // setDataSource -> prepare() -> start()
        soundVisualizerView.startVisualizing(true)
        recordTimeTextView.startCountUp()
        state = State.ON_PLAYING
    }

    private fun stopPlaying(){ // 오디오 녹음 재생을 중지 !!
        player?.release() // MediaPlayer State Machine을 참조 하면 release()가 end를 위한 조건
        player = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING
    }

    companion object{
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}