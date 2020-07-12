package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.app.Activity
import android.util.Log
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.extensions.addEnqueue
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.fragment.authCycle.newPassword.NewPasswordFragment
import com.example.bloodbank.utils.HelperMethods
import com.example.bloodbank.utils.PHONE

class RestPasswordModel : RestPasswordContract.Model {

    var tag = "restPassword"

    override fun restNewPassword(onFinishedListener: RestPasswordContract.Model.OnFinishedListener,
                                 phone: String, activity: Activity) {
        ApiClient.client().resetPassword(phone).addEnqueue(
                {
                    onFinishedListener.onFinish(it.body()!!, phone, activity)
                },
                {
                    onFinishedListener.onFailure(it)
                    Log.i(tag, it.message.toString())
                }
        )
    }

}