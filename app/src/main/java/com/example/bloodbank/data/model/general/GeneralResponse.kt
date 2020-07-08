package com.example.bloodbank.data.model.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeneralResponse {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("data")
    @Expose
    var data: List<GeneralData>? = null

}