package com.example.aoppart2chaptor4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.aoppart2chaptor4.model.History
import org.w3c.dom.Text
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    lateinit var db :AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "historyDB"
        ).build()
    }

    private var isOperator = false
    private var hasOperator = false

    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    private val historyLayout: View by lazy {
        findViewById<View>(R.id.historyLayout)
    }

    private val historyLinearLayout: LinearLayout by lazy {
        findViewById<LinearLayout>(R.id.historyLinearLayout)
    }

    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }



    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")
            R.id.buttonPlus -> operatorButtonClicked("+")
            R.id.buttonDivide -> operatorButtonClicked("/")
            R.id.buttonMulti -> operatorButtonClicked("*")
            R.id.buttonMinus -> operatorButtonClicked("-")
            R.id.buttonPercent -> operatorButtonClicked("%")
        }
    }

    private fun numberButtonClicked(number: String) { // 0 ~ 9 까지 숫자를 클릭했을 때 !!

        if (isOperator) {
            expressionTextView.append(" ")
        }

        isOperator = false

        val expressionText = expressionTextView.text.split(" ")
        // val str = "123.456,789"
        // val arr = str.split(".", ",") 구분자를 등록 해서 나누어 배열로 저장해줌!
        // println(arr) -> ["123", "456", "789"]

        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) { // 길이가 15를 초과하는 마지막 문자 가 있으면
            Toast.makeText(this, "15자리만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        //            2. 코틀린 first, last 함수 예제
        //            fun firstLast(){
        //                val names = listOf("Duke", "Leonardo", "Sara", "James", "Mino")
        //
        ////first 함수 : 첫번째 인자 리턴
        //                println(names.first())
        //
        ////문자열 길이 4를 초과하는 인자 첫번째 인자 리턴
        //                println(names.first{name -> name.length > 4})
        //
        ////last 함수 : 마지막 인자 리턴
        //                println(names.last())
        //
        ////문자열 길이 4를 초과하는 인자 마지막 인자 리턴
        //                println(names.last{name -> name.length > 4})
        //
        ////firstOrNull 함수 : 첫번째 인자반환하며, 없을 경우에 Null 리턴
        //                println(names.firstOrNull{ name -> name.length > 10})
        //
        ////lastOrNull 함수 : 마지막 인자를 반환하며, 없을 경우에 Null 리턴
        //                println(names.lastOrNull{ name -> name.length > 10})
        //            }

        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        expressionTextView.append(number)
        resultTextView.text = calcuExpression()
    }

    private fun operatorButtonClicked(operator: String) { // Operator 버튼을 눌렀을 경우 !
        if (expressionTextView.text.isEmpty()) { // 이미 비어 있는 Text이면 아무것도 반응 안해줌 !
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
                // text의 마지막 char dropLast로 drop해주고 기존 operator로 교체 해줌,
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한번만 사용 가능합니다..", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTextView.append(" $operator")
            }
        }
        val ssb = SpannableStringBuilder(expressionTextView.text) // 특정 글자의 색만 바꿔주기 위한 Span !

        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length - 1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        // Spannable.SPAN_EXCLUSIVE_INCLUSIVE 플래그를 사용하여 삽입된 텍스트를 포함하고
        // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE를 사용하여 삽입된 텍스트를 제외합니다.

        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true
    }

    fun clearButtonClicked(v: View) { // C 버튼 다 지워준다.
        expressionTextView.text = ""
        resultTextView.text = ""

        isOperator = false
        hasOperator = false
    }

    fun resultButtonClicked(v: View) { // 결과 버튼 '='
        val expressionTexts = expressionTextView.text.split(" ")
        Log.d("MainActivity", "${expressionTextView.text}")
        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1){
            return
        }
        if (expressionTexts.size != 3 && hasOperator){
            Toast.makeText(this, "아직 완성되지않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        else if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = expressionTextView.text.toString()
        val resultText = calcuExpression()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false


        Thread( {
            db.historyDao().insertHistory(History(uid = null,expressionText, resultText))
        }).start() //DB 관련 업무는 새로운 쓰레드에서 !
    }

    private fun calcuExpression() : String{ // 간단함 ! 배열 크기가 3이면 계산을 해준다, [숫자, (operator), 숫자] 형식일때 !! 계
        val expressionTexts = expressionTextView.text.split(" ")
        Log.d("MainActivity", "${expressionTexts}")

        if (hasOperator.not() || expressionTexts.size != 3){
            return ""
        }
        else if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger() // Integet로 변환
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when(op){
            "+" -> (exp1 + exp2).toString()
                "-" -> (exp1 - exp2).toString()
                "/" -> (exp1 / exp2).toString()
                "*" -> (exp1 * exp2).toString()
                "%" -> (exp1 % exp2).toString()
                else -> ""
        } // return에 when형식으로 써줄 수 도 있다 !!

    }

    fun historyButtonClicked(v: View) {
        historyLayout.isVisible = true
        historyLinearLayout.removeAllViews() // FrameLayout에 포함된 모든 자식(Children) 뷰 제거.

        Thread( { // db작업시는 새로운 쓰레드를 이용!
            db.historyDao().getAll().reversed().forEach {
                runOnUiThread {
                    var historyView = LayoutInflater.from(this).inflate(R.layout.history_row, null, false)
                    historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                    historyView.findViewById<TextView>(R.id.resultTextView).text = " = ${it.result}"

                    historyLinearLayout.addView(historyView)
                }
            }
        }).start()
        // Todo 모든 기록 가져오가
        // Todo 뷰 모든 기록 할당하기
    }

    fun closeHistoryButtonClicked(v: View) {
        historyLayout.isVisible = false
    }

    fun clearHistoryButtonClicked(v: View) {
        historyLinearLayout.removeAllViews()

        Thread({
            db.historyDao().deleteAll()
        }).start()

        // TODO DB에서 모든 기록 삭제
    }



}

fun String.isNumber() : Boolean{ // 확장 함수를 구현!
    return try{
        this.toBigInteger()
        true
    }catch (e: NumberFormatException){
        false
    }
}