package com.example.bloodbank.data.model.restPassword

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("pin_code_for_test")
    @Expose
    var pinCodeForTest: Int? = null

    @SerializedName("mail_fails")
    @Expose
    var mailFails: List<Any>? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

}