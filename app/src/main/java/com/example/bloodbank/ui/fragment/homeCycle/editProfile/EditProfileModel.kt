package com.example.bloodbank.ui.fragment.homeCycle.editProfile

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.extensions.addEnqueue

class EditProfileModel : EditProfileContract.Model {

    override fun editData(
            onFinishedListener: EditProfileContract.Model.OnFinishedListener,
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, apiToken: String, activity: Activity) {
        client().editProfileData(
                name, email, birth_date, city_id,
                phone, donation_last_date, password,
                password_confirmation, blood_type_id, apiToken
        ).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        onFinishedListener.onFinished(it.body()!!, password, activity)
                    } else {
                        onFinishedListener.onFailure(it.body()!!.msg.toString())
                    }
                },
                {
                    onFinishedListener.onFailure(it.toString())
                }
        )
    }
}