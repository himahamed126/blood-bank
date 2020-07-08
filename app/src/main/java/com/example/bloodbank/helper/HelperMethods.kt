package com.example.bloodbank.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Context
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.data.model.general.GeneralResponse
import org.ocpsoft.prettytime.PrettyTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

object HelperMethods {
    private var checkDialog: ProgressDialog? = null

    @JvmStatic
    fun showToast(activity: Activity?, message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showProgressDialog(activity: Activity?, title: String?) {
        checkDialog = ProgressDialog(activity)
        checkDialog!!.setMessage(title)
        checkDialog!!.isIndeterminate = false
        checkDialog!!.setCancelable(false)
        checkDialog!!.show()
    }

    @JvmStatic
    fun dismissProgressDialog() {
        checkDialog!!.dismiss()
    }

    @JvmStatic
    fun getSpinnerCityData(call: Call<GeneralResponse>, spinnerAdapter: GeneralResponseAdapter, spinner: Spinner,
                           hint: String?, id: Int, listener: OnItemSelectedListener?) {
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                try {
                    if (response.body()!!.status == 1) {
                        spinnerAdapter.setData(response.body()!!.data as MutableList<GeneralData>, hint)
                        spinner.adapter = spinnerAdapter
                        if (id != 0) {
                            spinner.setSelection(id)
                        }
                        spinner.onItemSelectedListener = listener
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    @JvmStatic
    fun getSpinnerCityData2(call: Call<GeneralResponse>, spinnerAdapter: GeneralResponseAdapter, spinner: Spinner,
                            hint: String?, activity: Activity?) {
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                try {
                    if (response.body()!!.status == 1) {
                        spinnerAdapter.setData(response.body()!!.data as MutableList<GeneralData>, hint)
                        spinner.adapter = spinnerAdapter
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    fun getSpinnerWithSelection(call: Call<GeneralResponse>, spinnerAdapter: GeneralResponseAdapter, spinner: Spinner,
                                hint: String?, id: Int) {
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                try {
                    if (response.body()!!.status == 1) {
                        spinnerAdapter.setData(response.body()!!.data as MutableList<GeneralData>, hint)
                        spinner.adapter = spinnerAdapter
                        if (id != 0) {
                            spinner.setSelection(id)
                        }
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    @JvmStatic
    fun showCalender(context: Context?, title: String?, text_view_data: TextView?, data1: DateModel) {
        val mDatePicker = DatePickerDialog(context!!, AlertDialog.THEME_HOLO_DARK, OnDateSetListener { datepicker, selectedYear, selectedMonth, selectedDay ->
            val symbols = DecimalFormatSymbols(Locale.US)
            val mFormat = DecimalFormat("00", symbols)
            val data = (selectedYear.toString() + "-" + String.format(Locale("en"), mFormat.format(java.lang.Double.valueOf((selectedMonth + 1).toDouble()))) + "-"
                    + mFormat.format(java.lang.Double.valueOf(selectedDay.toDouble())))
            data1.date_txt = data
            data1.day = mFormat.format(java.lang.Double.valueOf(selectedDay.toDouble()))
            data1.month = mFormat.format(java.lang.Double.valueOf(selectedMonth + 1.toDouble()))
            data1.year = selectedYear.toString()
            if (text_view_data != null) {
                text_view_data.text = data
            }
        }, data1.year.toInt(), data1.month.toInt() - 1, data1.day.toInt())
        mDatePicker.setTitle(title)
        mDatePicker.show()
    }


    @JvmStatic
    fun onLoadImageFromUrl(imageView: ImageView?, URL: String?, context: Context?) {
        if (context != null) {
            if (imageView != null) {
                Glide.with(context).load(URL).into(imageView)
            }
        }
    }

    fun setTimeAgo(createdAt: String): String {
        var date: Date? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
        try {
            date = sdf.parse(createdAt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val prettyTime = PrettyTime()
        return prettyTime.format(date)
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<GeneralResponse>) {

}