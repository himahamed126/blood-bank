package com.example.bloodbank.ui.fragment.homeCycle.more.contact_us

import android.app.Activity
import com.example.bloodbank.data.model.contactUs.ContactUs

interface ContactUsContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: ContactUs, activity: Activity)
            fun onFailure(string: String)
        }

        fun contactUs(
                onFinishedListener: OnFinishedListener,
                apiToken: String, title: String, content: String, activity: Activity)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(string: String)
    }

    interface Presenter {
        fun onDestroy()
        fun requestContactUs(apiToken: String, title: String, content: String, activity: Activity)
    }
}