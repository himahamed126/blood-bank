package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.DonationAdapter;
import com.example.bloodbank.adapter.GeneralResponseAdapter;
import com.example.bloodbank.data.model.donations.DonationData;
import com.example.bloodbank.data.model.donations.Donations;
import com.example.bloodbank.helper.OnEndLess;
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
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData2;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;

public class DonationFragment extends BaseFragment {
    @BindView(R.id.fragment_donation_sp_blood_type)
    Spinner fragmentDonationSpBloodType;
    @BindView(R.id.fragment_donation_sp_city)
    Spinner fragmentDonationSpCity;
    @BindView(R.id.fragment_donation_rv_donation_request)
    RecyclerView fragmentDonationRvDonationRequest;


    private DonationAdapter donationAdapter;
    private List<DonationData> donationsList;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage;
    private static final String TAG = "DonationFragment";

    private GeneralResponseAdapter bloodTypeAdapter, cityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        ButterKnife.bind(this, view);

        getGovernoters();
        getBloodType();
        getAllDonation(1);
        initRec();
        return view;
    }

    private void getGovernoters() {
        cityAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData2(getClient().getGovernorates(), cityAdapter, fragmentDonationSpCity,
                getString(R.string.governorate), getActivity());
    }

    private void getBloodType() {
        bloodTypeAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData(getClient().getbloodTypes(), bloodTypeAdapter, fragmentDonationSpBloodType, getString(R.string.blood_type), 0, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAllDonationWithFilter(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRec() {


        int resId = R.anim.layou_animation_left_to_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        fragmentDonationRvDonationRequest.setLayoutAnimation(animation);


//        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(),
//                R.anim.layou_animation_left_to_right);
//        fragmentDonationRvDonationRequest.setLayoutAnimation(controller);
//        fragmentDonationRvDonationRequest.getAdapter().notifyDataSetChanged();
//        fragmentDonationRvDonationRequest.scheduleLayoutAnimation();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentDonationRvDonationRequest.setLayoutManager(linearLayoutManager);
        donationsList = new ArrayList<>();
        donationAdapter = new DonationAdapter(getActivity(), donationsList);
        fragmentDonationRvDonationRequest.setAdapter(donationAdapter);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getAllDonation(current_page);
                        donationAdapter.notifyDataSetChanged();
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentDonationRvDonationRequest.addOnScrollListener(onEndLess);

    }

    private void getAllDonation(int page) {
//        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getAllDonation(LoadData(getActivity(), API_TOKEN), page).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
//                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    donationsList.addAll(response.body().getData().getData());
                    maxPage = response.body().getData().getLastPage();
                    donationAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {

            }
        });
    }

    private void getAllDonationWithFilter(int page) {
        donationsList = new ArrayList<>();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getAllDonationWithFilter(LoadData(getActivity(), API_TOKEN),
                fragmentDonationSpBloodType.getSelectedItemPosition(), fragmentDonationSpCity.getSelectedItemPosition(),
                page).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    donationsList.addAll(response.body().getData().getData());
                    maxPage = response.body().getData().getLastPage();
                    donationAdapter = new DonationAdapter(getActivity(), donationsList);
                    fragmentDonationRvDonationRequest.setAdapter(donationAdapter);
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.fragment_donation_fab_add)
    public void onViewClicked() {
        AddDonationFragment addDonationFragment = new AddDonationFragment();
        replace(addDonationFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
    }

    @Override
    public void onBack() {
    }
}
