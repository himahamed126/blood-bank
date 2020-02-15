package com.example.bloodbank.ui.fragment.authCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bloodbank.R;
import com.example.bloodbank.data.local.SharedPreferencesManger;
import com.example.bloodbank.data.model.restPassword.RestPassword;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PHONE;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class RestPasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_rest_password_ed_phone)
    EditText fragmentForgetPasswordEdPhone;
    private NewPasswordFragment newPasswordFragment;
    private static final String TAG = "RestPasswordFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_rest_password, container, false);
        ButterKnife.bind(this, view);
        setSharedPreferences(getActivity());
        return view;
    }

    private void restPassword(String phone) {
        newPasswordFragment = new NewPasswordFragment();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().resetPassword(phone).enqueue(new Callback<RestPassword>() {
            @Override
            public void onResponse(Call<RestPassword> call, Response<RestPassword> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    showToast(getActivity(), response.body().getMsg());
                    SaveData(getActivity(), PHONE, phone);
                    replace(newPasswordFragment, getFragmentManager(), R.id.activity_auth_fl_content);
                } else {
                    showToast(getActivity(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RestPassword> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.fragment_rest_password_btn_send)
    void onViewClicked() {
        restPassword(fragmentForgetPasswordEdPhone.getText().toString().trim());
    }

    @Override
    public void onBack() {
        LoginFragment loginFragment = new LoginFragment();
        replace(loginFragment, getActivity().getSupportFragmentManager(), R.id.activity_auth_fl_content);
    }
}
