package com.example.bloodbank.ui.fragment.authCycle.newPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentNewPasswordBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog

class NewPasswordFragment : BaseFragment(), NewPasswordContract.View, View.OnClickListener {

    private lateinit var binding: FragmentNewPasswordBinding

    lateinit var newPasswordPresenter: NewPasswordPresenter
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_new_password, container)
        newPasswordPresenter = NewPasswordPresenter(this)
        loadingDialog = LoadingDialog(activity)
        binding.fragmentNewPasswordBtnChange.setOnClickListener(this)
        return binding.root
    }

    override fun showProgress() {
//        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
//        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(throwable: Throwable) {
        requireActivity().createToast(throwable.toString())
    }

    override fun onClick(v: View?) {
        newPasswordPresenter.requestNewPassword(
                binding.fragmentNewPasswordEdNewPassword.text.toString(),
                binding.fragmentNewPasswordEdConfirmNewPassword.text.toString(),
                binding.fragmentNewPasswordEdCode.text.toString(), this.requireActivity())
    }
}
