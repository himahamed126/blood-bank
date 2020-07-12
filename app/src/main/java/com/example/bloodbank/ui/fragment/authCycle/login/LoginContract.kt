package com.example.bloodbank.ui.fragment.authCycle.login

import android.app.Activity
import com.example.bloodbank.data.model.login.Login

interface LoginContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: Login, activity: Activity, password: String, remember: Boolean)
            fun onFailure(throwable: Throwable)
        }

        fun loginUser(onFinishedListener: OnFinishedListener, phone: String, password: String, activity: Activity, isRemember: Boolean)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun requestLogin(phone: String, password: String, activity: Activity, isRemember: Boolean)
    }
}