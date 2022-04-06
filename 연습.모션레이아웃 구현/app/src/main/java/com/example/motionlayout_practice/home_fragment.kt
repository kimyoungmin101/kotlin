package com.example.motionlayout_practice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motionlayout_practice.databinding.FragmentHomeFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.abs

class home_fragment : Fragment() {
    lateinit var homeBinding: FragmentHomeFragmentBinding
    private val articleList = mutableListOf<Model>()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding = FragmentHomeFragmentBinding.bind(view)

        initialize()
        refreshRecyclerView(view)
    }

    private fun initialize(){
        for (num in 1..10){
            val articleModel = Model("user${num}", "title${num}", "${num}", "10${num}", "https://picsum.photos/200/300.jpg")
            articleList.add(articleModel)
            //http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00030_1.png
        }
    }

    private fun refreshRecyclerView(view: View) {
        recyclerViewAdapter = RecyclerViewAdapter(onItemClicked = {
                articleModel -> if (articleModel != null){
            Snackbar.make(view, "${articleModel.sellerId} 입니다.", Snackbar.LENGTH_SHORT).show()
        }
        })
        recyclerViewAdapter.submitList(articleList)
        homeBinding!!.itemRecyclerView.adapter = recyclerViewAdapter
        homeBinding!!.itemRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}