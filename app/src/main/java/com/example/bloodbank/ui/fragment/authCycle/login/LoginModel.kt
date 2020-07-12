package com.example.bloodbank.ui.fragment.authCycle.login

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.extensions.addEnqueue

class LoginModel : LoginContract.Model {

    val tag = "Login"

    override fun loginUser(onFinishedListener: LoginContract.Model.OnFinishedListener,
                           phone: String, password: String, activity: Activity, isRemember: Boolean) {
        ApiClient.client().login(phone, password).addEnqueue(
                {
                    onFinishedListener.onFinished(it.body()!!, activity, password, isRemember)
                },
                {
                    onFinishedListener.onFailure(it)
                }
        )
    }
}