package com.example.bloodbank.ui.fragment.homeCycle.donations.add_donation

import android.app.Activity
import com.example.bloodbank.data.model.donations.Donations

interface AddDonationContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(body: Donations, latitude: String, longitude: String, activity: Activity)
            fun onFailure(throwable: Throwable)
        }

        fun addDonationRequest(onFinishedListener: OnFinishedListener, apiToken: String,
                               name: String, age: String, bloodTypeId: Int, bagsNum: String,
                               hospitalName: String, hospitalAddress: String, cityId: Int,
                               phone: String, notes: String, latitude: String, longitude: String,
                               activity: Activity)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun requestAddDonationRequest(apiToken: String, name: String, age: String,
                                      bloodTypeId: Int, bagsNum: String,
                                      hospitalName: String, hospitalAddress: String,
                                      cityId: Int, phone: String, notes: String,
                                      latitude: String, longitude: String, activity: Activity)
    }
}