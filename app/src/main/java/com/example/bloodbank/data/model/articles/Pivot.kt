package com.example.bloodbank.data.model.articles

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pivot {
    @SerializedName("client_id")
    @Expose
    var clientId: String? = null

    @SerializedName("post_id")
    @Expose
    var postId: String? = null

}