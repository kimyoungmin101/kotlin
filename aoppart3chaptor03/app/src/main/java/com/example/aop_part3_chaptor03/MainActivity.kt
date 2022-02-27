package com.example.aop_part3_chaptor03

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Todo step0 : View 초기화 해주기
        initOnOffButton()
        initChangeAlarmTimeButton()

        // Todo Step1 : 데이터 가져오기
        val model = fetchDataFromSharedPreferences()
        renderView(model)
        cancelAlarm()

        // Todo Step2 : View에 데이터 그려주기

    }

    private fun initOnOffButton(){
        val onOffButton = findViewById<Button>(R.id.onOffButton)
        onOffButton.setOnClickListener {
            Log.d("this", "it.tag : ${it.tag}")
            val model = it.tag as? AlarmDisplayModel ?: return@setOnClickListener// 형 변환 실패할 수 있으므로 ?

            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not())
            // 데이터를 확인한다.
            renderView(newModel)
            Log.d("this", "Model : ${newModel}")

            if(newModel.onOff){
                // 꺼진 경우 -> 알람을 등록

                val calendar = java.util.Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)

                    if(before(java.util.Calendar.getInstance())){ // 지금 시간 보다 이전이다. 하면 다음날로 미뤄줌 !
                        add(Calendar.DATE, 1)
                    }
                }
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this,AlarmReceiver::class.java)

                val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE,
                intent,PendingIntent.FLAG_UPDATE_CURRENT)

                // alarmManager.setExactAndAllowWhileIdle() // 안드로이드 잠자기 모드에서도 실행 할 수 있는 메소드

                alarmManager.setInexactRepeating( // 정시 실행하여 반복하는 기능 API 핵심 기능 !! setInexactRepeating 비교적 비정확함,
                    // 알람 매니저는 비교적 정시에 실행해야하는 앱에서 사용 !
                    AlarmManager.RTC_WAKEUP, // 부팅된 시간이후로 하는 값 : ELAPSED_REALTIME_WAKEUP
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, // 하루에 한번씩 pendingIntent 실행 !
                    pendingIntent
                )
            }else{
                // 켜진 경우 -> 알람을 끔
                cancelAlarm()
            }
            // 온오프에 따라 작업을 처리한다.
            // 오프 -> 알람 제거
            // 온 -> 알람 등록
            // 데이터를 저장한다.
        }
    }

    private fun initChangeAlarmTimeButton(){
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeButton)
        changeAlarmButton.setOnClickListener {

            val calender = java.util.Calendar.getInstance()
            // 현재 시간 가져온다. 캘린더 이용

            TimePickerDialog(this,{picker, hour, minute ->
                val model = saveAlarmModel(hour, minute, onOff = false)
                renderView(model)
                // 데이터를 저장한다.
                // 뷰를 업데이트 한다.

                cancelAlarm()


            }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show()
            // TimePcikDialog 띄어 주어 시간을 설정 하도록 하게끔 하고 시간을 가져온다.

            // 기존에 있던 알람을 삭제 한다.
        }
    }

    private fun saveAlarmModel(hour : Int, minute : Int, onOff : Boolean) : AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = onOff
        ) // 처음으로 알람 설정 값이므로 false를 해준다.

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        with(sharedPreferences.edit()){
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFFKEY, model.onOff)
            commit() // with로 작성했으므로 commit()실행을 해준다.
        }

        return model
    }

    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val timeDBValue = sharedPreferences.getString(ALARM_KEY, "9:30") ?: "9:30"
        val onOffDBValue = sharedPreferences.getBoolean(ONOFFKEY, false)
        val alarmData = timeDBValue.split(":")

        val alarmModel = AlarmDisplayModel(
            hour = alarmData[0].toInt(),
            minute = alarmData[1].toInt(),
            onOff = onOffDBValue
        )

        //  보정 예외처리

        val pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_CODE, Intent(this,AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        if ((pendingIntent == null) and alarmModel.onOff){
            // 알람은 꺼져 있는데 데이터는 켜져 있는 경우
            alarmModel.onOff = false

        }else if((pendingIntent != null) and alarmModel.onOff.not()){
            // 알람은 켜져 있는데, 데이터는 꺼져있는 경우 , 알람을 취소함
            pendingIntent.cancel()
        }

        return alarmModel
    }
    private fun renderView(model: AlarmDisplayModel){
        findViewById<TextView>(R.id.ampmTextView).apply {
            text = model.ampmText
        }

        findViewById<TextView>(R.id.timeTextView).apply {
            text = model.timeText
        }

        findViewById<Button>(R.id.onOffButton).apply {
            text = model.onOffText
            tag = model
        }
    }

    private fun cancelAlarm(){
        val pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_CODE, Intent(this,AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        pendingIntent?.cancel()
    }

    companion object{
        private const val SHARED_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFFKEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}