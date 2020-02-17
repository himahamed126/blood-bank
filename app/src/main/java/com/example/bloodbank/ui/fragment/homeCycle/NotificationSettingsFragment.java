package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.CheckBoxAdapter;
import com.example.bloodbank.data.model.General.GenaralResponse;
import com.example.bloodbank.data.model.General.GeneralData;
import com.example.bloodbank.data.model.notificationSettings.NotificationSettings;
import com.example.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class NotificationSettingsFragment extends BaseFragment {
    @BindView(R.id.fragment_notifi_sett_rv_blood_type)
    RecyclerView fragmentNotifiSettRvBloodType;
    @BindView(R.id.fragment_notifi_sett_rv_governorate)
    RecyclerView fragmentNotifiSettRvGovernorate;
    @BindView(R.id.fragment_notifi_sett_cv_blood_type)
    CardView fragmentNotifiSettCvBloodType;
    @BindView(R.id.fragment_notifi_sett_cv_governorate)
    CardView fragmentNotifiSettCvGovernorate;
    @BindView(R.id.fragment_notifi_sett_iv_img_blood_type)
    ImageView fragmentNotifiSettIvImgBloodType;
    @BindView(R.id.fragment_notifi_sett_iv_img_governorate)
    ImageView fragmentNotifiSettIvImgGovernorate;

    private CheckBoxAdapter bloodTypeAdapter, governorateAdapter;
    private List<String> oldBloodTypeList, oldGovernorateList;

    private static final String TAG = "NotificationSettingsFra";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        fragmentNotifiSettRvBloodType.setLayoutManager(gridLayoutManager);
        fragmentNotifiSettRvGovernorate.setLayoutManager(gridLayoutManager1);

        getNotificationSettings();
        return view;
    }


    private void getBloodType() {
        getClient().getbloodTypes().enqueue(new Callback<GenaralResponse>() {
            @Override
            public void onResponse(Call<GenaralResponse> call, Response<GenaralResponse> response) {
                if (response.body().getStatus() == 1) {
                    bloodTypeAdapter = new CheckBoxAdapter(getActivity(), response.body().getData(), oldBloodTypeList);
                    fragmentNotifiSettRvBloodType.setAdapter(bloodTypeAdapter);
                }
            }

            @Override
            public void onFailure(Call<GenaralResponse> call, Throwable t) {

            }
        });
    }

    private void getGovernorates() {
        getClient().getGovernorates().enqueue(new Callback<GenaralResponse>() {
            @Override
            public void onResponse(Call<GenaralResponse> call, Response<GenaralResponse> response) {
                if (response.body().getStatus() == 1) {
                    governorateAdapter = new CheckBoxAdapter(getActivity(), response.body().getData(), oldGovernorateList);
                    fragmentNotifiSettRvGovernorate.setAdapter(governorateAdapter);
                }
            }

            @Override
            public void onFailure(Call<GenaralResponse> call, Throwable t) {

            }
        });
    }

    private void getNotificationSettings() {
        oldBloodTypeList = new ArrayList<>();
        oldGovernorateList = new ArrayList<>();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getNotificationSettings(LoadData(getActivity(), API_TOKEN)).enqueue(new Callback<NotificationSettings>() {
            @Override
            public void onResponse(Call<NotificationSettings> call, Response<NotificationSettings> response) {
                if (response.body().getStatus() == 1) {
                    dismissProgressDialog();
                    try {
                        oldBloodTypeList.addAll(response.body().getData().getBloodTypes());
                        oldGovernorateList.addAll(response.body().getData().getGovernorates());
                        getBloodType();
                        getGovernorates();

                        Log.i(TAG, " old " + oldBloodTypeList);
                    } catch (Exception e) {
                        Log.i(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationSettings> call, Throwable t) {

            }
        });
    }

    private void changeNotificationSettings() {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().changeNotificationSettings(LoadData(getActivity(), API_TOKEN), governorateAdapter.newIds, bloodTypeAdapter.newIds).enqueue(new Callback<NotificationSettings>() {
            @Override
            public void onResponse(Call<NotificationSettings> call, Response<NotificationSettings> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    showToast(getActivity(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<NotificationSettings> call, Throwable t) {

            }
        });
    }

    private void visible(View view, ImageView imageView) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_remove);
        } else {
            view.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.ic_add);
        }
    }

    @OnClick({R.id.fragment_notifi_sett_btn_show_blood_type, R.id.fragment_notifi_sett_btn_show_governorate, R.id.fragment_notifi_sett_btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_notifi_sett_btn_show_blood_type:
                visible(fragmentNotifiSettCvBloodType, fragmentNotifiSettIvImgBloodType);
                break;
            case R.id.fragment_notifi_sett_btn_show_governorate:
                visible(fragmentNotifiSettCvGovernorate, fragmentNotifiSettIvImgGovernorate);
                break;
            case R.id.fragment_notifi_sett_btn_save:
                changeNotificationSettings();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
