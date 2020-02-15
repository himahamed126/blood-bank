package com.example.bloodbank.ui.fragment;

import androidx.fragment.app.Fragment;

import com.example.bloodbank.ui.activity.BaseActivity;
import com.example.bloodbank.ui.activity.HomeActivity;

public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;
    HomeActivity homeActivity;

    public void setupAcvtivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }
}
