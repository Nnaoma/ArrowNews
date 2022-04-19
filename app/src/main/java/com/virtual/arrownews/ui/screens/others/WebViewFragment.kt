package com.virtual.arrownews.ui.screens.others

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature
import com.virtual.arrownews.R
import com.virtual.arrownews.databinding.FragmentWebViewBinding

const val WEB_URL = "WEB_URL"

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private lateinit var url : String
    private lateinit var binding: FragmentWebViewBinding

    private var isLoaded = false

    private val saveInstanceIsLoadedKey = "IS_LOADED"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(WEB_URL).toString()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewBinding.bind(view)

        binding.webView.apply {
            webChromeClient = object : WebChromeClient(){ }
            webViewClient = object : WebViewClientCompat(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.swipeLayout.isRefreshing = false
                }

                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = request.url.toString() != url
            }
            isSaveEnabled = true
            isFocusableInTouchMode = true


            settings.apply {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                domStorageEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
                databaseEnabled = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            binding.webView.reload()
            binding.swipeLayout.isRefreshing = true
        }

//        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY))
//            WebSettingsCompat.setForceDarkStrategy(binding.webView.settings, WebSettingsCompat.DARK_STRATEGY_WEB_THEME_DARKENING_ONLY)

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
            when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setForceDark(binding.webView.settings,
                        WebSettingsCompat.FORCE_DARK_ON)
                }
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setForceDark(binding.webView.settings, WebSettingsCompat.FORCE_DARK_OFF)
                }
                else -> { }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(saveInstanceIsLoadedKey, isLoaded)
        binding.webView.saveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            isLoaded = it.getBoolean(saveInstanceIsLoadedKey, false)
            binding.webView.restoreState(it)
        }
    }

    override fun onStart() {
        super.onStart()
        if(!isLoaded){
            binding.webView.loadUrl(url)
            binding.swipeLayout.isRefreshing = true
            isLoaded = true
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(url: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(WEB_URL, url)
                }
            }
    }
}