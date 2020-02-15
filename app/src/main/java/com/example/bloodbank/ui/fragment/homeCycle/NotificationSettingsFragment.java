package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.CheckBoxAdapter;
import com.example.bloodbank.data.model.General.GeneralData;
import com.example.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationSettingsFragment extends BaseFragment {
    @BindView(R.id.fragment_notifi_sett_rv_blood_type)
    RecyclerView fragmentNotifiSettRvBloodType;

    private CheckBoxAdapter checkBoxAdapter;
    private List<GeneralData> generalDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
        ButterKnife.bind(this, view);
        intiRec();
        return view;
    }

    private void intiRec() {
        generalDataList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        checkBoxAdapter = new CheckBoxAdapter(getActivity(), generalDataList);
        fragmentNotifiSettRvBloodType.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
