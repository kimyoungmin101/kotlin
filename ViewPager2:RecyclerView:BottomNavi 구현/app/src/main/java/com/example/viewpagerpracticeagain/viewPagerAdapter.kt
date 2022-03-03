package com.example.viewpagerpracticeagain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpagerpracticeagain.fragment.AddFragment
import com.example.viewpagerpracticeagain.fragment.HomeFragment
import com.example.viewpagerpracticeagain.fragment.ProfileFragment

class viewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment)  {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> AddFragment()
            else -> ProfileFragment()
        }
    }
}