package com.example.bloodbank.ui.fragment.homeCycle.donations.preview_donation

import android.app.Activity
import com.example.bloodbank.data.model.donations.DonationData

interface DonationContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(donationList: MutableList<DonationData>)
            fun onFailure(message: String)
        }

        fun getDonations(
                onFinishedListener: OnFinishedListener, apiToken: String, pageNo: Int
        )

        fun getDonationsWithFilter(
                onFinishedListener: OnFinishedListener, apiToken: String,
                bloodTypeId: Int, cityId: Int, pageNo: Int, activity: Activity
        )
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(donationList: MutableList<DonationData>)
        fun onResponseFailure(message: String)
    }

    interface Presenter {
        fun onDestroy()
        fun requestDataFromServer(apiToken: String)
        fun getMoreData(apiToken: String, pageNo: Int)
        fun requestDonationsWithFilter(
                apiToken: String, bloodTypeId: Int, cityId: Int, pageNo: Int, activity: Activity
        )
    }
}