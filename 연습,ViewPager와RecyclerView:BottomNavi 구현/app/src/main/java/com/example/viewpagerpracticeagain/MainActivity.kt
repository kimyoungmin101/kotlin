package com.example.viewpagerpracticeagain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpagerpracticeagain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager2.adapter = viewPagerAdapter(this)

        binding.viewPager2.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavi.menu.getItem(position).isChecked = true
                }
            }
        )
        binding.bottomNavi.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                binding.viewPager2.currentItem = 0
                return true
            }
            R.id.add ->{
                binding.viewPager2.currentItem = 1
                return true
            }
            R.id.profile -> {
                binding.viewPager2.currentItem = 2
                return true
            }
            else ->{
                return false
            }
        }
    }
}