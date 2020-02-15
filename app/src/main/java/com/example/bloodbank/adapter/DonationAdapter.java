package com.example.bloodbank.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.donations.DonationData;
import com.example.bloodbank.helper.HelperMethods;
import com.example.bloodbank.ui.fragment.homeCycle.DonationDetailsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.bloodbank.helper.HelperMethods.replace;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
    private Context context;
    private List<DonationData> donationsList;
    int REQUEST_PHONE_CALL = 123;

    public DonationAdapter(Context context, List donationsList) {
        this.context = context;
        this.donationsList = donationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        DonationData donation = donationsList.get(position);
        holder.itemDonationTvBloodType.setText(donation.getBloodType().getName());
        holder.itemDonationTvName.setText(donation.getClient().getName());
        holder.itemDonationTvHospital.setText(donation.getHospitalName());
        holder.itemDonationTvCity.setText(donation.getCity().getName());
    }

    private void setAction(ViewHolder holder, int position) {
        DonationData donation = donationsList.get(position);
        holder.itemDonationIvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonationDetailsFragment donationDetailsFragment = new DonationDetailsFragment();
                donationDetailsFragment.data = donation;
                replace(donationDetailsFragment, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_home_fl_content);
            }
        });
        holder.itemDonationIvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + donation.getClient().getPhone()));

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    context.startActivity(call);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_donation_tv_name)
        TextView itemDonationTvName;
        @BindView(R.id.item_donation_tv_hospital)
        TextView itemDonationTvHospital;
        @BindView(R.id.item_donation_tv_city)
        TextView itemDonationTvCity;
        @BindView(R.id.item_donation_tv_blood_type)
        TextView itemDonationTvBloodType;
        @BindView(R.id.item_donation_iv_info)
        ImageView itemDonationIvInfo;
        @BindView(R.id.item_donation_iv_phone)
        ImageView itemDonationIvPhone;

        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
