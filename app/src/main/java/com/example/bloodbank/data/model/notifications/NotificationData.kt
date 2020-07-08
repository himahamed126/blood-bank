package com.example.bloodbank.data.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationData {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("donation_request_id")
    @Expose
    var donationRequestId: String? = null

    @SerializedName("pivot")
    @Expose
    var pivot: Pivot? = null

}