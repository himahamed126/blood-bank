package com.example.bloodbank.data.model.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeneralData(@field:Expose @field:SerializedName("id") var id: Int,
                  @field:Expose @field:SerializedName("name") var name: String) {

    @SerializedName("created_at")
    @Expose
    var createdAt: Any? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: Any? = null

    @SerializedName("governorate_id")
    @Expose
    var governorateId: String? = null

    @SerializedName("governorate")
    @Expose
    var governorate: GeneralResponse? = null

}