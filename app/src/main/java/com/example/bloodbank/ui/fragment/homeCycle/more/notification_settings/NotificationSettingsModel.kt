package com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.data.model.general.GeneralResponse
import com.example.bloodbank.data.model.notificationSettings.NotificationSettings
import com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings.NotificationSettingsContract.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NotificationSettingsModel : Model {

    private var bloodList: MutableList<GeneralData> = mutableListOf()
    private var governoatesList: MutableList<GeneralData> = mutableListOf()
    private var oldBloodTypeList: MutableList<String> = mutableListOf()
    private var oldGovernorateList: MutableList<String> = mutableListOf()

    override fun getBloodTypes(onFinishedListenerBlood: Model.OnFinishedListenerBlood) {
        client().getbloodTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GeneralResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListenerBlood.onFailure(it.message.toString())
                    }

                    override fun onNext(it: GeneralResponse) {
                        bloodList.addAll(it.data!!)
                        onFinishedListenerBlood.onFinishedBlood(bloodList)
                    }
                })
    }

    override fun getGovernorate(onFinishedListenerGovernorate: Model.OnFinishedListenerGovernorate) {
        client().governorates
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GeneralResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListenerGovernorate.onFailure(it.message!!)
                    }

                    override fun onNext(it: GeneralResponse) {
                        governoatesList.addAll(it.data!!)
                        onFinishedListenerGovernorate.onFinishedGovernorate(governoatesList)
                    }
                })
    }

    override fun getNotificationSettings(onFinishedListenerGetNotiSett: Model.OnFinishedListenerGetNotiSett,
                                         apiToken: String) {
        client().getNotificationSettings(apiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NotificationSettings> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onFinishedListenerGetNotiSett.onFailure(e.message!!)
                    }

                    override fun onNext(it: NotificationSettings) {
                        oldBloodTypeList.addAll(it.data!!.bloodTypes!!)
                        oldGovernorateList.addAll(it.data!!.governorates!!)

                        onFinishedListenerGetNotiSett.onFinishedGetNotiSett(oldBloodTypeList, oldGovernorateList)
                    }
                })
    }

    override fun changeNotificationSetting(
            onFinishedListenerChangeNotiSett: Model.OnFinishedListenerChangeNotiSett,
            apiToken: String, governorateList: MutableList<Int>, bloodList: MutableList<Int>) {
        client().changeNotificationSettings(apiToken, governorateList, bloodList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NotificationSettings> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onFinishedListenerChangeNotiSett.onFailure(e.message!!)
                    }

                    override fun onNext(it: NotificationSettings) {
                        onFinishedListenerChangeNotiSett.onFinishedChangeNotiSett(it.msg!!)
                    }
                })
    }
}