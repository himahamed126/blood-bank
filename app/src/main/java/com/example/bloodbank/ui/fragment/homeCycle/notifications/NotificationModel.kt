package com.example.bloodbank.ui.fragment.homeCycle.notifications

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.notifications.Notification
import com.example.bloodbank.data.model.notifications.NotificationData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NotificationModel : NotificationContract.Model {

    private var notificationList: MutableList<NotificationData> = mutableListOf()

    override fun getNotification(
            onFinishedListener: NotificationContract.Model.OnFinishedListener,
            apiToken: String,
            pageNo: Int) {
        client().getNotifications(apiToken, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Notification> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it.toString())
                    }

                    override fun onNext(it: Notification) {
                        notificationList.addAll(it.data!!.data!!)
                        onFinishedListener.onFinished(notificationList)
                    }
                })
    }
}