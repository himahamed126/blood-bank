package com.example.bloodbank.ui.fragment.homeCycle.more.contact_us

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.extensions.addEnqueue

class ContactUsModel : ContactUsContract.Model {

    override fun contactUs(onFinishedListener: ContactUsContract.Model.OnFinishedListener,
                           apiToken: String, title: String, content: String, activity: Activity) {

        client().contactUs(apiToken, title, content).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        onFinishedListener.onFinished(it.body()!!, activity)
                    } else {
                        onFinishedListener.onFailure(it.body()!!.msg!!)
                    }
                },
                {
                    onFinishedListener.onFailure(it.toString())
                }
        )
    }
}