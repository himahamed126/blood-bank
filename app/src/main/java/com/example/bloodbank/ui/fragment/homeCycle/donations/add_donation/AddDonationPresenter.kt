package com.example.bloodbank.ui.fragment.homeCycle.donations.add_donation

import android.app.Activity
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.donations.Donations
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.utils.LATITUDE
import com.example.bloodbank.utils.LONGITUDE

class AddDonationPresenter(var addDonationView: AddDonationContract.View?) :
        AddDonationContract.Presenter, AddDonationContract.Model.OnFinishedListener {

    var addDonationModel: AddDonationModel = AddDonationModel()

    override fun onDestroy() {
        addDonationView = null
    }

    override fun requestAddDonationRequest(
            apiToken: String, name: String, age: String, bloodTypeId: Int,
            bagsNum: String, hospitalName: String, hospitalAddress: String,
            cityId: Int, phone: String, notes: String, latitude: String,
            longitude: String, activity: Activity) {

        if (addDonationView != null) {
            addDonationView!!.showProgress()
            addDonationModel.addDonationRequest(
                    this, apiToken, name, age,
                    bloodTypeId, bagsNum, hospitalName, hospitalAddress,
                    cityId, phone, notes, latitude, longitude, activity
            )
        }
    }

    override fun onFinished(
            body: Donations, latitude: String, longitude: String, activity: Activity
    ) {
        if (addDonationView != null) {
            addDonationView!!.hideProgress()

            SharedPreferencesManger(activity).setPreference( LONGITUDE, latitude)
            SharedPreferencesManger(activity).setPreference( LATITUDE, longitude)
            activity.createToast(body.msg!!)
        }
    }

    override fun onFailure(throwable: Throwable) {
        if (addDonationView != null) {
            addDonationView!!.hideProgress()
        }
        addDonationView!!.onResponseFailure(throwable)
    }
}