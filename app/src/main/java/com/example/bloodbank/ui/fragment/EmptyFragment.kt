package com.example.bloodbank.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.example.bloodbank.R

class EmptyFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        val view = inflater.inflate(R.layout.fragment_empty, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onBack() {
        super.onBack()
    }
}