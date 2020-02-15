package com.example.bloodbank.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.bloodbank.R;
import com.example.bloodbank.adapter.GeneralResponseAdapter;
import com.example.bloodbank.data.model.General.GenaralResponse;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperMethods {
    private static ProgressDialog checkDialog;


    public static void replace(Fragment fragment, FragmentManager manager, int id) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressDialog(Activity activity, String title) {
        checkDialog = new ProgressDialog(activity);
        checkDialog.setMessage(title);
        checkDialog.setIndeterminate(false);
        checkDialog.setCancelable(false);

        checkDialog.show();
    }

    public static void dismissProgressDialog() {
        checkDialog.dismiss();
    }

    public static void getSpinnerCityData(Call<GenaralResponse> call, GeneralResponseAdapter spinnerAdapter, Spinner spinner,
                                          String hint, AdapterView.OnItemSelectedListener listener) {

        call.enqueue(new Callback<GenaralResponse>() {
            @Override
            public void onResponse(Call<GenaralResponse> call, Response<GenaralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);
                        spinner.setOnItemSelectedListener(listener);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GenaralResponse> call, Throwable t) {
            }
        });
    }

    public static void getSpinnerCityData2(Call<GenaralResponse> call, GeneralResponseAdapter spinnerAdapter, Spinner spinner,
                                           String hint, Activity activity) {
//        showProgressDialog(activity, activity.getString(R.string.please_wait));
        call.enqueue(new Callback<GenaralResponse>() {
            @Override
            public void onResponse(Call<GenaralResponse> call, Response<GenaralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
//                        dismissProgressDialog();
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GenaralResponse> call, Throwable t) {
            }
        });
    }

    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {

        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat mFormat = new DecimalFormat("00", symbols);
                String data = selectedYear + "-" + String.format(new Locale("en"), mFormat.format(Double.valueOf((selectedMonth + 1)))) + "-"
                        + mFormat.format(Double.valueOf(selectedDay));
                data1.setDate_txt(data);
                data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                data1.setYear(String.valueOf(selectedYear));
                if (text_view_data != null) {
                    text_view_data.setText(data);
                }
            }
        }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URL, Context context) {
        Glide.with(context).load(URL).into(imageView);
    }
}
