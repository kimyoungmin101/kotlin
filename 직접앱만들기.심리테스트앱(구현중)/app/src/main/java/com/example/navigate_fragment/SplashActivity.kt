package com.example.navigate_fragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navigate_fragment.data.Article
import com.example.navigate_fragment.data.LoveTest
import com.example.navigate_fragment.databinding.SplashViewBinding
import com.example.navigate_fragment.fragment.MyFragment1
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.*
import org.json.JSONArray

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashViewBinding
    private val SPLASH_DELAY: Long = 2000 // 2 seconds

    private val articles = mutableListOf<Article>()
    private val keys = mutableListOf<String>()

    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {

            CoroutineScope(Dispatchers.IO).launch {
                initData("simple_test")
                initData("simple_test_second")
                initData("simple_test_third")
            }

            launch {
                subroutine()
            }
        }
    }

    suspend fun initData(dataKey: String) {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
        )

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val tests = parseQuotesJson(remoteConfig.getString("${dataKey}"))

                val article = Article(tests)
                articles.add(article)
                keys.add(dataKey)

            }
        }
    }

    private fun parseQuotesJson(json: String): List<LoveTest> {
        val jsonArray = JSONArray(json)

        var jsonList = emptyList<LoveTest>()

        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)

            val answerArray = jsonObject.getJSONArray("answer")
            val resultArray = jsonObject.getJSONArray("result")
            val questionArray = jsonObject.getJSONArray("question")

            val answerArrayList = ArrayList<String>()
            val resultArrayList = ArrayList<String>()
            val questionArrayList = ArrayList<String>()

            for (index_j in 0 until answerArray.length()) {
                answerArrayList.add(answerArray.getString(index_j))
            }
            for (index_j in 0 until resultArray.length()) {
                resultArrayList.add(resultArray.getString(index_j))
            }
            for (index_j in 0 until questionArray.length()) {
                questionArrayList.add(questionArray.getString(index_j))
            }

            val loveTestSample = LoveTest(
                jsonObject.getString("title"),
                jsonObject.getString("description"),
                jsonObject.getString("imgurl"),
                questionArrayList,
                answerArrayList,
                resultArrayList
            )

            jsonList = jsonList + loveTestSample
        }

        return jsonList.map {
            LoveTest(
                title = it.title,
                description = it.description,
                imgurl = it.imgurl,
                question = it.question,
                answer = it.answer,
                result = it.result
            )
        }
    }

    public override fun onDestroy() {
        activityScope.cancel()
        super.onDestroy()
    }

    suspend fun subroutine() {
        delay(SPLASH_DELAY)
        var intent = Intent(this@SplashActivity, MainActivity::class.java)

        intent.putExtra("${keys[0]}", articles[0])
        intent.putExtra("${keys[1]}", articles[1])
        intent.putExtra("${keys[2]}", articles[2])

        startActivity(intent)
        finish()
    }

}

