package com.example.bloodbank.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bloodbank.R;
import com.example.bloodbank.ui.fragment.authCycle.RestPasswordFragment;
import com.example.bloodbank.ui.fragment.authCycle.LoginFragment;
import com.example.bloodbank.ui.fragment.authCycle.RegisterFragment;
import com.example.bloodbank.ui.fragment.authCycle.NewPasswordFragment;

import static com.example.bloodbank.helper.HelperMethods.replace;

public class AuthActivity extends BaseActivity {
    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginFragment = new LoginFragment();
        replace(loginFragment, getSupportFragmentManager(), R.id.activity_auth_fl_content);
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
