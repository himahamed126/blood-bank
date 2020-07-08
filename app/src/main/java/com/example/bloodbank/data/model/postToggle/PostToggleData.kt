package com.example.bloodbank.data.model.postToggle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostToggleData {
    @SerializedName("attached")
    @Expose
    var attached: List<Int>? = null

    @SerializedName("detached")
    @Expose
    var detached: List<Any>? = null

}