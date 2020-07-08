package com.example.bloodbank.ui.fragment.authCycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentNewPasswordBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.viewModels.AuthViewModel
import org.koin.android.ext.android.inject

class NewPasswordFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentNewPasswordBinding
    private val viewModel by inject<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_new_password, container)
        binding.fragmentNewPasswordBtnChange.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        viewModel.newPassword(binding.fragmentNewPasswordEdNewPassword.text.toString(),
                binding.fragmentNewPasswordEdConfirmNewPassword.text.toString(),
                binding.fragmentNewPasswordEdCode.text.toString(), this.activity!!)
    }
}
