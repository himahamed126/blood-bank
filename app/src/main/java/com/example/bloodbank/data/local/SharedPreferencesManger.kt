package com.example.bloodbank.data.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.bloodbank.data.model.login.ClientData
import com.example.bloodbank.utils.USER_DATA
import com.google.gson.Gson

object SharedPreferencesManger {
    private var sharedPreferences: SharedPreferences? = null

    fun setSharedPreferences(activity: Activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", Context.MODE_PRIVATE)
        }
    }

    fun SaveData(activity: Activity, data_Key: String?, data_Value: String?) {
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.putString(data_Key, data_Value)
            editor.commit()
        } else {
            setSharedPreferences(activity)
        }
    }

    fun SaveData(activity: Activity, data_Key: String?, data_Value: Boolean) {
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.putBoolean(data_Key, data_Value)
            editor.commit()
        } else {
            setSharedPreferences(activity)
        }
    }

    @kotlin.jvm.JvmStatic
    fun LoadData(activity: Activity, data_Key: String?): String? {
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
        } else {
            setSharedPreferences(activity)
        }
        return sharedPreferences!!.getString(data_Key, null)
    }

    fun LoadBoolean(activity: Activity, data_Key: String?): Boolean {
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
        } else {
            setSharedPreferences(activity)
        }
        return sharedPreferences!!.getBoolean(data_Key, false)
    }

    @kotlin.jvm.JvmStatic
    fun SaveData(activity: Activity, data_Key: String?, data_Value: Any?) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            val gson = Gson()
            val StringData = gson.toJson(data_Value)
            editor.putString(data_Key, StringData)
            editor.commit()
        }
    }

    @kotlin.jvm.JvmStatic
    fun loadUserData(activity: Activity): ClientData? {
        setSharedPreferences(activity)
        var loginData: ClientData? = null
        val gson = Gson()
        loginData = gson.fromJson(LoadData(activity, USER_DATA), ClientData::class.java)
        return loginData
    }

    @kotlin.jvm.JvmStatic
    fun clean(activity: Activity) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
        }
    }
}