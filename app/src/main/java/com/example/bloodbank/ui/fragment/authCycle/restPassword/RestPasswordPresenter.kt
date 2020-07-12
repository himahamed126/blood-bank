package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.app.Activity
import com.example.bloodbank.R
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.restPassword.RestPassword
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.authCycle.newPassword.NewPasswordFragment
import com.example.bloodbank.utils.PHONE

class RestPasswordPresenter(var restPasswordView: RestPasswordContract.View?)
    : RestPasswordContract.Presenter, RestPasswordContract.Model.OnFinishedListener {

    var restPasswordModel: RestPasswordModel = RestPasswordModel()

    override fun onDestroy() {
        restPasswordView = null
    }

    override fun requestNewPassword(phone: String, activity: Activity) {
        if (restPasswordView != null) {
            restPasswordView!!.showProgress()
        }
        restPasswordModel.restNewPassword(this, phone, activity)
    }

    override fun onFinish(body: RestPassword, phone: String, activity: Activity) {
        if (restPasswordView != null) {
            restPasswordView!!.hideProgress()
        }
        if (body.status == 1) {
            SharedPreferencesManger.SaveData(activity, PHONE, phone)
            activity.replaceFragment(R.id.activity_auth_fl_content, NewPasswordFragment(), "")
        }
        activity.createToast(body.msg!!)
    }

    override fun onFailure(throwable: Throwable) {
        if (restPasswordView != null) {
            restPasswordView!!.hideProgress()
        }
        restPasswordView!!.onResponseFailure(throwable)
    }
}