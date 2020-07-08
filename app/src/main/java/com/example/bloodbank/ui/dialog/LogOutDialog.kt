package com.example.bloodbank.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client

import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.local.SharedPreferencesManger.clean
import com.example.bloodbank.data.model.general.GeneralResponse
import com.example.bloodbank.databinding.DialogLogOutBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.FIREBASE_TOKEN
import com.example.bloodbank.ui.activity.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogOutDialog : DialogFragment(), View.OnClickListener {

    lateinit var binding: DialogLogOutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = inflater.inflateWithBinding(R.layout.dialog_log_out, container)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setCanceledOnTouchOutside(false)

        binding.dialogSignUpBtnYes.setOnClickListener(this)
        binding.dialogSignUpBtnNo.setOnClickListener(this)

        return binding.root
    }

    private fun removeFirebaseToken() {
        client.removeNotificationToken(LoadData(activity!!, FIREBASE_TOKEN)!!, LoadData(activity!!, API_TOKEN)!!).enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                if (response.body()!!.status == 1) {
                    Log.i(TAG, "onResponse: " + response.body()!!.msg)
                    clean(activity!!)
                    val intent = Intent(activity, SplashActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.i(TAG, "onResponse: " + response.body()!!.msg)
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.dialog_sign_up_btn_yes -> {
//                removeFirebaseToken();
                clean(activity!!)
                val intent = Intent(activity, SplashActivity::class.java)
                startActivity(intent)
            }
            R.id.dialog_sign_up_btn_no -> dialog!!.cancel()
        }
    }

    companion object {
        private const val TAG = "LogOutDialog"
    }
}