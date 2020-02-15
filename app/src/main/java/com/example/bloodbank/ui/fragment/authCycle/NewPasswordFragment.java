package com.example.bloodbank.ui.fragment.authCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.restPassword.RestPassword;
import com.example.bloodbank.ui.activity.HomeActivity;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PHONE;
import static com.example.bloodbank.data.local.SharedPreferencesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class NewPasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_new_password_ed_code)
    EditText fragmentNewPasswordEdCode;
    @BindView(R.id.fragment_new_password_ed_new_password)
    EditText fragmentNewPasswordEdNewPassword;
    @BindView(R.id.fragment_new_password_ed_confirm_new_password)
    EditText fragmentNewPasswordEdConfirmNewPassword;
    private static final String TAG = "NewPasswordFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void newPassword() {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().newPassword(fragmentNewPasswordEdNewPassword.getText().toString(),
                fragmentNewPasswordEdConfirmNewPassword.getText().toString(),
                fragmentNewPasswordEdCode.getText().toString(), LoadData(getActivity(), PHONE)).enqueue(new Callback<RestPassword>() {
            @Override
            public void onResponse(Call<RestPassword> call, Response<RestPassword> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    showToast(getActivity(), response.body().getMsg());
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RestPassword> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.fragment_new_password_btn_change)
    public void onViewClicked() {
        newPassword();
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
