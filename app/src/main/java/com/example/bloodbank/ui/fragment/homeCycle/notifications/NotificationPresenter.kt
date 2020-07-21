package com.example.bloodbank.ui.fragment.homeCycle.notifications

import com.example.bloodbank.data.model.notifications.NotificationData

class NotificationPresenter(private var notificationView: NotificationContract.View?)
    : NotificationContract.Model.OnFinishedListener, NotificationContract.Presenter {

    private var notificationModel: NotificationContract.Model = NotificationModel()

    override fun onFinished(notificationList: MutableList<NotificationData>) {
        if (notificationView != null) {
            notificationView!!.hideProgress()
        }
        notificationView!!.setDataToRecyclerView(notificationList)
    }

    override fun onFailure(message: String) {
        if (notificationView != null) {
            notificationView!!.hideProgress()
        }
        notificationView!!.onResponseFailure(message)
    }

    override fun onDestroy() {
        notificationView = null
    }

    override fun requestDataFromServer(apiToken: String) {
        if (notificationView != null) {
            notificationView!!.showProgress()
        }
        notificationModel.getNotification(this, apiToken, 1)
    }

    override fun getMoreData(apiToken: String, pageNo: Int) {
        if (notificationView != null) {
            notificationView!!.showProgress()
        }
        notificationModel.getNotification(this, apiToken, pageNo)
    }
}