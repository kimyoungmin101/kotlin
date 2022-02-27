package com.example.aop_part2_chaptor08

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private val progressBar : ContentLoadingProgressBar by lazy {
        findViewById(R.id.progressBar)
    }

    private val refreshLayOut : SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshLayout)
    }

    private val goBackButton : ImageButton by lazy {
        findViewById(R.id.goBackButton)
    }

    private val goHomeButton : ImageButton by lazy{
        findViewById(R.id.goHomeButton)
    }

    private val goFowardButton : ImageButton by lazy{
        findViewById(R.id.goFowardButton)
    }

    private val addressBar : EditText by lazy {
        findViewById(R.id.addressBar)
    }

    private val webView : WebView by lazy {
        findViewById(R.id.webView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){ // 뒤로 갈 수 있다면
            webView.goBack()
        }
        else {
            super.onBackPressed() // 홈 이면 그냥 꺼버림 !!
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(){
        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = webChromeClient()
            settings.javaScriptEnabled = true // 자바 스크립트도 사용하겠다는 명시도 해야
            loadUrl("${DEFAULT_URL}")
        }
    }
    // 아래식을 위처럼 apply형태로 변경 가능하다.
    //    webView.webViewClient = WebViewClient()
    //    webView.settings.javaScriptEnabled = true // 자바 스크립트도 사용하겠다는 명시도 해야
    //    webView.loadUrl("Https://google.com")

    private fun bindViews(){
        addressBar.setOnEditorActionListener { v, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE){
                val loadingUrl = v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)){
                    webView.loadUrl(loadingUrl)
                }
                else {
                    webView.loadUrl("http://$loadingUrl")
                }
            }

            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener {
            webView.goBack()
        }

        goFowardButton.setOnClickListener {
            webView.goForward()
        }

        goHomeButton.setOnClickListener {
            webView.loadUrl("${DEFAULT_URL}")
        }

        refreshLayOut.setOnRefreshListener {
            webView.reload()
        } // 밑으로 당기면 reflesh 새로고침이 된다.
    }

    inner class WebViewClient : android.webkit.WebViewClient(){ // inner를 붙여줌으로써 상위 클래스에 접근 가능

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) { // Page가 전부 로딩 됐을 때 !
            super.onPageFinished(view, url)

            refreshLayOut.isRefreshing = false
            progressBar.hide()
            goBackButton.isEnabled = webView.canGoBack() // 백 이있을때만 백버튼 누를 수 있다.
            goFowardButton.isEnabled = webView.canGoForward() // forward가 있을 때만 누를 수 있다.

            addressBar.setText(url) // 페이지 이동시 m.naver.com 같은 url을 보여 주고 싶을 때
        }
    }


    inner class webChromeClient : android.webkit.WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            progressBar.progress = newProgress
        }
    }

    companion object{
        private const val DEFAULT_URL = "http://www.google.com"
    }
}