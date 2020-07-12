package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.app.Activity
import com.example.bloodbank.data.model.restPassword.RestPassword

interface RestPasswordContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinish(body: RestPassword, phone: String, activity: Activity)
            fun onFailure(throwable: Throwable)
        }

        fun restNewPassword(onFinishedListener: OnFinishedListener, phone: String, activity: Activity)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun requestNewPassword(phone: String, activity: Activity)
    }
}