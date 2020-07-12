package com.example.bloodbank.ui.fragment.splashCycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.ui.fragment.BaseFragment

class SplashFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onBack() {
        super.onBack()
    }
}