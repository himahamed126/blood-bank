package com.example.bloodbank.ui.fragment.authCycle.register

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.extensions.addEnqueue

class RegisterModel : RegisterContract.Model {

    var tag = "Register"

    override fun registerUser(
            onFinishedListener: RegisterContract.Model.OnFinishedListener,
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, activity: Activity) {
        ApiClient.client().signup(
                name, email, birth_date, city_id,
                phone, donation_last_date, password,
                password_confirmation, blood_type_id
        ).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        onFinishedListener.onFinished(it.body()!!, password, activity)
                    } else {
                        onFinishedListener.onFailure(it.message())
                    }
                },
                {
                    onFinishedListener.onFailure(it.toString())
                }
        )
    }
}