package com.example.bloodbank.ui.fragment.homeCycle.editProfile

import android.app.Activity
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.login.Login
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditProfileModel : EditProfileContract.Model {

    override fun editData(
            onFinishedListener: EditProfileContract.Model.OnFinishedListener,
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, apiToken: String, activity: Activity) {

        client().editProfileData(
                name, email, birth_date, city_id, phone, donation_last_date,
                password, password_confirmation, blood_type_id, apiToken
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Login> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it.toString())
                    }

                    override fun onNext(it: Login) {
                        onFinishedListener.onFinished(it, password, activity)
                    }
                })
    }
}