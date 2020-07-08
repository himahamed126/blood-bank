package com.example.bloodbank.ui.fragment.homeCycle.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentAboutAppBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment

class AboutAppFragment : BaseFragment() {

    lateinit var binding: FragmentAboutAppBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_about_app, container)
        return binding.root
    }
}