package com.example.bloodbank.ui.fragment.homeCycle.donations.add_donation

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.donations.Donations
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Donations> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it)
                    }

                    override fun onNext(it: Donations) {
                        onFinishedListener.onFinished(
                                it, latitude, longitude, activity
                        )
                    }
                })
    }
}