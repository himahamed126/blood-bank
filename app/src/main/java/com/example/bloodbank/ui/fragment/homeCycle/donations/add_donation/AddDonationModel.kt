package com.example.bloodbank.ui.fragment.homeCycle.donations.add_donation

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.extensions.addEnqueue

class AddDonationModel : AddDonationContract.Model {

    override fun addDonationRequest(
            onFinishedListener: AddDonationContract.Model.OnFinishedListener,
            apiToken: String, name: String, age: String, bloodTypeId: Int,
            bagsNum: String, hospitalName: String, hospitalAddress: String,
            cityId: Int, phone: String, notes: String, latitude: String,
            longitude: String, activity: Activity
    ) {
        client().addDonationRequset(
                apiToken, name, age, bloodTypeId, bagsNum, hospitalName,
                hospitalAddress, cityId, phone, notes, latitude, longitude
        ).addEnqueue(
                {
                    onFinishedListener.onFinished(
                            it.body()!!, latitude, longitude, activity
                    )
                },
                {
                    onFinishedListener.onFailure(it)
                }
        )
    }
}