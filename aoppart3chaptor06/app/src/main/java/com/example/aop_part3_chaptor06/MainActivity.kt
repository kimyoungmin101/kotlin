package com.example.aop_part3_chaptor06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.aop_part3_chaptor06.chatList.ChatListFragment
import com.example.aop_part3_chaptor06.home.HomeListFragment
import com.example.aop_part3_chaptor06.mypage.MyPafeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val homeFragment = HomeListFragment()
        val chatFragment = ChatListFragment()
        val mypageFragment = MyPafeFragment()

        replaceFragment(homeFragment)

        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList -> replaceFragment(chatFragment)
                R.id.myPage -> replaceFragment(mypageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment).commit()
        }
    }
}