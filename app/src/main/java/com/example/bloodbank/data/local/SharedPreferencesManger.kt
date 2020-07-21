package com.example.bloodbank.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.bloodbank.data.model.login.ClientData
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.USER_DATA
import com.google.gson.Gson

open class SharedPreferencesManger(var context: Context) {
    private var sharedPreferences: SharedPreferences? = null

    init {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                    "MyBlood", MODE_PRIVATE)
        }
    }

    companion object {
        private var INSTANCE: SharedPreferencesManger? = null

        fun getINSTANCE(context: Context?): SharedPreferencesManger? {
            if (INSTANCE == null) {
                INSTANCE = SharedPreferencesManger(context!!)
            }
            return INSTANCE
        }
    }


    fun loadUserData(): ClientData? {
        val loginData: ClientData?
        val gson = Gson()
        loginData = gson.fromJson(restoreStringValue(USER_DATA), ClientData::class.java)
        return loginData
    }

    fun clean() {
        getINSTANCE(context)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.clear()
            editor.apply()
        }
    }

    fun setPreference(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getApiToken(): String {
        return sharedPreferences!!.getString(API_TOKEN, "")!!
    }

    fun setPreference(key: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun setPreference(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setPreference(key: String, value: Any) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        val gson = Gson()
        val stringData: String = gson.toJson(value)
        editor.putString(key, stringData)
        editor.apply()
    }

    open fun restoreStringValue(key: String?): String? {
        return sharedPreferences!!.getString(key, "Empty")
    }

    fun restoreIntValue(key: String?): Int? {
        return sharedPreferences!!.getInt(key, 0)
    }

    fun restoreBooleanValue(key: String?): Boolean? {
        return sharedPreferences!!.getBoolean(key, false)
    }
}