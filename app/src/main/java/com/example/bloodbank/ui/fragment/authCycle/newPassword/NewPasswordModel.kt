package com.example.bloodbank.ui.fragment.authCycle.newPassword

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.restPassword.RestPassword
import com.example.bloodbank.utils.PHONE
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewPasswordModel : NewPasswordContract.Model {

    var tag = "NewPassword"

    override fun newPassword(
            onFinishedListener: NewPasswordContract.Model.OnFinishedListener,
            newPassword: String, confirmNewPassword: String,
            code: String, activity: Activity
    ) {
        ApiClient.client().newPassword(
                newPassword, confirmNewPassword, code,
                SharedPreferencesManger(activity).restoreStringValue(PHONE)!!
        ).subscribeOn(Schedulers.io())
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
                        onFinishedListener.onFinished(it, activity)
                    }
                })
    }
}