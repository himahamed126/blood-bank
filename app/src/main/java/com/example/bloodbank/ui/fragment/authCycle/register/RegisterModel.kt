package com.example.bloodbank.ui.fragment.authCycle.register

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.login.Login
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterModel : RegisterContract.Model {

    override fun registerUser(
            onFinishedListener: RegisterContract.Model.OnFinishedListener,
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, activity: Activity) {

        client().signup(
                name, email, birth_date, city_id,
                phone, donation_last_date, password,
                password_confirmation, blood_type_id
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Login> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(it: Login) {
                        onFinishedListener.onFinished(it, password, activity)
                    }

                    override fun onError(e: Throwable) {
                        onFinishedListener.onFailure(e.toString())
                    }
                })

    }
}