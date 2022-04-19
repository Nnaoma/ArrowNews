package com.virtual.arrownews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.virtual.arrownews.databinding.ActivityDisplayBinding
import com.virtual.arrownews.ui.screens.others.WEB_URL
import com.virtual.arrownews.ui.screens.others.WebViewFragment

class DisplayActivity : AppCompatActivity() {

    companion object{
        const val WEB_VIEW_FRAGMENT = "WEB_VIEW_FRAGMENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDisplayBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val intent = intent
        val startAction = intent.action
        val url = intent.getStringExtra(WEB_URL).toString()

        supportFragmentManager.beginTransaction().add(R.id.container, WebViewFragment.newInstance(url)).commitNow()
    }

}