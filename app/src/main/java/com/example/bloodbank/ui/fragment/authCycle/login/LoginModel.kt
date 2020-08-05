package com.example.bloodbank.ui.fragment.authCycle.login

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.login.Login
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginModel : LoginContract.Model {

    override fun loginUser(onFinishedListener: LoginContract.Model.OnFinishedListener,
                           phone: String, password: String, activity: Activity, isRemember: Boolean) {

        client().login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Login> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onFinishedListener.onFailure(e)
                    }

                    override fun onNext(it: Login) {
                        onFinishedListener.onFinished(it, activity, password, isRemember)
                    }
                })
    }
}