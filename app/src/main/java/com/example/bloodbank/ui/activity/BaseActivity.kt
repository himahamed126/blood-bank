package com.example.bloodbank.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.bloodbank.ui.fragment.BaseFragment

open class BaseActivity : AppCompatActivity() {
    lateinit var baseFragment: BaseFragment
    override fun onBackPressed() {
        baseFragment.onBack()
    }

    fun superBackPressed() {
        super.onBackPressed()
    }
}