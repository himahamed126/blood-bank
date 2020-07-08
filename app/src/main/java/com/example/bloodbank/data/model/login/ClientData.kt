package com.example.bloodbank.data.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClientData {
    @SerializedName("api_token")
    @Expose
    var apiToken: String? = null

    @SerializedName("client")
    @Expose
    var client: Client? = null

}