package com.example.bloodbank.ui.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.local.SharedPreferencesManger.SaveData
import com.example.bloodbank.extensions.addEnqueue
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.ui.fragment.authCycle.newPassword.NewPasswordFragment
import com.example.bloodbank.utils.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.Dispatchers.Main


class AuthViewModel(var context: Context)
    : CoroutineViewModel(Main) {

    private val tag: String = "AUTH_VIEW_MODEL"

    fun registerUser(
            name: String,
            email: String,
            birth_date: String,
            phone: String,
            donation_last_date: String,
            password: String,
            password_confirmation: String,
            city_id: Int,
            blood_type_id: Int,
            activity: Activity
    ) {

        HelperMethods.showProgressDialog(activity, context.getString(R.string.please_wait))
        client().signup(name, email, birth_date, city_id, phone, donation_last_date, password, password_confirmation, blood_type_id).addEnqueue(
                {
                    activity.createToast(it.body()!!.msg!!)
                    SaveData(activity, API_TOKEN, it.body()!!.data!!.apiToken)
                    SaveData(activity, USER_DATA, it.body()!!.data)
                    SaveData(activity, PASSWORD, password)
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                },
                {
                    Log.i(tag, it.message.toString())
                }
        )
    }



    private fun registerNotificationToken(apiToken: String, activity: Activity) {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            val token = task.result!!.token
            if (!task.isSuccessful) {
                Log.w(tag, "onComplete: ", task.exception)
            } else {
                client().registerNotificationToken(token, apiToken, "android").addEnqueue(
                        {
                            Log.i(tag, "onResponse: " + it.body()!!.msg)
                        },
                        {
                            Log.i(tag, it.message.toString())
                        }
                )
                SaveData(activity, FIREBASE_TOKEN, token)
            }
        }
    }

    fun newPassword(newPassword: String, confirmNewPassword: String, code: String, activity: Activity) {
        HelperMethods.showProgressDialog(activity, context.getString(R.string.please_wait))
        client().newPassword(
                newPassword, confirmNewPassword, code,
                LoadData(activity, PHONE)!!).addEnqueue(
                {
                    activity.createToast(it.body()!!.msg!!)
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                },
                {
                    Log.i(tag, it.message.toString())
                }
        )
    }

    fun restPassword(phone: String, activity: Activity) {
        HelperMethods.showProgressDialog(activity, activity.getString(R.string.please_wait))
        client().resetPassword(phone).addEnqueue(
                {
                    activity.createToast(it.body()!!.msg!!)
                    SaveData(activity, PHONE, phone)
                    activity.replaceFragment(R.id.activity_auth_fl_content, NewPasswordFragment(), "")
                },
                {
                    Log.i(tag, it.message.toString())
                }
        )
    }
}