package com.example.bloodbank.ui.fragment.homeCycle.donations.preview_donation

import android.app.Activity
import com.example.bloodbank.data.model.donations.DonationData

class DonationPresenter(private var donationView: DonationContract.View?)
    : DonationContract.Presenter, DonationContract.Model.OnFinishedListener {

    private var donationModel: DonationContract.Model = DonationModel()

    override fun onFinished(donationList: MutableList<DonationData>) {
        if (donationView != null) {
            donationView!!.hideProgress()
        }
        donationView!!.setDataToRecyclerView(donationList)
    }

    override fun onFailure(message: String) {
        if (donationView != null) {
            donationView!!.hideProgress()
        }
        donationView!!.onResponseFailure(message)
    }

    override fun onDestroy() {
        donationView = null
    }

    override fun requestDataFromServer(apiToken: String) {
        if (donationView != null) {
            donationView!!.showProgress()
        }
        donationModel.getDonations(this, apiToken, 1)
    }

    override fun requestDonationsWithFilter(
            apiToken: String, bloodTypeId: Int, cityId: Int, pageNo: Int, activity: Activity
    ) {
        if (donationView != null) {
            donationView!!.showProgress()
        }
        donationModel.getDonationsWithFilter(
                this, apiToken, bloodTypeId, cityId, pageNo, activity
        )
    }


    override fun getMoreData(apiToken: String, pageNo: Int) {
        if (donationView != null) {
            donationView!!.showProgress()
        }
        donationModel.getDonations(this, apiToken, pageNo)
    }
}