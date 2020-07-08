package com.example.bloodbank.data.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pivot {
    @SerializedName("client_id")
    @Expose
    var clientId: String? = null

    @SerializedName("notification_id")
    @Expose
    var notificationId: String? = null

    @SerializedName("is_read")
    @Expose
    var isRead: String? = null

}