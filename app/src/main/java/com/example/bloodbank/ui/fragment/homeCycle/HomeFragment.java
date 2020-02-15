package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.ViewPagerAdapter;
import com.example.bloodbank.ui.fragment.BaseFragment;
import com.example.bloodbank.ui.fragment.authCycle.LoginFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bloodbank.helper.HelperMethods.replace;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragment_home_tbl_tab_layout)
    TabLayout fragmentHomeTblTabLayout;
    @BindView(R.id.fragment_home_vp_view_pager)
    ViewPager fragmentHomeVpViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        ArticleFragment articleFragment = new ArticleFragment();
        DonationFragment donationFragment = new DonationFragment();
        viewPagerAdapter.addPager(articleFragment, getString(R.string.articles));
        viewPagerAdapter.addPager(donationFragment, getString(R.string.donation_request));
        fragmentHomeVpViewPager.setAdapter(viewPagerAdapter);
        fragmentHomeTblTabLayout.setupWithViewPager(fragmentHomeVpViewPager);
        return view;
    }

    @Override
    public void onBack() {
        LoginFragment loginFragment = new LoginFragment();
        replace(loginFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
    }
}
