package com.example.bloodbank.data.model.notificationSettings

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("governorates")
    @Expose
    var governorates: List<String>? = null

    @SerializedName("blood_types")
    @Expose
    var bloodTypes: List<String>? = null

}