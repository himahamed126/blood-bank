package com.example.bloodbank.ui.fragment.homeCycle;

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
import com.example.bloodbank.data.model.login.ClientData;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.helper.DateModel;
import com.example.bloodbank.helper.HelperMethods;
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
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PASSWORD;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.loadUserData;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData2;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class EditProfileFragment extends BaseFragment {
    @BindView(R.id.fragment_edit_profile_ed_name)
    EditText fragmentEditProfileEdName;
    @BindView(R.id.fragment_edit_profile_ed_email)
    EditText fragmentEditProfileEdEmail;
    @BindView(R.id.fragment_edit_profile_tv_birth_date)
    TextView fragmentEditProfileTvBirthDate;
    @BindView(R.id.fragment_edit_profile_sp_blood_type)
    AppCompatSpinner fragmentEditProfileSpBloodType;
    @BindView(R.id.fragment_edit_profile_tv_donation_date)
    TextView fragmentEditProfileTvDonationDate;
    @BindView(R.id.fragment_edit_profile_sp_governorate)
    AppCompatSpinner fragmentEditProfileSpGovernorate;
    @BindView(R.id.fragment_edit_profile_sp_city)
    AppCompatSpinner fragmentEditProfileSpCity;
    @BindView(R.id.fragment_edit_profile_ed_phone)
    EditText fragmentEditProfileEdPhone;
    @BindView(R.id.fragment_edit_profile_ed_password)
    EditText fragmentEditProfileEdPassword;
    @BindView(R.id.fragment_edit_profile_ed_confirm_password)
    EditText fragmentEditProfileEdConfirmPassword;

    private static final String TAG = "EditProfileFragment";
    private String name, email, birth_date, phone, donation_last_date, password, password_confirmation;
    private int city_id, blood_type_id;
    private GeneralResponseAdapter governorateAdapter, cityAdapter, bloodTypeAdapter;
    private DateModel dateModel;

    private ClientData clientData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        clientData = loadUserData(getActivity());
        executSpinner();
        setData();
        return view;
    }

    private void executSpinner() {
        bloodTypeAdapter = new GeneralResponseAdapter(getActivity());
        governorateAdapter = new GeneralResponseAdapter(getActivity());
        cityAdapter = new GeneralResponseAdapter(getActivity());

        getSpinnerCityData2(getClient().getbloodTypes(), bloodTypeAdapter, fragmentEditProfileSpBloodType, getString(R.string.blood_type), getActivity());
        getSpinnerCityData(getClient().getGovernorates(), governorateAdapter, fragmentEditProfileSpGovernorate, getString(R.string.governorate), new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fragmentEditProfileSpCity.setSelection(clientData.getClient().getCity().getId());
                getSpinnerCityData2(getClient().getCities(position), cityAdapter, fragmentEditProfileSpCity, getString(R.string.city), getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentEditProfileSpBloodType.setSelection(clientData.getClient().getBloodType().getId());
        fragmentEditProfileSpGovernorate.setSelection(Integer.parseInt(clientData.getClient().getCity().getGovernorateId()));
        fragmentEditProfileSpCity.setSelection(clientData.getClient().getCity().getId());
    }

    private void setData() {
        fragmentEditProfileEdName.setText(clientData.getClient().getName());
        fragmentEditProfileEdEmail.setText(clientData.getClient().getEmail());
        fragmentEditProfileTvBirthDate.setText(clientData.getClient().getBirthDate());
        fragmentEditProfileTvDonationDate.setText(clientData.getClient().getDonationLastDate());
        fragmentEditProfileEdPhone.setText(clientData.getClient().getPhone());
        fragmentEditProfileEdPassword.setText(LoadData(getActivity(), PASSWORD));
        fragmentEditProfileEdConfirmPassword.setText(LoadData(getActivity(), PASSWORD));


    }

    private void editProfile() {
//        name = fragmentRegisterEdName.getText().toString();
//        email = fragmentRegisterEdEmail.getText().toString();
//        birth_date = fragmentRegisterTvBirthDate.getText().toString();
//        phone = fragmentRegisterEdPhone.getText().toString();
//        donation_last_date = fragmentRegisterTvDonationDate.getText().toString();
//        password = fragmentRegisterEdPassword.getText().toString();
//        password_confirmation = fragmentRegisterEdConfirmPassword.getText().toString();
//        city_id = fragmentRegisterSpCity.getSelectedItemPosition();
//        blood_type_id = fragmentRegisterSpBloodType.getSelectedItemPosition();

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

    @OnClick({R.id.fragment_edit_profile_tv_birth_date, R.id.fragment_edit_profile_tv_donation_date, R.id.fragment_edit_profile_btn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_edit_profile_tv_birth_date:
                break;
            case R.id.fragment_edit_profile_tv_donation_date:
                break;
            case R.id.fragment_edit_profile_btn_edit:
                editProfile();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
