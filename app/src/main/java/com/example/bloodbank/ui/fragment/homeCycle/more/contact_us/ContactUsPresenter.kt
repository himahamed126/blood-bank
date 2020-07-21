package com.example.bloodbank.ui.fragment.homeCycle.more.contact_us

import android.app.Activity
import com.example.bloodbank.data.model.contactUs.ContactUs
import com.example.bloodbank.extensions.createToast

class ContactUsPresenter(var contactUsView: ContactUsContract.View?) : ContactUsContract.Model.OnFinishedListener, ContactUsContract.Presenter {

    var contactUsModel: ContactUsModel = ContactUsModel()

    override fun onFinished(body: ContactUs, activity: Activity) {
        if (contactUsView != null) {
            contactUsView!!.hideProgress()
        }
        activity.createToast(body.msg!!)
    }

    override fun onFailure(string: String) {
        if (contactUsView != null) {
            contactUsView!!.hideProgress()
        }
        contactUsView!!.onResponseFailure(string)
    }

    override fun onDestroy() {
        contactUsView = null
    }

    override fun requestContactUs(apiToken: String, title: String, content: String, activity: Activity) {
        if (contactUsView != null) {
            contactUsView!!.showProgress()
        }
        contactUsModel.contactUs(this, apiToken, title, content, activity)
    }
}