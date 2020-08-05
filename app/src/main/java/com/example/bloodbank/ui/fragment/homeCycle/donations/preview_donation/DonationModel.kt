package com.example.bloodbank.ui.fragment.homeCycle.donations.preview_donation

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.donations.DonationData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DonationModel : DonationContract.Model {

    private var donationsList: MutableList<DonationData> = mutableListOf()
    private var donationsListWithFilter: MutableList<DonationData> = mutableListOf()

    private lateinit var compositeDisposable: CompositeDisposable

    override fun getDonations(onFinishedListener: DonationContract.Model.OnFinishedListener,
                              apiToken: String, pageNo: Int) {

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(client().getAllDonation(apiToken, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    donationsListWithFilter.clear()
                    donationsList.addAll(it.data!!.data!!)
                    onFinishedListener.onFinished(donationsList)
                })
    }

    override fun getDonationsWithFilter(
            onFinishedListener: DonationContract.Model.OnFinishedListener,
            apiToken: String, bloodTypeId: Int, cityId: Int, pageNo: Int, activity: Activity
    ) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(client().getAllDonationWithFilter(
                apiToken, bloodTypeId, cityId, pageNo
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            donationsListWithFilter.clear()
            donationsListWithFilter.addAll(it.data!!.data!!)
            onFinishedListener.onFinished(donationsListWithFilter)
        })
    }
}

