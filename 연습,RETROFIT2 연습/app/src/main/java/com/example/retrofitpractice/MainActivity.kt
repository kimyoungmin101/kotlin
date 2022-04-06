package com.example.retrofitpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitpractice.adapter.RecyclerViewAdapter
import com.example.retrofitpractice.databinding.ActivityMainBinding
import com.example.retrofitpractice.model.InterFaceRetrofit
import com.example.retrofitpractice.model.PhotoEntity
import com.example.retrofitpractice.model.retrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerViewAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RecyclerViewAdapter()


        getPhotosListFromServer()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyClerView.adapter = adapter
        binding.recyClerView.layoutManager = LinearLayoutManager(this)
    }


    private fun getPhotosListFromServer() {
        val service = retrofitApi.retrofitService

        service.listPhotos().enqueue(
            object : Callback<List<PhotoEntity>> {
                override fun onResponse(
                    call: Call<List<PhotoEntity>>,
                    response: Response<List<PhotoEntity>>
                ) {
                    if(response.isSuccessful == true){
                        val result = response.body()?.toList()
                        adapter.submitList(result!!)
                    }
                }

                override fun onFailure(call: Call<List<PhotoEntity>>, t: Throwable) {
                    Log.d("this", "실패 : ${t.toString()}")
                }

            }
        )

    }
}