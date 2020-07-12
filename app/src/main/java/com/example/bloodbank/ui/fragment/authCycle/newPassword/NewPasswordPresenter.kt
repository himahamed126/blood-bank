package com.example.bloodbank.ui.fragment.authCycle.newPassword

import android.app.Activity
import android.content.Intent
import com.example.bloodbank.data.model.restPassword.RestPassword
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.ui.activity.HomeActivity

class NewPasswordPresenter(
        var newPasswordView: NewPasswordContract.View?
) : NewPasswordContract.Presenter, NewPasswordContract.Model.OnFinishedListener {

    var newPasswordModel: NewPasswordContract.Model = NewPasswordModel()

    override fun onDestroy() {
        newPasswordView = null
    }

    override fun requestNewPassword(newPassword: String, confirmNewPassword: String, code: String, activity: Activity) {
        if (newPasswordView != null) {
            newPasswordView!!.showProgress()
        }
        newPasswordModel.newPassword(this, newPassword, confirmNewPassword, code, activity)
    }

    override fun onFinished(body: RestPassword, activity: Activity) {
        if (newPasswordView != null) {
            newPasswordView!!.hideProgress()
        }
        if (body.status == 1) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
        activity.createToast(body.msg!!)
    }

    override fun onFailure(throwable: Throwable) {
        if (newPasswordView != null) {
            newPasswordView!!.hideProgress()
        }
        newPasswordView!!.onResponseFailure(throwable)
    }
}