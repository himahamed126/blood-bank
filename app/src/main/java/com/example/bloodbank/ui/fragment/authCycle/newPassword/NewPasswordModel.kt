package com.example.bloodbank.ui.fragment.authCycle.newPassword

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.extensions.addEnqueue
import com.example.bloodbank.utils.PHONE

class NewPasswordModel : NewPasswordContract.Model {

    var tag = "NewPassword"

    override fun newPassword(
            onFinishedListener: NewPasswordContract.Model.OnFinishedListener,
            newPassword: String, confirmNewPassword: String,
            code: String, activity: Activity
    ) {
        ApiClient.client().newPassword(
                newPassword, confirmNewPassword, code,
                SharedPreferencesManger.LoadData(activity, PHONE)!!
        ).addEnqueue(
                {
                    onFinishedListener.onFinished(it.body()!!, activity)
                },
                {
                    onFinishedListener.onFailure(it)
                }
        )
    }
}