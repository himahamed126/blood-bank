package com.example.bloodbank.ui.fragment.authCycle.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.databinding.FragmentLoginBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.fragment.authCycle.register.RegisterFragment
import com.example.bloodbank.ui.fragment.authCycle.restPassword.RestPasswordFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.IS_REMEMBER

class LoginFragment : BaseFragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener, LoginContract.View {

    lateinit var binding: FragmentLoginBinding
    var isRemember: Boolean = false

    var loginPresenter: LoginPresenter? = null
    private var loadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_login, container)

        loginPresenter = LoginPresenter(this)

        if (SharedPreferencesManger.LoadBoolean(requireActivity(), IS_REMEMBER)) {
            requireActivity().startActivity(Intent(activity, HomeActivity::class.java))
        }
        initUi()
        return binding.root
    }

    private fun initUi() {
        loadingDialog = LoadingDialog(activity)
        binding.fragmentLoginBtnLogin.setOnClickListener(this)
        binding.fragmentLoginTvForgetPassword.setOnClickListener(this)
        binding.fragmentLoginTvRegister.setOnClickListener(this)
        binding.fragmentLoginCbRemember.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        isRemember = isChecked
    }

    override fun showProgress() {
        loadingDialog!!.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog!!.dismissDialog()
    }

    override fun onResponseFailure(throwable: Throwable) {
        requireActivity().createToast(throwable.toString())
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_login_btn_login -> {
                loginPresenter!!.requestLogin(
                        binding.fragmentLoginEdPhone.text.toString(),
                        binding.fragmentLoginEdPassword.text.toString(),
                        this.requireActivity(), isRemember)
            }
            R.id.fragment_login_tv_forget_password -> activity.replaceFragment(R.id.activity_auth_fl_content, RestPasswordFragment(), "")
            R.id.fragment_login_tv_register -> activity.replaceFragment(R.id.activity_auth_fl_content, RegisterFragment(), "")
        }
    }

    override fun onBack() {
        requireActivity().finish()
    }
}
