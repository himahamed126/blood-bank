package com.example.bloodbank.ui.fragment

import androidx.fragment.app.Fragment
import com.example.bloodbank.ui.activity.BaseActivity
import com.example.bloodbank.ui.activity.HomeActivity

open class BaseFragment : Fragment() {
    @JvmField
    var baseActivity: BaseActivity? = null
    var homeActivity: HomeActivity? = null
    fun setupActivity() {
        baseActivity = activity as BaseActivity?
        baseActivity!!.baseFragment = this
    }

    open fun onBack() {
        baseActivity!!.superBackPressed()
    }
}