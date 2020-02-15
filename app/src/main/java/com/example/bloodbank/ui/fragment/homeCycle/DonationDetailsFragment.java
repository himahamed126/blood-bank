package com.example.bloodbank.ui.fragment.homeCycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.donations.DonationData;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonationDetailsFragment extends BaseFragment {
    @BindView(R.id.fragment_donation_details_tv_tb_name)
    TextView fragmentDonationDetailsTvTbName;
    @BindView(R.id.fragment_donation_details_tv_name)
    TextView fragmentDonationDetailsTvName;
    @BindView(R.id.fragment_donation_details_tv_age)
    TextView fragmentDonationDetailsTvAge;
    @BindView(R.id.fragment_donation_details_tv_blood_type)
    TextView fragmentDonationDetailsTvBloodType;
    @BindView(R.id.fragment_donation_details_tv_hospital)
    TextView fragmentDonationDetailsTvHospital;
    @BindView(R.id.fragment_donation_details_tv_address)
    TextView fragmentDonationDetailsTvAddress;
    @BindView(R.id.fragment_donation_details_tv_phone)
    TextView fragmentDonationDetailsTvPhone;
    @BindView(R.id.fragment_donation_details_tv_blood_count)
    TextView fragmentDonationDetailsTvBloodCount;
    @BindView(R.id.fragment_donation_details_iv_map)
    ImageView fragmentDonationDetailsIvMap;
    @BindView(R.id.fragment_donation_details_btn_call)
    TextView fragmentDonationDetailsBtnCall;

    public DonationData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        ButterKnife.bind(this, view);

        setData();

        return view;
    }

    private void setData() {
        fragmentDonationDetailsTvTbName.setText(data.getPatientName());
        fragmentDonationDetailsTvName.setText(data.getPatientName());
        fragmentDonationDetailsTvAge.setText(data.getPatientAge());
        fragmentDonationDetailsTvBloodType.setText(data.getBloodType().getName());
        fragmentDonationDetailsTvHospital.setText(data.getHospitalName());
        fragmentDonationDetailsTvAddress.setText(data.getHospitalAddress());
        fragmentDonationDetailsTvPhone.setText(data.getClient().getPhone());
    }

    @OnClick(R.id.fragment_donation_details_btn_call)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + data.getClient().getPhone()));
        startActivity(intent);
    }

    @Override
    public void onBack() {
        baseActivity.superBackPressed();
    }
}
