package com.example.bloodbank.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.ui.fragment.homeCycle.EditProfileFragment;
import com.example.bloodbank.ui.fragment.homeCycle.HomeFragment;
import com.example.bloodbank.ui.fragment.homeCycle.MoreFragment;
import com.example.bloodbank.ui.fragment.homeCycle.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bloodbank.helper.HelperMethods.replace;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_home_bn_bottom_navigation)
    BottomNavigationView activityHomeBnBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        HomeFragment homeFragment = new HomeFragment();

        replace(homeFragment, getSupportFragmentManager(), R.id.activity_home_fl_content);
        activityHomeBnBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                HomeFragment homeFragment = new HomeFragment();
                replace(homeFragment, getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.profile:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                replace(editProfileFragment, getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.notification:
                NotificationFragment notificationFragment = new NotificationFragment();
                replace(notificationFragment, getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.more:
                MoreFragment moreFragment = new MoreFragment();
                replace(moreFragment, getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
