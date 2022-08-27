package com.example.booksearchapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration // AppBar 타이틀 변경을 위해 Navi마다 다른 타이틀 !

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        setUpBottonNavigation()
//
//        if (savedInstanceState == null) { // 맨처음 실행하면 search 뜨게함, 앱이 맨 처음으로 생성됐는지 여부에 따라savedInstanceState로 확인가능
//            binding.bottomNavi.selectedItemId = R.id.fragment_search
//        }

        setupJetpackNavigation()

//        val database = BookSearchDatabase.getInstacne(this)
//        val bookSearchRepository = BookSearchRespositoryImp(database)
//        val factory = BookSearchViewModelProviderFactory(bookSearchRepository, this)
//        bookSearchViewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]

    }

    private fun setupJetpackNavigation() {
        val host =
            supportFragmentManager.findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment?
                ?: return
        navController = host.navController
        binding.bottomNavi.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search,
                R.id.fragment_favorite,
                R.id.fragment_settings // 모두가 TopLevel로 지정돼서 뒤로가기 없어짐
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp() // 뒤로가기 버튼 활ㄹ성화
    }
//
//    private fun setUpBottonNavigation() {
//        binding.bottomNavi.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.fragment_search -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SearchFragment()).commit()
//                    true
//                }
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, FavoriteFragment()).commit()
//                    true
//                }
//                R.id.fragment_settings -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SettingFragment()).commit()
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }
//    }


}