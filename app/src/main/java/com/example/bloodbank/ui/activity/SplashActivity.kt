package com.example.bloodbank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.base.BaseActivity
import com.example.bloodbank.ui.fragment.splashCycle.InroFragment
import com.example.bloodbank.ui.fragment.splashCycle.SplashFragment

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.replaceFragment(R.id.activity_splash_fl_content, SplashFragment())

        when (SharedPreferencesManger.getINSTANCE(this)!!.restoreBooleanValue("SEE_SPLASH")) {
            null -> {
                Handler().postDelayed({ this.replaceFragment(R.id.activity_splash_fl_content, InroFragment()) }, 2000)
            }
            false -> {
                Handler().postDelayed({ this.replaceFragment(R.id.activity_splash_fl_content, InroFragment()) }, 2000)
            }
            true -> {
                Handler().postDelayed({ startActivity(Intent(this, AuthActivity::class.java)) }, 2000)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}