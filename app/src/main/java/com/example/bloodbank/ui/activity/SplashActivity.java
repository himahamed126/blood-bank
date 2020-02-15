package com.example.bloodbank.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.bloodbank.R;
import com.example.bloodbank.ui.fragment.splashCycle.InroFragment;
import com.example.bloodbank.ui.fragment.splashCycle.SplashFragment;

import static com.example.bloodbank.helper.HelperMethods.replace;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = new SplashFragment();
        InroFragment inroFragment = new InroFragment();

        replace(splashFragment, getSupportFragmentManager(), R.id.activity_splash_fl_content);

        new Handler().postDelayed(() -> replace(inroFragment, getSupportFragmentManager(), R.id.activity_splash_fl_content), 2000);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
