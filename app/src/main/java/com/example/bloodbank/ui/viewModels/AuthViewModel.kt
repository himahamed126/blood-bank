package com.example.bloodbank.ui.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.local.SharedPreferencesManger
import com.example.bloodbank.data.model.general.GeneralResponse
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.data.model.restPassword.RestPassword
import com.example.bloodbank.extensions.replaceFragment
import com.example.bloodbank.helper.*
import com.example.bloodbank.ui.activity.HomeActivity
import com.example.bloodbank.ui.fragment.authCycle.NewPasswordFragment
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(var context: Context)
    : CoroutineViewModel(Main) {

    private val tag: String = "AUTH_VIEW_MODEL"

    fun registerUser(name: String, email: String, birth_date: String,
                     phone: String, donation_last_date: String, password: String,
                     password_confirmation: String, city_id: Int, blood_type_id: Int,
                     activity: Activity) {

        HelperMethods.showProgressDialog(activity, context.getString(R.string.please_wait))
        ApiClient.client.signup(name, email, birth_date, city_id, phone, donation_last_date,
                password, password_confirmation, blood_type_id).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                HelperMethods.dismissProgressDialog()
                try {
                    if (response.body()!!.status == 1) {
                        HelperMethods.showToast(activity, response.body()!!.msg)
                        SharedPreferencesManger.SaveData(activity, API_TOKEN, response.body()!!.data!!.apiToken)
                        SharedPreferencesManger.SaveData(activity, USER_DATA, response.body()!!.data)
                        SharedPreferencesManger.SaveData(activity, PASSWORD, password)
                        activity.startActivity(Intent(activity, HomeActivity::class.java))
                    } else {
                        HelperMethods.showToast(activity, response.body()!!.msg)
                        Log.i(tag, response.body()!!.msg.toString())
                    }
                } catch (e: Exception) {
                    Log.i(tag, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {}
        })
    }

    fun loginUser(phone: String, password: String, activity: Activity, isRemember: Boolean) {
        HelperMethods.showProgressDialog(activity, activity.getString(R.string.please_wait))
        ApiClient.client.login(phone, password).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                HelperMethods.dismissProgressDialog()
                try {
                    if (response.body()!!.status == 1) {
                        HelperMethods.showToast(activity, response.body()!!.msg)
//                        registerNotificationToken(response.body().getData().getApiToken());
                        SharedPreferencesManger.SaveData(activity, API_TOKEN, response.body()!!.data!!.apiToken)
                        SharedPreferencesManger.SaveData(activity, USER_DATA, response.body()!!.data)
                        SharedPreferencesManger.SaveData(activity, PASSWORD, password)
                        SharedPreferencesManger.SaveData(activity, IS_REMEMBER, isRemember)
                        activity.startActivity(Intent(activity, HomeActivity::class.java))

                    } else {
                        HelperMethods.showToast(activity, response.body()!!.msg)
                        Log.i(tag, response.body()!!.msg.toString())
                    }
                } catch (e: Exception) {
                    Log.i(tag, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {}
        })
    }

    private fun registerNotificationToken(apiToken: String, activity: Activity) {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            val token = task.result!!.token
            if (!task.isSuccessful) {
                Log.w(tag, "onComplete: ", task.exception)
            } else {
                ApiClient.client.registerNotificationToken(token, apiToken, "android").enqueue(object : Callback<GeneralResponse> {
                    override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                        if (response.body()!!.status == 1) {
                            Log.i(tag, "onResponse: " + response.body()!!.msg)
                        } else {
                            Log.i(tag, "onResponse: " + response.body()!!.msg)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
                })
            }
            SharedPreferencesManger.SaveData(activity, FIREBASE_TOKEN, token)
        }
    }

    fun newPassword(newPassword: String, confirmNewPassword: String, code: String, activity: Activity) {
        HelperMethods.showProgressDialog(activity, context.getString(R.string.please_wait))
        ApiClient.client.newPassword(newPassword, confirmNewPassword, code, SharedPreferencesManger.LoadData(activity, PHONE)!!).enqueue(object : Callback<RestPassword> {
            override fun onResponse(call: Call<RestPassword>, response: Response<RestPassword>) {
                HelperMethods.dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    HelperMethods.showToast(activity, response.body()!!.msg)
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                } else {
                    Log.i(tag, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<RestPassword>, t: Throwable) {}
        })
    }

    fun restPassword(phone: String, activity: Activity) {
        HelperMethods.showProgressDialog(activity, activity.getString(R.string.please_wait))
        ApiClient.client.resetPassword(phone).enqueue(object : Callback<RestPassword> {
            override fun onResponse(call: Call<RestPassword>, response: Response<RestPassword>) {
                HelperMethods.dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    HelperMethods.showToast(activity, response.body()!!.msg)
                    SharedPreferencesManger.SaveData(activity, PHONE, phone)
                    activity.replaceFragment(R.id.activity_auth_fl_content, NewPasswordFragment(), "")
                } else {
                    HelperMethods.showToast(activity, response.body()!!.msg)
                }
            }

            override fun onFailure(call: Call<RestPassword>, t: Throwable) {}
        })
    }
}