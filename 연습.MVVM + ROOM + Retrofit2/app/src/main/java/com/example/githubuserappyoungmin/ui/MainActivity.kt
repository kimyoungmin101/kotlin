package com.example.githubuserappyoungmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserappyoungmin.AppDatabase
import com.example.githubuserappyoungmin.mvvm.MainViewModel
import com.example.githubuserappyoungmin.mvvm.MyViewModelFactory
import com.example.githubuserappyoungmin.OnItemClick
import com.example.githubuserappyoungmin.adapter.RecyclerViewAdapter
import com.example.githubuserappyoungmin.api.RetrofitService
import com.example.githubuserappyoungmin.databinding.ActivityMainBinding
import com.example.githubuserappyoungmin.model.User
import com.example.githubuserappyoungmin.room.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnItemClick {
    lateinit var adapter: RecyclerViewAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val db = AppDatabase.getDataBase(this)
        val userDao = db.getUserDao()

        Log.d("this", "asdsd : ${userDao.getAllRecords().value}")


        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService, userDao)

        viewModel  = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)


        viewModel.getAllTasks().observe(this, Observer<List<User>>{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            if (it.size == 0){
                viewModel.makeApiCall()
            }
        })

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun updateUser(user: User) {
        if (user.hart == false){
            user.hart = true
        }
        else{
            user.hart = false
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUpdateTakst(user)
        }
    }

    override fun intentAcitivity(user: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}


