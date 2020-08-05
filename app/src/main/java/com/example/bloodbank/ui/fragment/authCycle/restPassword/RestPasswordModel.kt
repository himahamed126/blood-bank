package com.example.bloodbank.ui.fragment.authCycle.restPassword

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.model.restPassword.RestPassword
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RestPasswordModel : RestPasswordContract.Model {

    override fun restNewPassword(onFinishedListener: RestPasswordContract.Model.OnFinishedListener,
                                 phone: String, activity: Activity) {
        ApiClient.client().resetPassword(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<RestPassword> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it)
                    }

                    override fun onNext(it: RestPassword) {
                        onFinishedListener.onFinish(it, phone, activity)
                    }
                })
    }
}