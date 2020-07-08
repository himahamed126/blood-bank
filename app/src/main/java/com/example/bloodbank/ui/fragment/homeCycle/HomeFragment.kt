package com.example.bloodbank.ui.fragment.homeCycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.ui.adapter.ViewPagerAdapter
import com.example.bloodbank.databinding.FragmentHomeBinding
import com.example.bloodbank.extensions.addFragment
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.fragment.authCycle.LoginFragment
import com.example.bloodbank.ui.fragment.homeCycle.articles.ArticleFragment
import com.example.bloodbank.ui.fragment.homeCycle.donations.AddDonationFragment
import com.example.bloodbank.ui.fragment.homeCycle.donations.DonationFragment

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_home, container)

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        val articleFragment = ArticleFragment()
        val donationFragment = DonationFragment()
        viewPagerAdapter.addPager(articleFragment, getString(R.string.articles))
        viewPagerAdapter.addPager(donationFragment, getString(R.string.donation_request))
        binding.fragmentHomeVpViewPager.adapter = viewPagerAdapter
        binding.fragmentHomeTblTabLayout.setupWithViewPager(binding.fragmentHomeVpViewPager)
        binding.fragmentArticlesFabAdd.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_articles_fab_add -> activity.addFragment(R.id.activity_home_fl_content, AddDonationFragment())
        }
    }

    override fun onBack() {
        activity.replaceFragment(R.id.activity_home_fl_content, LoginFragment())
    }
}