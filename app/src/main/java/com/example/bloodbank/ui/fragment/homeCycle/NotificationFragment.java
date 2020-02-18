package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.NotificationAdapter;
import com.example.bloodbank.data.model.notifications.Notification;
import com.example.bloodbank.data.model.notifications.NotificationData;
import com.example.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;

public class NotificationFragment extends BaseFragment {
    @BindView(R.id.fragment_notification_rv)
    RecyclerView fragmentNotificationRv;

    private List<NotificationData> notificationData;
    private NotificationAdapter notificationAdapter;
    private String TAG = "notification";
    private String apiToken;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);

        apiToken = LoadData(getActivity(), API_TOKEN);
//        apiToken = "W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl";

        getNotification(1);

        initRecyc();
        return view;
    }

    private void initRecyc() {
        notificationData = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationAdapter = new NotificationAdapter(getActivity(), notificationData);
        fragmentNotificationRv.setLayoutManager(linearLayoutManager);
        fragmentNotificationRv.setAdapter(notificationAdapter);
    }


    private void getNotification(int page) {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getNotifications(apiToken, page).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        notificationData.addAll(response.body().getData().getData());
                        notificationAdapter.notifyDataSetChanged();
                    } else {
                        Log.i(TAG, response.body().getMsg());
                    }
                } catch (
                        Exception e) {
                    Log.i(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
