package com.example.bloodbank.ui.fragment.authCycle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.databinding.FragmentLoginBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.helper.IS_REMEMBER
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.viewModels.AuthViewModel
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    lateinit var binding: FragmentLoginBinding
    private val viewModel by inject<AuthViewModel>()
    var isRemember: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_login, container)

        if (SharedPreferencesManger.LoadBoolean(activity!!, IS_REMEMBER)) {
            activity!!.startActivity(Intent(activity, HomeActivity::class.java))
        }
        init()
        return binding.root
    }

    private fun init() {
        binding.fragmentLoginBtnLogin.setOnClickListener(this)
        binding.fragmentLoginTvForgetPassword.setOnClickListener(this)
        binding.fragmentLoginTvRegister.setOnClickListener(this)
        binding.fragmentLoginCbRemember.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        isRemember = isChecked
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_login_btn_login -> {
                viewModel.loginUser(
                        binding.fragmentLoginEdPhone.text.toString(),
                        binding.fragmentLoginEdPassword.text.toString(), this.activity!!, isRemember)
            }
            R.id.fragment_login_tv_forget_password -> activity.replaceFragment(R.id.activity_auth_fl_content, RestPasswordFragment(), "")
            R.id.fragment_login_tv_register -> activity.replaceFragment(R.id.activity_auth_fl_content, RegisterFragment(), "")
        }
    }

    override fun onBack() {
        activity!!.finish()
    }
}