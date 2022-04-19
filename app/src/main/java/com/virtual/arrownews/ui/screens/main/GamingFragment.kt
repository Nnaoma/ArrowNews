package com.virtual.arrownews.ui.screens.main

import android.os.Bundle
import android.view.View
import com.virtual.arrownews.ui.GenericFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamingFragment : GenericFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        observeHeadline(category = "gaming")
    }

}