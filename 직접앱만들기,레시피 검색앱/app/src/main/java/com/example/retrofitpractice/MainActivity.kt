package com.example.retrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitpractice.adapter.RecyclerViewAdapter
import com.example.retrofitpractice.adapter.RecyclerViewAdapter_2
import com.example.retrofitpractice.databinding.ActivityMainBinding
import com.example.retrofitpractice.model.Recipe
import com.example.retrofitpractice.model.RecipeEntity
import com.example.retrofitpractice.model.Row
import com.example.retrofitpractice.model.retrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerViewAdapter
    lateinit var adapter2 : RecyclerViewAdapter_2

    lateinit var binding: ActivityMainBinding
    private var menuname : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerViewAdapter(
            clickListener =  {
                row ->
                val dataListRecipe = get_text_image(row)
                binding.recyClerView2.isVisible = true
                binding.backButton.isVisible = true
                binding.recyClerView1.isVisible = false
                adapter2 = RecyclerViewAdapter_2()

                adapter2.submitList(dataListRecipe)

                initRecyclerViewSecond()
            }

        )

        binding.searchButton.setOnClickListener {
            menuname = binding.editText.text.toString()
            binding.editText.setText("")
            binding.recyClerView2.isVisible = false
            binding.backButton.isVisible = false
            binding.recyClerView1.isVisible = true
            getPhotosListFromServer(menuname!!)
            initRecyclerView()
        }

        binding.backButton.setOnClickListener {
            binding.recyClerView2.isVisible = false
            binding.backButton.isVisible = false
            binding.recyClerView1.isVisible = true
        }

    }

    private fun get_text_image(row : Row): MutableList<Recipe> {

        val dataList = mutableListOf<Recipe>()

        if (row.mANUAL01 == "" && row.mANUALIMG01 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL01}", "${row.mANUALIMG01}"))


        if (row.mANUAL02 == "" && row.mANUALIMG02 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL02}", "${row.mANUALIMG02}"))

        if (row.mANUAL03 == "" && row.mANUALIMG03 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL03}", "${row.mANUALIMG03}"))

        if (row.mANUAL04 == "" && row.mANUALIMG04 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL04}", "${row.mANUALIMG04}"))

        if (row.mANUAL05 == "" && row.mANUALIMG05 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL05}", "${row.mANUALIMG05}"))

        if (row.mANUAL06 == "" && row.mANUALIMG06 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL06}", "${row.mANUALIMG06}"))

        if (row.mANUAL07 == "" && row.mANUALIMG07 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL07}", "${row.mANUALIMG07}"))

        if (row.mANUAL08 == "" && row.mANUALIMG08 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL08}", "${row.mANUALIMG08}"))

        if (row.mANUAL09 == "" && row.mANUALIMG09 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL10}", "${row.mANUALIMG10}"))

        if (row.mANUAL11 == "" && row.mANUALIMG11 == ""){
            return dataList
        }
        dataList.add(Recipe("${row.mANUAL11}", "${row.mANUALIMG11}"))

        return dataList
    }

    private fun initRecyclerView() {
        binding.recyClerView1.adapter = adapter
        binding.recyClerView1.layoutManager = LinearLayoutManager(this)
    }

    private fun initRecyclerViewSecond() {
        binding.recyClerView2.adapter = adapter2
        binding.recyClerView2.layoutManager = LinearLayoutManager(this)
    }


    private fun getPhotosListFromServer(menu : String) {
        val service = retrofitApi.retrofitService

        service.getString("${KeyResult.API_KEY}","${menu}").enqueue(
            object : Callback<RecipeEntity> {
                override fun onResponse(
                    call: Call<RecipeEntity>,
                    response: Response<RecipeEntity>
                ) {
                    if(response.isSuccessful == true){

                        val result = response.body()?.cOOKRCP01?.row

                        if (response.body()?.cOOKRCP01?.totalCount?.equals("0") == true){
                            Log.d("this", "검색 실패 : ${response.body()?.cOOKRCP01?.totalCount}")
                        }
                        else{
                            adapter.submitList(result!!)
                        }


                    }
                }

                override fun onFailure(call: Call<RecipeEntity>, t: Throwable) {
                    Log.d("this", "실패 : ${t.toString()}")
                }

            }
        )
    }
}