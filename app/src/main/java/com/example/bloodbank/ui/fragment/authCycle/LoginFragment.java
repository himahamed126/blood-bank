package com.example.bloodbank.ui.fragment.authCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.bloodbank.R;
import com.example.bloodbank.data.local.SharedPreferencesManger;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.data.model.notifications.Notification;
import com.example.bloodbank.ui.activity.HomeActivity;
import com.example.bloodbank.ui.fragment.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.FIREBASE_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PASSWORD;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.bloodbank.data.local.SharedPreferencesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.fragment_login_ed_phone)
    EditText fragmentLoginEdPhone;
    @BindView(R.id.fragment_login_ed_password)
    EditText fragmentLoginEdPassword;
    @BindView(R.id.fragment_login_cb_remember)
    AppCompatCheckBox fragmentLoginCbRemember;

    private static final String TAG = "LoginFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setSharedPreferences(getActivity());
        return view;
    }

    private void registerNotificationToken(String apiToken) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = task.getResult().getToken();
                if (!task.isSuccessful()) {
                    Log.w(TAG, "onComplete: ", task.getException());
                } else {
                    getClient().registerNotificationToken(token, apiToken, "").enqueue(new Callback<Notification>() {
                        @Override
                        public void onResponse(Call<Notification> call, Response<Notification> response) {
                            if (response.body().getStatus() == 1) {
                                Log.i(TAG, "onResponse: " + response.body().getMsg());
                            } else {
                                Log.i(TAG, "onResponse: " + response.body().getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Call<Notification> call, Throwable t) {

                        }
                    });
                }
                SharedPreferencesManger.SaveData(getActivity(), FIREBASE_TOKEN, token);
            }
        });
    }

    private void loginUser(String phone, String password) {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().login(phone, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        showToast(getActivity(), response.body().getMsg());
                        registerNotificationToken(response.body().getData().getApiToken());
                        SaveData(getActivity(), API_TOKEN, response.body().getData().getApiToken());
                        SaveData(getActivity(), USER_DATA, response.body().getData());
                        SaveData(getActivity(), PASSWORD, password);
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    } else {
                        showToast(getActivity(), response.body().getMsg());
                        Log.i(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.fragment_login_tv_forget_password, R.id.fragment_login_btn_login, R.id.fragment_login_tv_register})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_tv_forget_password:
                RestPasswordFragment restPasswordFragment = new RestPasswordFragment();
                replace(restPasswordFragment, getActivity().getSupportFragmentManager(), R.id.activity_auth_fl_content);
                break;
            case R.id.fragment_login_btn_login:
                loginUser(fragmentLoginEdPhone.getText().toString(), fragmentLoginEdPassword.getText().toString());
                break;
            case R.id.fragment_login_tv_register:
                RegisterFragment registerFragment = new RegisterFragment();
                replace(registerFragment, getActivity().getSupportFragmentManager(), R.id.activity_auth_fl_content);
                break;
        }
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
