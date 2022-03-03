package com.example.aop_part2_chaptor02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy{
        findViewById<Button>(R.id.resetButton)
    }

    private  val addButton: Button by lazy{
        findViewById<Button>(R.id.addButton)
    }

    private  val runButton: Button by lazy{
        findViewById<Button>(R.id.runButton)
    } // 자동 생성 시작 버튼

    private  val numberPicker: NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.NumberPicker)
    }

    private var didrun = false
    private val pickNumberSet = mutableSetOf<Int>() // 최종적인 로또 번호가 담길 set !!

    private val numberTextViewList : List<TextView> by lazy{
        listOf<TextView>(
            findViewById<TextView>(R.id.first_number),
            findViewById<TextView>(R.id.second_number),
            findViewById<TextView>(R.id.third_number),
            findViewById<TextView>(R.id.fourth_number),
            findViewById<TextView>(R.id.fifth_number),
            findViewById<TextView>(R.id.sixth_number),
        )
    } // 이런식으로 textview를 리시트안에 넣어서 사용하는 방법이 핵심 !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initResetButton()

    }

    private fun initRunButton(){
        runButton.setOnClickListener{
            val list = getRandomNumber()

            list.forEachIndexed{ // Index와 value
                index, value ->
                var textView = numberTextViewList[index]

                textView.text = value.toString()
                textView.isVisible = true

                setNumberBackground(value, textView)
            }

        }
    }

    private fun getRandomNumber() : List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45){
                if (pickNumberSet.contains(i)){
                    continue
                }
                add(i)
            }
        }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0,6-pickNumberSet.size)

        return newList.sorted()
    }

    private fun initResetButton(){
        clearButton.setOnClickListener{
            pickNumberSet.clear() // clear() 실행시 빈값으로 변경 됨 !
            numberTextViewList.forEach{
                it.isVisible = false // 보이지 않음 ! visible이 gone으로 다시 바뀜 ! 자기자신 It을 사용 가능 !
            } // forEach는 각각의 문 들 모두 적용시켜준다, 파이썬같은경우 for i in range(len(numberTextView): 이런식 ?
            didrun = false
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener{
            if(didrun){
                makeText(this,"초기화 후에 시도해 주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 6){
                makeText(this,"번호는 6개까지 선택가능 합니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                makeText(this,"이미 선택한 번호 입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pickNumberSet.add(numberPicker.value)

            getSortedResult(pickNumberSet)
        }
    }

    private fun getSortedResult(pickNumber : Set<Int>){
        val listOfResult = pickNumber.toMutableList()
        listOfResult.sort()

        listOfResult.forEachIndexed{ // Index와 value
                index, value ->
            var textView = numberTextViewList[index]

            textView.text = value.toString()
            textView.isVisible = true

            setNumberBackground(value, textView)
        }
    } // Add Button 클릭 시 자동 정렬 되도록 바꿈

    private fun setNumberBackground(number:Int, textview:TextView){
        when(number){
            in 1..10 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textview.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        } // contextCompat
    }

//    API 레벨 22에서 사용되지 않는 getDrawable (int)
//    getDrawable(int ) 가 이제 API 레벨 22에서 사용되지 않습니다. 대신 지원 라이브러리에서 다음 코드를 사용해야합니다.
//    ContextCompat.getDrawable(context, R.drawable.ready)

}
