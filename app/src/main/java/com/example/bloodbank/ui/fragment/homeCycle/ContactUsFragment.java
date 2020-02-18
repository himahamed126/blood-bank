package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.contactUs.ContactUs;
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
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class ContactUsFragment extends BaseFragment {
    @BindView(R.id.fragment_contact_us_ed_message_title)
    EditText fragmentContactUsEdMessageTitle;
    @BindView(R.id.fragment_contact_us_ed_message_content)
    EditText fragmentContactUsEdMessageContent;

    private String apiToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);

        apiToken = LoadData(getActivity(), API_TOKEN);
//        apiToken = "Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27";
        return view;
    }

    private void contactUs() {
        String title = fragmentContactUsEdMessageTitle.getText().toString();
        String content = fragmentContactUsEdMessageContent.getText().toString();

        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().contactUs(apiToken, title, content).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    showToast(getActivity(), response.body().getMsg());
                } else {
                    showToast(getActivity(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.fragment_contact_us_btn_send)
    public void onViewClicked() {
        contactUs();
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
