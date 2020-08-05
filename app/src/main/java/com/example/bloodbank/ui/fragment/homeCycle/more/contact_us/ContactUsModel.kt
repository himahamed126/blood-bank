package com.example.bloodbank.ui.fragment.homeCycle.more.contact_us

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.contactUs.ContactUs
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ContactUsModel : ContactUsContract.Model {

    override fun contactUs(onFinishedListener: ContactUsContract.Model.OnFinishedListener,
                           apiToken: String, title: String, content: String, activity: Activity) {

        client().contactUs(apiToken, title, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ContactUs> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onFinishedListener.onFailure(e.toString())
                    }

                    override fun onNext(it: ContactUs) {
                        onFinishedListener.onFinished(it, activity)
                    }
                })
    }
}