package com.example.navigate_fragment.fragment

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigate_fragment.MainActivity
import com.example.navigate_fragment.TestActivity
import com.example.navigate_fragment.adapter.LovePagerAdapter
import com.example.navigate_fragment.data.Article
import com.example.navigate_fragment.data.LoveTest
import com.example.navigate_fragment.databinding.FirstTablayoutBinding
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray


class MyFragment1 : Fragment() {

    lateinit var binding: FirstTablayoutBinding
    private val article_one = mutableListOf<LoveTest>()
    private val article_two = mutableListOf<LoveTest>()
    private val article_three = mutableListOf<LoveTest>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FirstTablayoutBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Framgent에서 actiity 메서드 접근가능 !
        val first_test = (activity as MainActivity).one
        val second_test = (activity as MainActivity).two
        val third_test = (activity as MainActivity).three

        first_test.article.map {
            article_one.add(it)
        }

        second_test.article.map {
            article_two.add(it)
        }

        third_test.article.map {
            article_three.add(it)
        }

        displayQuotesPager(article_one, "simple_test")
        displayQuotesPager(article_two, "simple_test_second")
        displayQuotesPager(article_three, "simple_test_third")


    }

    private fun displayQuotesPager(tests: List<LoveTest>, dataKey: String) {
        val adapter = LovePagerAdapter(
            test = tests,
            onItemClicked = {articleModel ->
                val title = articleModel.title.toString()
                val description = articleModel.description.toString()
                val imgurl = articleModel.imgurl
                val question = articleModel.question
                val answer = articleModel.answer
                val result = articleModel.result

                val article = LoveTest(title, description, imgurl, question, answer, result)

                val intent = Intent(getActivity(), TestActivity::class.java)
                intent.putExtra("dataItem", article)
                startActivity(intent)
            }
        )

        when(dataKey){
            "simple_test" -> {
                binding.viewPagerTest.adapter = adapter
                binding.viewPagerTest.setCurrentItem(adapter.itemCount / 2, false)
            }
            "simple_test_second" -> {
                binding.viewPagerTestSecond.adapter = adapter
                binding.viewPagerTestSecond.setCurrentItem(adapter.itemCount / 2, false)
            }
            else-> {
                binding.viewPagerTestThird.adapter = adapter
                binding.viewPagerTestThird.setCurrentItem(adapter.itemCount / 2, false)
            }
        }

    }

}