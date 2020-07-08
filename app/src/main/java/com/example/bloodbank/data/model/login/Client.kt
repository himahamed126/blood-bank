package com.example.bloodbank.data.model.login

import com.example.bloodbank.data.model.general.GeneralData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Client {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("birth_date")
    @Expose
    var birthDate: String? = null

    @SerializedName("city_id")
    @Expose
    var cityId: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("donation_last_date")
    @Expose
    var donationLastDate: String? = null

    @SerializedName("blood_type_id")
    @Expose
    var bloodTypeId: String? = null

    @SerializedName("is_active")
    @Expose
    var isActive: String? = null

    @SerializedName("pin_code")
    @Expose
    var pinCode: String? = null

    @SerializedName("remember_token")
    @Expose
    var rememberToken: Any? = null

    @SerializedName("can_donate")
    @Expose
    var canDonate: Boolean? = null

    @SerializedName("city")
    @Expose
    var city: GeneralData? = null

    @SerializedName("blood_type")
    @Expose
    var bloodType: GeneralData? = null

}