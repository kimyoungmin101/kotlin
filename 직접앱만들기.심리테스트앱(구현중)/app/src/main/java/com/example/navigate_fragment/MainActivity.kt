package com.example.navigate_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.navigate_fragment.fragment.MyFargment2
import com.example.navigate_fragment.fragment.MyFragment1
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottom_navigation: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottomNavi)
    }

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

        replaceFragment(MyFragment1())
        bottom_navigation.setOnItemSelectedListener(mOnNavigationiItemSelectedListener)
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}