package com.example.bloodbank.ui.fragment.homeCycle.editProfile

import android.app.Activity
import com.example.bloodbank.data.model.login.Login

interface EditProfileContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: Login, password: String, activity: Activity)
            fun onFailure(string: String)
        }

        fun editData(onFinishedListener: OnFinishedListener, name: String,
                     email: String, birth_date: String, phone: String,
                     donation_last_date: String, password: String,
                     password_confirmation: String, city_id: Int,
                     blood_type_id: Int, apiToken: String, activity: Activity)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(string: String)
    }

    interface Presenter {
        fun onDestroy()
        fun requestEditData(
                name: String, email: String, birth_date: String, phone: String,
                donation_last_date: String, password: String, password_confirmation: String,
                city_id: Int, blood_type_id: Int, apiToken: String, activity: Activity)
    }
}