package com.example.bloodbank.data.model.donations

import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.data.model.login.Client
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DonationData {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("client_id")
    @Expose
    var clientId: String? = null

    @SerializedName("patient_name")
    @Expose
    var patientName: String? = null

    @SerializedName("patient_age")
    @Expose
    var patientAge: String? = null

    @SerializedName("blood_type_id")
    @Expose
    var bloodTypeId: String? = null

    @SerializedName("bags_num")
    @Expose
    var bagsNum: String? = null

    @SerializedName("hospital_name")
    @Expose
    var hospitalName: String? = null

    @SerializedName("hospital_address")
    @Expose
    var hospitalAddress: String? = null

    @SerializedName("city_id")
    @Expose
    var cityId: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("notes")
    @Expose
    var notes: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @SerializedName("city")
    @Expose
    var city: GeneralData? = null

    @SerializedName("client")
    @Expose
    var client: Client? = null

    @SerializedName("blood_type")
    @Expose
    var bloodType: GeneralData? = null

}