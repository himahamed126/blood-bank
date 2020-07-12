package com.example.bloodbank.ui.fragment.authCycle.login

import android.app.Activity
import android.content.Intent
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.IS_REMEMBER
import com.example.bloodbank.utils.PASSWORD
import com.example.bloodbank.utils.USER_DATA

class LoginPresenter(var loginView: LoginContract.View?) : LoginContract.Presenter, LoginContract.Model.OnFinishedListener {

    var loginModel: LoginContract.Model? = null

    init {
        this.loginModel = LoginModel()
    }

    override fun onDestroy() {
        loginView = null
    }

    override fun requestLogin(phone: String, password: String, activity: Activity, isRemember: Boolean) {
        if (loginView != null) {
            loginView!!.showProgress()
        }
        loginModel!!.loginUser(this, phone, password, activity, isRemember)
    }

    override fun onFinished(body: Login, activity: Activity, password: String, remember: Boolean) {
        if (loginView != null) {
            loginView!!.hideProgress()
        }
        if (body.status == 1) {
            SharedPreferencesManger.SaveData(activity, API_TOKEN, body.data!!.apiToken)
            SharedPreferencesManger.SaveData(activity, USER_DATA, body.data)
            SharedPreferencesManger.SaveData(activity, PASSWORD, password)
            SharedPreferencesManger.SaveData(activity, IS_REMEMBER, remember)
            activity.startActivity(Intent(activity, HomeActivity::class.java))
//          registerNotificationToken(it.body().getData().getApiToken());
        }
        activity.createToast(body.msg!!)
    }

    override fun onFailure(throwable: Throwable) {
        if (loginView != null) {
            loginView!!.hideProgress()
        }
        loginView!!.onResponseFailure(throwable)
    }
}