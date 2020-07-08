package com.example.bloodbank.ui.fragment.homeCycle.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentMoreBinding
import com.example.bloodbank.extensions.addFragment
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.addFragment
import com.example.bloodbank.ui.dialog.LogOutDialog
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.fragment.homeCycle.notifications.NotificationSettingsFragment

class MoreFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentMoreBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_more, container)

        binding.fragmentMoreBtnFavorite.setOnClickListener(this)
        binding.fragmentMoreBtnConnectUs.setOnClickListener(this)
        binding.fragmentMoreBtnAboutApp.setOnClickListener(this)
        binding.fragmentMoreBtnRateOnStore.setOnClickListener(this)
        binding.fragmentMoreBtnNotificationSettings.setOnClickListener(this)
        binding.fragmentMoreBtnLogOut.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_more_btn_favorite -> activity.addFragment(R.id.activity_home_fl_content, FavoriteFragment())
            R.id.fragment_more_btn_connect_us -> activity.addFragment(R.id.activity_home_fl_content, ContactUsFragment())
            R.id.fragment_more_btn_about_app -> activity.addFragment(R.id.activity_home_fl_content, AboutAppFragment())
            R.id.fragment_more_btn_rate_on_store -> {}
            R.id.fragment_more_btn_notification_settings -> activity.addFragment(R.id.activity_home_fl_content, NotificationSettingsFragment())
            R.id.fragment_more_btn_log_out -> {
                val fragmentManager = fragmentManager
                val logOutDialog = LogOutDialog()
                logOutDialog.show(fragmentManager!!, "logout")
            }
        }
    }

}