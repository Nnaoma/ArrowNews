package com.virtual.arrownews.ui.screens

import android.os.Bundle
import android.view.View
import com.virtual.arrownews.ui.GenericFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntertainmentFragment : GenericFragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeHeadline(category = "entertainment")
    }
}