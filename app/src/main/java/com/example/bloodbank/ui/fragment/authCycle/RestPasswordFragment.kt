package com.example.bloodbank.ui.fragment.authCycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger.setSharedPreferences
import com.example.bloodbank.databinding.FragmentRestPasswordBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.viewModels.AuthViewModel
import org.koin.android.ext.android.inject

class RestPasswordFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentRestPasswordBinding
    private val viewModel by inject<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_rest_password, container)

        setSharedPreferences(activity!!)
        binding.fragmentRestPasswordBtnSend.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        viewModel.restPassword(binding.fragmentRestPasswordEdPhone.text.toString().trim(' '), this.activity!!)
    }

    override fun onBack() {
        activity.replaceFragment(R.id.activity_auth_fl_content, LoginFragment())
    }
}