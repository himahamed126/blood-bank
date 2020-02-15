package com.example.bloodbank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbank.ui.fragment.BaseFragment;

public class BaseActivity extends AppCompatActivity {
    public BaseFragment baseFragment;

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }

    public void superBackPressed() {
        super.onBackPressed();
    }
}
