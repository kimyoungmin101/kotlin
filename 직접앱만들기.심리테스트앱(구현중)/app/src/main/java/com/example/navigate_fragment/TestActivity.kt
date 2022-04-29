package com.example.navigate_fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.navigate_fragment.adapter.AnswerAdapter
import com.example.navigate_fragment.data.AnswerModel
import com.example.navigate_fragment.data.LoveTest
import com.example.navigate_fragment.databinding.ActivityTestBinding
import com.google.android.material.snackbar.Snackbar

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    private val articleList = mutableListOf<AnswerModel>()
    private lateinit var answerAdapter: AnswerAdapter
    lateinit var love : LoveTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        love = intent.getParcelableExtra<LoveTest>("dataItem")!!

        refreshRecyclerView()

        val answerList = love?.answer?.toList()
        Log.d("answerList", "answerList : ${answerList}")
        val questionList = love?.question?.toList()

        if (answerList != null) {
            initialize(answerList)
        }

        if (intent.hasExtra("dataItem")) {
            binding.titleTextview.text = love?.title
            binding.textQuestion.text = questionList?.get(0)
            if(love.imgurl != ""){
                binding.QuestionImageView.isVisible = true
                Glide.with(this)
                    .load("${love.imgurl}")
                    .into(binding.QuestionImageView)
            }
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initialize(answerList: List<String>) {
        for (index in 0 until answerList.size) {
            val model = AnswerModel(answerList.get(index))
            articleList.add(model)
        }

    }

    private fun refreshRecyclerView() {
        answerAdapter = AnswerAdapter(onItemClicked = {
                articleModel -> if (articleModel != null){
                    Log.d("imageUrl", "${love.answer}")
        }
        })

        answerAdapter.submitList(articleList)
        binding!!.recyclerView.adapter = answerAdapter
        binding!!.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}






