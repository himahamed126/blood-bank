package com.example.bloodbank.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.General.GeneralResponse;
import com.example.bloodbank.ui.activity.SplashActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.FIREBASE_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.clean;

public class LogOutDialog extends DialogFragment {
    private static final String TAG = "LogOutDialog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_log_out, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    private void removeFirebaseToken() {
        getClient().removeNotificationToken(LoadData(getActivity(), FIREBASE_TOKEN), LoadData(getActivity(), API_TOKEN)).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    Log.i(TAG, "onResponse: " + response.body().getMsg());
                    clean(getActivity());
                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                } else {
                    Log.i(TAG, "onResponse: " + response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.dialog_sign_up_btn_yes, R.id.dialog_sign_up_btn_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_sign_up_btn_yes:
//                removeFirebaseToken();
                clean(getActivity());
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.dialog_sign_up_btn_no:
                getDialog().cancel();
                break;
        }
    }
}
