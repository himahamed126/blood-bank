package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.extensions.addEnqueue

class RestPasswordModel : RestPasswordContract.Model {

    override fun restNewPassword(onFinishedListener: RestPasswordContract.Model.OnFinishedListener,
                                 phone: String, activity: Activity) {
        ApiClient.client().resetPassword(phone).addEnqueue(
                {
                    onFinishedListener.onFinish(it.body()!!, phone, activity)
                },
                {
                    onFinishedListener.onFailure(it)
                }
        )
    }
}