package com.example.bloodbank.ui.fragment.homeCycle.editProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.login.ClientData
import com.example.bloodbank.databinding.FragmentEditProfileBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.DateModel
import com.example.bloodbank.utils.HelperMethods.getSpinnerWithSelection
import com.example.bloodbank.utils.HelperMethods.setSpinnerWithListener
import com.example.bloodbank.utils.HelperMethods.showCalender
import com.example.bloodbank.utils.PASSWORD

class EditProfileFragment : BaseFragment(), EditProfileContract.View, View.OnClickListener {

    private var governorateAdapter: GeneralResponseAdapter? = null
    private var cityAdapter: GeneralResponseAdapter? = null
    private var bloodTypeAdapter: GeneralResponseAdapter? = null
    private var dateModel: DateModel? = null
    private var clientData: ClientData? = null
    private var loadPassword: String? = null

    lateinit var binding: FragmentEditProfileBinding
    lateinit var editProfilePresenter: EditProfilePresenter
    lateinit var loadingDialog: LoadingDialog
    lateinit var apiToken: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_edit_profile, container)
        editProfilePresenter = EditProfilePresenter(this)

        init()
        return binding.root
    }

    private fun init() {
        apiToken = SharedPreferencesManger(this.requireActivity()).getApiToken()
        dateModel = DateModel("01", "01", "1970", "01-01-1970")
        clientData = SharedPreferencesManger(this.requireActivity()).loadUserData()
        loadPassword = SharedPreferencesManger(this.requireActivity()).restoreStringValue(PASSWORD).toString()
        binding.clientData = clientData
        binding.password = loadPassword

        executeSpinner()
        loadingDialog = LoadingDialog(activity)

        binding.fragmentEditProfileBtnEdit.setOnClickListener(this)
        binding.fragmentEditProfileTvBirthDate.setOnClickListener(this)
        binding.fragmentEditProfileTvDonationDate.setOnClickListener(this)
    }

    private fun executeSpinner() {
        bloodTypeAdapter = GeneralResponseAdapter(activity)
        governorateAdapter = GeneralResponseAdapter(activity)
        cityAdapter = GeneralResponseAdapter(activity)
        getSpinnerWithSelection(client().getbloodTypes(), bloodTypeAdapter!!, binding.fragmentEditProfileSpBloodType, getString(R.string.blood_type),
                clientData!!.client!!.bloodType!!.id)
        setSpinnerWithListener(client().governorates, governorateAdapter!!, binding.fragmentEditProfileSpGovernorate, getString(R.string.governorate),
                clientData!!.client!!.city!!.id, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
//                fragmentEditProfileSpCity.setSelection(clientData.getClient().getCity().getId());
                getSpinnerWithSelection(client().getCities(position), cityAdapter!!, binding.fragmentEditProfileSpCity, getString(R.string.city), clientData!!.client!!.city!!.governorateId!!.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        binding.fragmentEditProfileSpGovernorate.setSelection(clientData!!.client!!.city!!.governorateId!!.toInt())
        binding.fragmentEditProfileSpCity.setSelection(clientData!!.client!!.city!!.id)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_edit_profile_tv_birth_date -> showCalender(activity, getString(R.string.birth_date), binding.fragmentEditProfileTvBirthDate, dateModel!!)
            R.id.fragment_edit_profile_tv_donation_date -> showCalender(activity, getString(R.string.chose_donation_date), binding.fragmentEditProfileTvDonationDate, dateModel!!)
            R.id.fragment_edit_profile_btn_edit -> {
                editProfilePresenter.requestEditData(
                        binding.fragmentEditProfileEdName.text.toString(),
                        binding.fragmentEditProfileEdEmail.text.toString(),
                        binding.fragmentEditProfileTvBirthDate.text.toString(),
                        binding.fragmentEditProfileEdPhone.text.toString(),
                        binding.fragmentEditProfileTvDonationDate.text.toString(),
                        binding.fragmentEditProfileEdPassword.text.toString(),
                        binding.fragmentEditProfileEdConfirmPassword.text.toString(),
                        binding.fragmentEditProfileSpCity.selectedItemPosition,
                        binding.fragmentEditProfileSpBloodType.selectedItemPosition,
                        API_TOKEN!!,
                        this.requireActivity())
            }
        }
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(string: String) {
        requireActivity().createToast(string)
    }
}