package com.example.viewpagerpracticeagain.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewpagerpracticeagain.R
import com.example.viewpagerpracticeagain.RecyclerViewAdapter
import com.example.viewpagerpracticeagain.databinding.FragmentHomeBinding
import com.example.viewpagerpracticeagain.model.Model
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val articleList = mutableListOf<Model>()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private var binding : FragmentHomeBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        initialize()
        refreshRecyclerView(view)
    }

    private fun initialize(){
        for (num in 1..100){
            val articleModel = Model("user${num}", "title${num}", "${num}", "10${num}", "http://picsum.photos/536/354")
            articleList.add(articleModel)
        }
    }

    private fun refreshRecyclerView(view: View) {
        recyclerViewAdapter = RecyclerViewAdapter(onItemClicked = {
                articleModel -> if (articleModel != null){
                    Snackbar.make(view, "${articleModel.sellerId}입니다.", Snackbar.LENGTH_SHORT).show()
                }
        })
        recyclerViewAdapter.submitList(articleList)
        binding!!.itemRecyclerView.adapter = recyclerViewAdapter
        binding!!.itemRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}