package com.example.bloodbank.ui.fragment.homeCycle.notifications

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.notifications.NotificationData
import com.example.bloodbank.extensions.addEnqueue

class NotificationModel : NotificationContract.Model {

    private var notificationList: MutableList<NotificationData> = mutableListOf()

    override fun getNotification(
            onFinishedListener: NotificationContract.Model.OnFinishedListener,
            apiToken: String,
            pageNo: Int) {
        client().getNotifications(apiToken, pageNo).addEnqueue(
                {
                    notificationList.addAll(it.body()!!.data!!.data!!)
                    onFinishedListener.onFinished(notificationList)
                },
                {
                    onFinishedListener.onFailure(it.toString())
                }
        )
    }
}