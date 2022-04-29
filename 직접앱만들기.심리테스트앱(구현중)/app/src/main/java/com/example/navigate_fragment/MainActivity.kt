package com.example.navigate_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.navigate_fragment.data.Article
import com.example.navigate_fragment.fragment.MyFargment2
import com.example.navigate_fragment.fragment.MyFragment1
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottom_navigation: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottomNavi)
    }

    lateinit var one : Article
    lateinit var two : Article
    lateinit var three : Article

    private val mOnNavigationiItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId){
            R.id.test -> {
                replaceFragment(MyFragment1())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                replaceFragment(MyFargment2())
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        one = intent.getParcelableExtra<Article>("simple_test")!!
        two = intent.getParcelableExtra<Article>("simple_test_second")!!
        three = intent.getParcelableExtra<Article>("simple_test_third")!!

        replaceFragment(MyFragment1())
        bottom_navigation.setOnItemSelectedListener(mOnNavigationiItemSelectedListener)
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}