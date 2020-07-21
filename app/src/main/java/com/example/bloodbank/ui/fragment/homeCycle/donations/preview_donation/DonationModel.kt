package com.example.bloodbank.ui.fragment.homeCycle.donations.preview_donation

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.donations.DonationData
import com.example.bloodbank.extensions.addEnqueue

class DonationModel : DonationContract.Model {

    private var donationsList: MutableList<DonationData> = mutableListOf()

    override fun getDonations(onFinishedListener: DonationContract.Model.OnFinishedListener,
                              apiToken: String, pageNo: Int) {
        client().getAllDonation(apiToken, pageNo).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        donationsList.addAll(it.body()!!.data!!.data!!)
                        onFinishedListener.onFinished(donationsList)
                    } else {
                        onFinishedListener.onFailure(it.message())
                    }
                },
                {
                    onFinishedListener.onFailure(it.message.toString())
                }
        )
    }

    override fun getDonationsWithFilter(
            onFinishedListener: DonationContract.Model.OnFinishedListener,
            apiToken: String, bloodTypeId: Int, cityId: Int, pageNo: Int, activity: Activity
    ) {
        client().getAllDonationWithFilter(
                apiToken, bloodTypeId, cityId, pageNo
        ).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        donationsList.clear()
                        donationsList.addAll(it.body()!!.data!!.data!!)
                        onFinishedListener.onFinished(donationsList)
                    } else {
                        onFinishedListener.onFailure(it.message().toString())
                    }
                },
                {
                    onFinishedListener.onFailure(it.toString())
                }
        )
    }
}