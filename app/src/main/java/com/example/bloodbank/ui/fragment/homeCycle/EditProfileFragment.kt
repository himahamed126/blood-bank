package com.example.bloodbank.ui.fragment.homeCycle

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
import com.example.bloodbank.data.local.SharedPreferencesManger.loadUserData
import com.example.bloodbank.data.model.login.ClientData
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.databinding.FragmentEditProfileBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.DateModel
import com.example.bloodbank.helper.HelperMethods.dismissProgressDialog
import com.example.bloodbank.helper.HelperMethods.getSpinnerCityData
import com.example.bloodbank.helper.HelperMethods.getSpinnerWithSelection
import com.example.bloodbank.helper.HelperMethods.showCalender
import com.example.bloodbank.helper.HelperMethods.showProgressDialog
import com.example.bloodbank.helper.HelperMethods.showToast
import com.example.bloodbank.helper.PASSWORD
import com.example.bloodbank.helper.USER_DATA
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileFragment : BaseFragment(), View.OnClickListener {

    private var name: String? = null
    private var email: String? = null
    private var birth_date: String? = null
    private var phone: String? = null
    private var donation_last_date: String? = null
    private var password_confirmation: String? = null
    private var city_id = 0
    private var blood_type_id = 0
    private var governorateAdapter: GeneralResponseAdapter? = null
    private var cityAdapter: GeneralResponseAdapter? = null
    private var bloodTypeAdapter: GeneralResponseAdapter? = null
    private var dateModel: DateModel? = null
    private var clientData: ClientData? = null
    private var password: String? = null
    private var loadPassword: String? = null

    lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_edit_profile, container)
        dateModel = DateModel("01", "01", "1970", "01-01-1970")
        clientData = loadUserData(activity!!)
        loadPassword = LoadData(activity!!, PASSWORD)
        binding.clientData = clientData
        binding.password = loadPassword
        executSpinner()
        return binding.root
    }

    private fun executSpinner() {
        bloodTypeAdapter = GeneralResponseAdapter(activity)
        governorateAdapter = GeneralResponseAdapter(activity)
        cityAdapter = GeneralResponseAdapter(activity)
        getSpinnerWithSelection(client.getbloodTypes(), bloodTypeAdapter!!, binding.fragmentEditProfileSpBloodType, getString(R.string.blood_type),
                clientData!!.client!!.bloodType!!.id)
        getSpinnerCityData(client.governorates, governorateAdapter!!, binding.fragmentEditProfileSpGovernorate, getString(R.string.governorate),
                clientData!!.client!!.city!!.id, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
//                fragmentEditProfileSpCity.setSelection(clientData.getClient().getCity().getId());
                getSpinnerWithSelection(client.getCities(position), cityAdapter!!, binding.fragmentEditProfileSpCity, getString(R.string.city), clientData!!.client!!.city!!.governorateId!!.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        binding.fragmentEditProfileSpGovernorate.setSelection(clientData!!.client!!.city!!.governorateId!!.toInt())
        binding.fragmentEditProfileSpCity.setSelection(clientData!!.client!!.city!!.id)
    }

    private fun editProfile() {
        name = binding.fragmentEditProfileEdName.text.toString()
        email = binding.fragmentEditProfileEdEmail.text.toString()
        birth_date = binding.fragmentEditProfileTvBirthDate.text.toString()
        phone = binding.fragmentEditProfileEdPhone.text.toString()
        donation_last_date = binding.fragmentEditProfileTvDonationDate.text.toString()
        password = binding.fragmentEditProfileEdPassword.text.toString()
        password_confirmation = binding.fragmentEditProfileEdConfirmPassword.text.toString()
        city_id = binding.fragmentEditProfileSpCity.selectedItemPosition
        blood_type_id = binding.fragmentEditProfileSpBloodType.selectedItemPosition
        showProgressDialog(activity, getString(R.string.please_wait))
        client.signup(name!!, email!!, birth_date!!, city_id, phone!!, donation_last_date!!,
                password!!, password_confirmation!!, blood_type_id).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                dismissProgressDialog()
                try {
                    if (response.body()!!.status == 1) {
                        showToast(activity, response.body()!!.msg)
                        SaveData(activity!!, API_TOKEN, response.body()!!.data!!.apiToken)
                        SaveData(activity!!, USER_DATA, response.body()!!.data!!.client)
                        SaveData(activity!!, PASSWORD, password)
                        startActivity(Intent(activity, HomeActivity::class.java))
                    } else {
                        showToast(activity, response.body()!!.msg)
                        Log.i(TAG, response.body()!!.msg.toString())
                    }
                } catch (e: Exception) {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_edit_profile_tv_birth_date -> showCalender(activity, getString(R.string.birth_date), binding.fragmentEditProfileTvBirthDate, dateModel!!)
            R.id.fragment_edit_profile_tv_donation_date -> showCalender(activity, getString(R.string.chose_donation_date), binding.fragmentEditProfileTvDonationDate, dateModel!!)
            R.id.fragment_edit_profile_btn_edit -> editProfile()
        }
    }

    companion object {
        private const val TAG = "EditProfileFragment"
    }
}