package com.example.bloodbank.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import butterknife.ButterKnife
import com.example.bloodbank.R
import com.example.bloodbank.databinding.ActivityHomeBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.homeCycle.EditProfileFragment
import com.example.bloodbank.ui.fragment.homeCycle.HomeFragment
import com.example.bloodbank.ui.fragment.homeCycle.more.MoreFragment
import com.example.bloodbank.ui.fragment.homeCycle.notifications.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        ButterKnife.bind(this)
        this.replaceFragment(R.id.activity_home_fl_content, HomeFragment())

        binding.activityHomeBnBottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main -> this.replaceFragment(R.id.activity_home_fl_content, HomeFragment())
            R.id.profile -> this.replaceFragment(R.id.activity_home_fl_content, EditProfileFragment())
            R.id.notification -> this.replaceFragment(R.id.activity_home_fl_content, NotificationFragment())
            R.id.more -> this.replaceFragment(R.id.activity_home_fl_content, MoreFragment())
        }
        return true
    }

    override fun onBackPressed() {
        baseFragment.onBack()
    }
}