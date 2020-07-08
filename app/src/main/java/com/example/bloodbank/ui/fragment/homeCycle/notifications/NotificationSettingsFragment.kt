package com.example.bloodbank.ui.fragment.homeCycle.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.data.model.general.GeneralResponse
import com.example.bloodbank.data.model.notificationSettings.NotificationSettings
import com.example.bloodbank.databinding.FragmentNotificationSettingsBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.visible
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.HelperMethods.dismissProgressDialog
import com.example.bloodbank.helper.HelperMethods.showProgressDialog
import com.example.bloodbank.helper.HelperMethods.showToast
import com.example.bloodbank.ui.adapter.CheckBoxAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationSettingsFragment : BaseFragment(), View.OnClickListener {

    private var bloodTypeAdapter: CheckBoxAdapter? = null
    private var governorateAdapter: CheckBoxAdapter? = null
    private lateinit var oldBloodTypeList: MutableList<String>
    private lateinit var oldGovernorateList: MutableList<String>

    lateinit var binding: FragmentNotificationSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_notification_settings, container)

        val gridLayoutManager = GridLayoutManager(activity, 3)
        val gridLayoutManager1 = GridLayoutManager(activity, 3)
        binding.fragmentNotifiSettRvBloodType.layoutManager = gridLayoutManager
        binding.fragmentNotifiSettRvGovernorate.layoutManager = gridLayoutManager1
        notificationSettings()
        binding.fragmentNotifiSettBtnShowBloodType.setOnClickListener(this)
        binding.fragmentNotifiSettBtnShowGovernorate.setOnClickListener(this)
        binding.fragmentNotifiSettBtnSave.setOnClickListener(this)

        return binding.root
    }

    fun bloodType() {
        client.getbloodTypes().enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                if (response.body()!!.status == 1) {
                    bloodTypeAdapter = CheckBoxAdapter((response.body()!!.data as MutableList<GeneralData>?)!!, oldBloodTypeList)
                    binding.fragmentNotifiSettRvBloodType.adapter = bloodTypeAdapter
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    fun governorates() {
        client.governorates.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                if (response.body()!!.status == 1) {
                    governorateAdapter = CheckBoxAdapter((response.body()!!.data as MutableList<GeneralData>?)!!, oldGovernorateList)
                    binding.fragmentNotifiSettRvGovernorate.adapter = governorateAdapter
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    private fun notificationSettings() {
        oldBloodTypeList = mutableListOf()
        oldGovernorateList = mutableListOf()
        showProgressDialog(activity, getString(R.string.please_wait))
        client.getNotificationSettings(LoadData(activity!!, API_TOKEN)!!).enqueue(object : Callback<NotificationSettings> {
            override fun onResponse(call: Call<NotificationSettings>, response: Response<NotificationSettings>) {
                if (response.body()!!.status == 1) {
                    dismissProgressDialog()
                    try {
                        oldBloodTypeList.addAll(response.body()!!.data!!.bloodTypes!!)
                        oldGovernorateList.addAll(response.body()!!.data!!.governorates!!)
                        bloodType()
                        governorates()
                        Log.i(TAG, " old $oldBloodTypeList")
                    } catch (e: Exception) {
                        Log.i(TAG, "onResponse: " + e.message)
                    }
                }
            }

            override fun onFailure(call: Call<NotificationSettings>, t: Throwable) {}
        })
    }

    private fun changeNotificationSettings() {
        showProgressDialog(activity, getString(R.string.please_wait))
        client.changeNotificationSettings(LoadData(activity!!, API_TOKEN)!!, governorateAdapter!!.newIds, bloodTypeAdapter!!.newIds).enqueue(object : Callback<NotificationSettings> {
            override fun onResponse(call: Call<NotificationSettings>, response: Response<NotificationSettings>) {
                dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    showToast(activity, response.body()!!.msg)
                }
            }

            override fun onFailure(call: Call<NotificationSettings>, t: Throwable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_notifi_sett_btn_show_blood_type -> binding.fragmentNotifiSettCvBloodType.visible(binding.fragmentNotifiSettIvImgBloodType)
            R.id.fragment_notifi_sett_btn_show_governorate -> binding.fragmentNotifiSettCvGovernorate.visible(binding.fragmentNotifiSettIvImgGovernorate)
            R.id.fragment_notifi_sett_btn_save -> changeNotificationSettings()
        }
    }

    companion object {
        private const val TAG = "NotificationSettingsFra"
    }
}