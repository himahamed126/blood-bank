package com.example.bloodbank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.base.BaseActivity
import com.example.bloodbank.utils.FIRST_LAUNCH
import com.example.bloodbank.ui.fragment.splashCycle.InroFragment
import com.example.bloodbank.ui.fragment.splashCycle.SplashFragment

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SharedPreferencesManger.setSharedPreferences(this)
        this.replaceFragment(R.id.activity_splash_fl_content, SplashFragment())

        if (SharedPreferencesManger.LoadBoolean(this, FIRST_LAUNCH)) {
            Handler().postDelayed({ startActivity(Intent(this, AuthActivity::class.java)) }, 2000)
        } else {
            Handler().postDelayed({ this.replaceFragment(R.id.activity_splash_fl_content, InroFragment()) }, 2000)
        }

    }

    override fun onBackPressed() {
        finish()
    }
}