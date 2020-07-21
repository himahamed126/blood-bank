package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentRestPasswordBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.fragment.authCycle.login.LoginFragment
import com.example.bloodbank.ui.views.LoadingDialog

class RestPasswordFragment : BaseFragment(), RestPasswordContract.View, View.OnClickListener {

    private lateinit var binding: FragmentRestPasswordBinding
    private lateinit var restPasswordPresenter: RestPasswordPresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_rest_password, container)
        restPasswordPresenter = RestPasswordPresenter(this)
        loadingDialog = LoadingDialog(requireActivity())

        binding.fragmentRestPasswordBtnSend.setOnClickListener(this)
        return binding.root
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

    override fun onClick(v: View?) {
        restPasswordPresenter.requestNewPassword(
                binding.fragmentRestPasswordEdPhone.text.toString().trim(' '), this.requireActivity())
    }

    override fun onBack() {
        activity.replaceFragment(R.id.activity_auth_fl_content, LoginFragment())
    }
}