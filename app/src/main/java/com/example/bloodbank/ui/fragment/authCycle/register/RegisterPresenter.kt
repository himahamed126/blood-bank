package com.example.bloodbank.ui.fragment.authCycle.register

import android.app.Activity
import android.content.Intent
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.utils.PASSWORD
import com.example.bloodbank.utils.USER_DATA

class RegisterPresenter(var registerView: RegisterContract.View?) :
        RegisterContract.Presenter, RegisterContract.Model.OnFinishedListener {
    var registerModel: RegisterModel = RegisterModel()

    override fun onDestroy() {
        registerView = null
    }

    override fun requestRegister(
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, activity: Activity
    ) {
        if (registerView != null) {
            registerView!!.showProgress()
        }
        registerModel.registerUser(
                this, name, email,
                birth_date, phone, donation_last_date,
                password, password_confirmation,
                city_id, blood_type_id, activity
        )
    }

    override fun onFinished(body: Login, password: String, activity: Activity) {
        if (registerView != null) {
            registerView!!.hideProgress()
        }
        if (body.status == 1) {
            SharedPreferencesManger(activity).setPreference("API_TOKEN", body.data!!.apiToken!!)
            SharedPreferencesManger(activity).setPreference(USER_DATA, body.data!!)
            SharedPreferencesManger(activity).setPreference(PASSWORD, password)
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
        activity.createToast(body.msg!!)
    }

    override fun onFailure(string: String) {
        if (registerView != null) {
            registerView!!.hideProgress()
        }
        registerView!!.onResponseFailure(string)
    }
}