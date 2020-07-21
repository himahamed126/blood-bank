package com.example.bloodbank.ui.fragment.homeCycle.notifications

import com.example.bloodbank.data.model.notifications.NotificationData

interface NotificationContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(notificationList: MutableList<NotificationData>)
            fun onFailure(message: String)
        }

        fun getNotification(onFinishedListener: OnFinishedListener, apiToken: String, pageNo: Int)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(notificationList: MutableList<NotificationData>)
        fun onResponseFailure(message: String)
    }

    interface Presenter {
        fun onDestroy()
        fun requestDataFromServer(apiToken: String)
        fun getMoreData(apiToken: String, pageNo: Int)
    }
}