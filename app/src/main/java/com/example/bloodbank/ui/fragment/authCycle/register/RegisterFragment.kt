package com.example.bloodbank.ui.fragment.authCycle.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.databinding.FragmentRegisterBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.fragment.authCycle.login.LoginFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.DateModel
import com.example.bloodbank.utils.HelperMethods

class RegisterFragment : BaseFragment(), RegisterContract.View, View.OnClickListener {

    private var governorateAdapter: GeneralResponseAdapter? = null
    private var cityAdapter: GeneralResponseAdapter? = null
    private var bloodTypeAdapter: GeneralResponseAdapter? = null
    private var dateModel: DateModel? = null
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_register, container)
        registerPresenter = RegisterPresenter(this)
        loadingDialog = LoadingDialog(activity)

        dateModel = DateModel("01", "01", "1950", "01-01-1950")
        bloodTypeAdapter = GeneralResponseAdapter(activity)
        HelperMethods.getSpinnerCityData2(client().getbloodTypes(), bloodTypeAdapter!!, binding.fragmentRegisterSpBloodType, getString(R.string.blood_type), activity)
        governorateAdapter = GeneralResponseAdapter(activity)
        HelperMethods.setSpinnerWithListener(client().governorates, governorateAdapter!!, binding.fragmentRegisterSpGovernorate,
                getString(R.string.governorate), 0, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                cityAdapter = GeneralResponseAdapter(activity)
                HelperMethods.getSpinnerCityData2(client().getCities(position), cityAdapter!!, binding.fragmentRegisterSpCity, getString(R.string.city), activity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.fragmentRegisterBtnRegister.setOnClickListener(this)
        binding.fragmentRegisterTvBirthDate.setOnClickListener(this)
        binding.fragmentRegisterTvDonationDate.setOnClickListener(this)

        return binding.root
    }


    override fun showProgress() {
//        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
//        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(string: String) {
        requireActivity().createToast(string)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_register_tv_birth_date -> HelperMethods.showCalender(activity, getString(R.string.birth_date), binding.fragmentRegisterTvBirthDate, this.dateModel!!)

            R.id.fragment_register_tv_donation_date -> HelperMethods.showCalender(activity, getString(R.string.chose_donation_date), binding.fragmentRegisterTvDonationDate, this.dateModel!!)

            R.id.fragment_register_btn_register -> registerPresenter.requestRegister(
                    binding.fragmentRegisterEdName.text.toString(),
                    binding.fragmentRegisterEdEmail.text.toString(),
                    binding.fragmentRegisterTvBirthDate.text.toString(),
                    binding.fragmentRegisterEdPhone.text.toString(),
                    binding.fragmentRegisterTvDonationDate.text.toString(),
                    binding.fragmentRegisterEdPassword.text.toString(),
                    binding.fragmentRegisterEdConfirmPassword.text.toString(),
                    binding.fragmentRegisterSpCity.selectedItemPosition,
                    binding.fragmentRegisterSpBloodType.selectedItemPosition,
                    this.requireActivity())
        }
    }

    override fun onBack() {
        activity.replaceFragment(R.id.activity_auth_fl_content, LoginFragment())
    }
}