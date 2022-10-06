package com.example.lets_camping.presantation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lets_camping.R
import com.example.lets_camping.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration // AppBar 타이틀 변경을 위해 Navi마다 다른 타이틀 !

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {
        val host =
            supportFragmentManager.findFragmentById(R.id.camping_search_nav_host) as NavHostFragment?
                ?: return
        navController = host.navController
        binding.bottomNavi.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.letsCampingSearch,
                R.id.letsCampingFavorite,
                R.id.letsCampingSettings // 모두가 TopLevel로 지정돼서 뒤로가기 없어짐
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp() // 뒤로가기 버튼 활성화
    }


}