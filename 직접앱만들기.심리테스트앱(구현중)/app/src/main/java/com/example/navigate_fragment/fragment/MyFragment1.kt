package com.example.navigate_fragment.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigate_fragment.TestActivity
import com.example.navigate_fragment.adapter.LovePagerAdapter
import com.example.navigate_fragment.data.LoveTest
import com.example.navigate_fragment.databinding.FirstTablayoutBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray
import org.json.JSONObject

class MyFragment1 : Fragment() {

    lateinit var binding: FirstTablayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstTablayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData("simple_test")
        initData("simple_test_second")
        initData("simple_test_third")
    }

    private fun initData(dataKey : String) {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
        )
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                binding.progressBar.visibility = View.GONE // remoteConfig fectch가 완료 되면 사라지게 함, gone으로 사라지게 가능 !
                val tests = parseQuotesJson(remoteConfig.getString("${dataKey}"))
                displayQuotesPager(tests, dataKey)
            }
        }
    }

    private fun parseQuotesJson(json: String): List<LoveTest> {
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }
        return jsonList.map {
            LoveTest(
                title = it.getString("title"),
                description = it.getString("description"),
            )
        }
    }

    private fun displayQuotesPager(tests: List<LoveTest>, dataKey: String) {
        val adapter = LovePagerAdapter(
            test = tests,
            onItemClicked = {articleModel ->
                val title = articleModel.title.toString()
                val description = articleModel.description.toString()

                val article = LoveTest(title, description)

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