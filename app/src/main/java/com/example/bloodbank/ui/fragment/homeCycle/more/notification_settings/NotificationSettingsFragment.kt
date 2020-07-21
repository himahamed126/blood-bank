package com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.databinding.FragmentNotificationSettingsBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.visible
import com.example.bloodbank.ui.adapter.CheckBoxAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN

class NotificationSettingsFragment : BaseFragment(), NotificationSettingsContract.View, View.OnClickListener {

    private lateinit var bloodTypeAdapter: CheckBoxAdapter
    private lateinit var governorateAdapter: CheckBoxAdapter

    private var bloodTypeList: MutableList<GeneralData>? = mutableListOf()
    private var governorateList: MutableList<GeneralData>? = mutableListOf()
    private var oldBloodTypeList: MutableList<String>? = mutableListOf()
    private var oldGovernorateList: MutableList<String>? = mutableListOf()

    lateinit var binding: FragmentNotificationSettingsBinding
    private lateinit var notificationSettingsPresenter: NotificationSettingsPresenter
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_notification_settings, container)

        init()
        notificationSettingsPresenter = NotificationSettingsPresenter(this)
        notificationSettingsPresenter.requestFillAdapterFromServer(API_TOKEN!!)

        return binding.root
    }

    fun init() {
        loadingDialog = LoadingDialog(activity)

        bloodTypeAdapter = CheckBoxAdapter(this.bloodTypeList!!, oldBloodTypeList!!)
        governorateAdapter = CheckBoxAdapter(this.governorateList!!, oldGovernorateList!!)

        binding.fragmentNotifiSettRvBloodType.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = bloodTypeAdapter
        }
        binding.fragmentNotifiSettRvGovernorate.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = governorateAdapter
        }

        binding.fragmentNotifiSettBtnShowBloodType.setOnClickListener(this)
        binding.fragmentNotifiSettBtnShowGovernorate.setOnClickListener(this)
        binding.fragmentNotifiSettBtnSave.setOnClickListener(this)
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(message: String) {
        requireActivity().createToast(message)
    }

    override fun getBloodTypeList(bloodList: MutableList<GeneralData>) {
        bloodTypeList!!.addAll(bloodList)
        bloodTypeAdapter.notifyDataSetChanged()
    }

    override fun getGovernoratesList(governoratesList: MutableList<GeneralData>) {
        this.governorateList!!.addAll(governoratesList)
        governorateAdapter.notifyDataSetChanged()
    }

    override fun getNotificationSettings(bloodList: MutableList<String>, governorteList: MutableList<String>) {
        oldBloodTypeList!!.addAll(bloodList)
        oldGovernorateList!!.addAll(governorteList)
        governorateAdapter.notifyDataSetChanged()
        bloodTypeAdapter.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_notifi_sett_btn_show_blood_type -> {
                binding.fragmentNotifiSettCvBloodType.visible(binding.fragmentNotifiSettIvImgBloodType)
            }
            R.id.fragment_notifi_sett_btn_show_governorate -> {
                binding.fragmentNotifiSettCvGovernorate.visible(binding.fragmentNotifiSettIvImgGovernorate)
            }
            R.id.fragment_notifi_sett_btn_save -> {
                notificationSettingsPresenter.requestChangeNotificationSetting(
                        API_TOKEN!!, governorateAdapter.newIds, bloodTypeAdapter.newIds)
            }
        }
    }
}