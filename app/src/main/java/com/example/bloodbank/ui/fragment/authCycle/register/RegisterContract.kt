package com.example.bloodbank.ui.fragment.authCycle.register

import android.app.Activity
import com.example.bloodbank.data.model.login.Login

interface RegisterContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: Login, password: String, activity: Activity)
            fun onFailure(string: String)
        }

        fun registerUser(onFinishedListener: OnFinishedListener, name: String,
                         email: String, birth_date: String, phone: String,
                         donation_last_date: String, password: String,
                         password_confirmation: String, city_id: Int,
                         blood_type_id: Int, activity: Activity)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(string: String)
    }

    interface Presenter {
        fun onDestroy()
        fun requestRegister(name: String, email: String, birth_date: String,
                            phone: String, donation_last_date: String,
                            password: String, password_confirmation: String,
                            city_id: Int, blood_type_id: Int, activity: Activity)
    }
}