package com.example.bloodbank.data.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Notification {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("data")
    @Expose
    var data: NotificationPagination? = null

}