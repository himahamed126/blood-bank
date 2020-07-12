package com.example.bloodbank.ui.fragment.authCycle.newPassword

import android.app.Activity
import com.example.bloodbank.data.model.restPassword.RestPassword

interface NewPasswordContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: RestPassword, activity: Activity)
            fun onFailure(throwable: Throwable)
        }

        fun newPassword(onFinishedListener: OnFinishedListener, newPassword: String, confirmNewPassword: String, code: String, activity: Activity) {}
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun requestNewPassword(newPassword: String, confirmNewPassword: String, code: String, activity: Activity)
    }
}