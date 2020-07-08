package com.example.bloodbank.ui.activity

import android.os.Bundle
import com.example.bloodbank.R
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.authCycle.LoginFragment

class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        this.replaceFragment(R.id.activity_auth_fl_content, LoginFragment())
    }

    override fun onBackPressed() {
        baseFragment.onBack()
    }
}