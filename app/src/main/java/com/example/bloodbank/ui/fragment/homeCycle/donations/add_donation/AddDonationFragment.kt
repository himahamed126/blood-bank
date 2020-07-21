package com.example.bloodbank.ui.fragment.homeCycle.donations.add_donation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.databinding.FragmentAddDonationBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.activity.MapsActivity
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods.getSpinnerCityData2
import com.example.bloodbank.utils.HelperMethods.setSpinnerWithListener
import com.example.bloodbank.utils.LATITUDE
import com.example.bloodbank.utils.LONGITUDE

class AddDonationFragment : BaseFragment(), AddDonationContract.View, View.OnClickListener {

    private var bloodTypeAdapter: GeneralResponseAdapter? = null
    private var governorteAdapter: GeneralResponseAdapter? = null
    private var citiesAdapter: GeneralResponseAdapter? = null
    private var name: String? = null
    private var age: String? = null
    private var bagsNum: String? = null
    private var hospitalName: String? = null
    private var hospitalAddress: String? = null
    private var phone: String? = null
    private var notes: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var bloodTypeId = 0
    private var cityId = 0

    lateinit var binding: FragmentAddDonationBinding
    lateinit var addDonationPresenter: AddDonationPresenter
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_add_donation, container)
        addDonationPresenter = AddDonationPresenter(this)
        bloodType()
        governorate()

        binding.fragmentAddDonationBtnHospitalAddress.setOnClickListener(this)
        binding.fragmentAddDonationBtnSend.setOnClickListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadingDialog = LoadingDialog(activity)
        latitude = SharedPreferencesManger(this.requireActivity()).restoreStringValue(LATITUDE)!!
        longitude = SharedPreferencesManger(this.requireActivity()).restoreStringValue(LONGITUDE)!!
        if (latitude != null && longitude != null) {
            binding.fragmentAddDonationTvHospitalAddress.text = "$latitude $longitude"
        } else {
            binding.fragmentAddDonationTvHospitalAddress.text = getString(R.string.hospital_address)
        }
    }

    private fun bloodType() {
        bloodTypeAdapter = GeneralResponseAdapter(this.requireActivity())
        getSpinnerCityData2(client().getbloodTypes(), bloodTypeAdapter!!,
                binding.fragmentAddDonationSpBloodType, getString(R.string.blood_type), activity)
    }

    private fun governorate() {
        governorteAdapter = GeneralResponseAdapter(this.requireActivity())
        setSpinnerWithListener(client().governorates, governorteAdapter!!,
                binding.fragmentAddDonationSpGovernorate, getString(R.string.governorate), 0, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                citiesAdapter = GeneralResponseAdapter(activity as Context)
                getSpinnerCityData2(client().getCities(position), citiesAdapter!!,
                        binding.fragmentAddDonationSpCity, getString(R.string.city), activity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    private fun checkData() {
        name = binding.fragmentAddDonationEdName.text.toString()
        age = binding.fragmentAddDonationEdAge.text.toString()
        bagsNum = binding.fragmentAddDonationEdBloodCount.text.toString()
        hospitalName = binding.fragmentAddDonationEdHospitalName.text.toString()
        hospitalAddress = binding.fragmentAddDonationTvHospitalAddress.text.toString()
        phone = binding.fragmentAddDonationEdPhone.text.toString()
        notes = binding.fragmentAddDonationEdNote.text.toString()
        bloodTypeId = binding.fragmentAddDonationSpBloodType.selectedItemPosition
        cityId = binding.fragmentAddDonationSpCity.selectedItemPosition
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(throwable: Throwable) {
        requireActivity().createToast(throwable.toString())
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_add_donation_btn_hospital_address -> {
                startActivity(Intent(activity, MapsActivity::class.java))
            }
            R.id.fragment_add_donation_btn_send -> {
                checkData()
                addDonationPresenter.requestAddDonationRequest(
                        (API_TOKEN!!),
                        name!!, age!!, bloodTypeId, bagsNum!!, hospitalName!!, hospitalAddress!!,
                        cityId, phone!!, notes!!, latitude!!, longitude!!, this.requireActivity()
                )
            }
        }
    }
}