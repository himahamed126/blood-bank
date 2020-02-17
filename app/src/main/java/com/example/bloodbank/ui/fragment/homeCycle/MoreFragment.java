package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbank.R;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.helper.HelperMethods.replace;

public class MoreFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.fragment_more_btn_favorite, R.id.fragment_more_btn_connect_us, R.id.fragment_more_btn_about_app, R.id.fragment_more_btn_rate_on_store, R.id.fragment_more_btn_notification_settings, R.id.fragment_more_btn_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_more_btn_favorite:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                replace(favoriteFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.fragment_more_btn_connect_us:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                replace(contactUsFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.fragment_more_btn_about_app:
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                replace(aboutAppFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.fragment_more_btn_rate_on_store:
                break;
            case R.id.fragment_more_btn_notification_settings:
                NotificationSettingsFragment notificationSettingsFragment = new NotificationSettingsFragment();
                replace(notificationSettingsFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
            case R.id.fragment_more_btn_log_out:
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
