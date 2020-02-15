package com.example.bloodbank.ui.fragment.authCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.GeneralResponseAdapter;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.helper.DateModel;
import com.example.bloodbank.ui.activity.HomeActivity;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PASSWORD;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData2;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showCalender;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.fragment_register_ed_name)
    EditText fragmentRegisterEdName;
    @BindView(R.id.fragment_register_ed_email)
    EditText fragmentRegisterEdEmail;
    @BindView(R.id.fragment_register_tv_birth_date)
    TextView fragmentRegisterTvBirthDate;
    @BindView(R.id.fragment_register_sp_blood_type)
    AppCompatSpinner fragmentRegisterSpBloodType;
    @BindView(R.id.fragment_register_tv_donation_date)
    TextView fragmentRegisterTvDonationDate;
    @BindView(R.id.fragment_register_sp_governorate)
    AppCompatSpinner fragmentRegisterSpGovernorate;
    @BindView(R.id.fragment_register_sp_city)
    AppCompatSpinner fragmentRegisterSpCity;
    @BindView(R.id.fragment_register_ed_phone)
    EditText fragmentRegisterEdPhone;
    @BindView(R.id.fragment_register_ed_password)
    EditText fragmentRegisterEdPassword;
    @BindView(R.id.fragment_register_ed_confirm_password)
    EditText fragmentRegisterEdConfirmPassword;

    private String name, email, birth_date, phone, donation_last_date, password, password_confirmation;
    private int city_id, blood_type_id;

    private static final String TAG = "RegisterFragment";
    private GeneralResponseAdapter governorateAdapter, cityAdapter, bloodTypeAdapter;
    private DateModel dateModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        dateModel = new DateModel("01", "01", "1950", "01-01-1950");

        bloodTypeAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData2(getClient().getbloodTypes(), bloodTypeAdapter, fragmentRegisterSpBloodType, getString(R.string.blood_type), getActivity());

        governorateAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData(getClient().getGovernorates(), governorateAdapter, fragmentRegisterSpGovernorate, getString(R.string.governorate), new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter = new GeneralResponseAdapter(getActivity());
                getSpinnerCityData2(getClient().getCities(position), cityAdapter, fragmentRegisterSpCity, getString(R.string.city), getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    private void registerUser() {
        name = fragmentRegisterEdName.getText().toString();
        email = fragmentRegisterEdEmail.getText().toString();
        birth_date = fragmentRegisterTvBirthDate.getText().toString();
        phone = fragmentRegisterEdPhone.getText().toString();
        donation_last_date = fragmentRegisterTvDonationDate.getText().toString();
        password = fragmentRegisterEdPassword.getText().toString();
        password_confirmation = fragmentRegisterEdConfirmPassword.getText().toString();
        city_id = fragmentRegisterSpCity.getSelectedItemPosition();
        blood_type_id = fragmentRegisterSpBloodType.getSelectedItemPosition();

        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().signup(name, email, birth_date, city_id, phone, donation_last_date,
                password, password_confirmation, blood_type_id).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        showToast(getActivity(), response.body().getMsg());
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


    @OnClick({R.id.fragment_register_tv_birth_date, R.id.fragment_register_tv_donation_date, R.id.fragment_login_btn_login})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_tv_birth_date:
                showCalender(getActivity(), getString(R.string.birth_date), fragmentRegisterTvBirthDate, dateModel);
                break;
            case R.id.fragment_register_tv_donation_date:
                showCalender(getActivity(), getString(R.string.chose_donation_date), fragmentRegisterTvDonationDate, dateModel);
                break;
            case R.id.fragment_login_btn_login:
                registerUser();
                break;
        }
    }

    @Override
    public void onBack() {
        LoginFragment loginFragment = new LoginFragment();
        replace(loginFragment, getActivity().getSupportFragmentManager(), R.id.activity_auth_fl_content);
    }
}
