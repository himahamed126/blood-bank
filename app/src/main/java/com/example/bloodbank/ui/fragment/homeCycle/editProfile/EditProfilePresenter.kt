package com.example.bloodbank.ui.fragment.homeCycle.editProfile

import android.app.Activity
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.utils.PASSWORD
import com.example.bloodbank.utils.USER_DATA

class EditProfilePresenter(private var editProfileView: EditProfileContract.View?) :
        EditProfileContract.Presenter, EditProfileContract.Model.OnFinishedListener {
    var editProfileModel: EditProfileModel = EditProfileModel()

    override fun onDestroy() {
        editProfileView = null
    }

    override fun requestEditData(
            name: String, email: String, birth_date: String,
            phone: String, donation_last_date: String,
            password: String, password_confirmation: String,
            city_id: Int, blood_type_id: Int, apiToken: String, activity: Activity
    ) {
        if (editProfileView != null) {
            editProfileView!!.showProgress()
        }
        editProfileModel.editData(
                this, name, email,
                birth_date, phone, donation_last_date,
                password, password_confirmation,
                city_id, blood_type_id, apiToken, activity
        )
    }

    override fun onFinished(body: Login, password: String, activity: Activity) {
        if (editProfileView != null) {
            editProfileView!!.hideProgress()
        }
        SharedPreferencesManger.getINSTANCE(activity)!!.setPreference(USER_DATA, body.data!!)
        SharedPreferencesManger.getINSTANCE(activity)!!.setPreference(PASSWORD, password)
        activity.createToast(body.msg!!)
    }

    override fun onFailure(string: String) {
        if (editProfileView != null) {
            editProfileView!!.hideProgress()
        }
        editProfileView!!.onResponseFailure(string)
    }
}