package com.example.aop_part3_chaptor03

data class AlarmDisplayModel(
    val hour: Int,
    val minute : Int,
    var onOff: Boolean // 밖에서 바꿔 줄 수 있기 때문에 변수로 선언
){
    // 09:30 이렇게 표현 되야해서 로직 설정 !
    val timeText : String

    get() { // %02d 는 두자리 수 까지 들어 올 수 있고 두자리가 아니면 0을 넣어 준다는 포멧 형식이다.
        val h = "%02d".format(if (hour < 12) hour else hour - 12)
        val m = "%02d".format(minute)

        return "$h:$m"
    }

    val ampmText : String
    get(){
        return if (hour < 12) "AM" else "PM"
    }

    val onOffText: String
    get() {
        return if (onOff) "알람 끄기" else "알람 켜기"
    }

    fun makeDataForDB(): String{
        return "$hour:$minute"
    }
}