package com.example.bloodbank.ui.fragment.homeCycle.donations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.local.SharedPreferencesManger.SaveData
import com.example.bloodbank.data.model.donations.Donations
import com.example.bloodbank.databinding.FragmentAddDonationBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.activity.MapsActivity
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods.dismissProgressDialog
import com.example.bloodbank.utils.HelperMethods.getSpinnerCityData
import com.example.bloodbank.utils.HelperMethods.getSpinnerCityData2
import com.example.bloodbank.utils.HelperMethods.showProgressDialog
import com.example.bloodbank.utils.LATITUDE
import com.example.bloodbank.utils.LONGITUDE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDonationFragment : BaseFragment(), View.OnClickListener {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_add_donation, container)

        bloodType()
        governorate()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        latitude = LoadData(activity!!, LATITUDE)
        longitude = LoadData(activity!!, LONGITUDE)
        if (latitude != null && longitude != null) {
            binding.fragmentAddDonationTvHospitalAddress.text = latitude + "" + longitude
        } else {
            binding.fragmentAddDonationTvHospitalAddress.text = getString(R.string.hospital_address)
        }
    }

    private fun bloodType() {
        bloodTypeAdapter = GeneralResponseAdapter(this.activity!!)
        getSpinnerCityData2(client().getbloodTypes(), bloodTypeAdapter!!,
                binding.fragmentAddDonationSpBloodType, getString(R.string.blood_type), activity)
    }

    private fun governorate() {
        governorteAdapter = GeneralResponseAdapter(this.activity!!)
        getSpinnerCityData(client().governorates, governorteAdapter!!,
                binding.fragmentAddDonationSpGovernorate, getString(R.string.governorate), 0, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                citiesAdapter = GeneralResponseAdapter(activity as Context)
                getSpinnerCityData2(client().getCities(position), citiesAdapter!!,
                        binding.fragmentAddDonationSpCity, getString(R.string.city), activity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    private fun openMap() {
        val intent = Intent(activity, MapsActivity::class.java)
        startActivity(intent)
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

    private fun addRequest() {
        checkData()
        showProgressDialog(activity, getString(R.string.please_wait))
        client().addDonationRequset(LoadData(activity!!, API_TOKEN)!!,
                name!!, age!!, bloodTypeId, bagsNum!!, hospitalName!!, hospitalAddress!!,
                cityId, phone!!, notes!!, latitude!!, longitude!!).enqueue(object : Callback<Donations> {
            override fun onResponse(call: Call<Donations>, response: Response<Donations>) {
                dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    SaveData(activity!!, LONGITUDE, "")
                    SaveData(activity!!, LATITUDE, "")
                    activity!!.createToast(response.body()!!.msg!!)
                } else {
                    activity!!.createToast(response.body()!!.msg!!)
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Donations>, t: Throwable) {}
        })
    }

    companion object {
        private const val TAG = "AddDonationFragment"
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_add_donation_btn_hospital_address -> openMap()
            R.id.fragment_add_donation_btn_send -> addRequest()
        }
    }

}