package com.example.bloodbank.ui.fragment.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.GeneralResponseAdapter;
import com.example.bloodbank.data.model.donations.Donations;
import com.example.bloodbank.ui.activity.MapsActivity;
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
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData2;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class AddDonationFragment extends BaseFragment {
    @BindView(R.id.fragment_add_donation_ed_name)
    EditText fragmentAddDonationEdName;
    @BindView(R.id.fragment_add_donation_ed_age)
    EditText fragmentAddDonationEdAge;
    @BindView(R.id.fragment_add_donation_sp_blood_type)
    AppCompatSpinner fragmentAddDonationSpBloodType;
    @BindView(R.id.fragment_add_donation_ed_blood_count)
    EditText fragmentAddDonationEdBloodCount;
    @BindView(R.id.fragment_add_donation_ed_hospital_name)
    EditText fragmentAddDonationEdHospitalName;
    @BindView(R.id.fragment_add_donation_ed_hospital_address)
    EditText fragmentAddDonationEdHospitalAddress;
    @BindView(R.id.fragment_add_donation_sp_governorate)
    Spinner fragmentAddDonationSpGovernorate;
    @BindView(R.id.fragment_add_donation_sp_city)
    Spinner fragmentAddDonationSpCity;
    @BindView(R.id.fragment_add_donation_ed_phone)
    EditText fragmentAddDonationEdPhone;
    @BindView(R.id.fragment_add_donation_ed_note)
    EditText fragmentAddDonationEdNote;
    @BindView(R.id.fragment_add_donation_tv_hospital_address)
    TextView fragmentAddDonationTvHospitalAddress;


    private GeneralResponseAdapter bloodTypeAdapter, governorteAdapter, citiesAdapter;
    private String name, age, bagsNum, hospitalName, hospitalAddress, phone, notes, latitude, longitude;
    private int bloodTypeId, cityId;
    private static final String TAG = "AddDonationFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_add_donation, container, false);
        ButterKnife.bind(this, view);

        getBloodType();
        getGovernorate();
        return view;
    }

    private void getBloodType() {
        bloodTypeAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData2(getClient().getbloodTypes(), bloodTypeAdapter,
                fragmentAddDonationSpBloodType, getString(R.string.blood_type), getActivity());
    }

    private void getGovernorate() {
        governorteAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData(getClient().getGovernorates(), governorteAdapter,
                fragmentAddDonationSpGovernorate, getString(R.string.governorate), new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        citiesAdapter = new GeneralResponseAdapter(getActivity());
                        getSpinnerCityData2(getClient().getCities(position), citiesAdapter,
                                fragmentAddDonationSpCity, getString(R.string.city), getActivity());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }

    private void openMap() {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
    }

    private void checkData() {
        name = fragmentAddDonationEdName.getText().toString();
        age = fragmentAddDonationEdAge.getText().toString();
        bagsNum = fragmentAddDonationEdBloodCount.getText().toString();
        hospitalName = fragmentAddDonationEdHospitalAddress.getText().toString();
        hospitalName = fragmentAddDonationEdHospitalName.getText().toString();
        hospitalAddress = fragmentAddDonationEdHospitalAddress.getText().toString();
        phone = fragmentAddDonationEdPhone.getText().toString();
        notes = fragmentAddDonationEdNote.getText().toString();
        bloodTypeId = fragmentAddDonationSpBloodType.getSelectedItemPosition();
        cityId = fragmentAddDonationSpCity.getSelectedItemPosition();
    }

    private void addRequest() {
        checkData();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().addDonationRequset(LoadData(getActivity(), API_TOKEN),
                name, age, bloodTypeId, bagsNum, hospitalName, hospitalAddress,
                cityId, phone, notes, latitude, longitude).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    showToast(getActivity(), response.body().getMsg());
                } else {
                    showToast(getActivity(), response.body().getMsg());
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.fragment_add_donation_btn_hospital_address, R.id.fragment_add_donation_btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_add_donation_btn_hospital_address:
                openMap();
                break;
            case R.id.fragment_add_donation_btn_send:
                addRequest();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
