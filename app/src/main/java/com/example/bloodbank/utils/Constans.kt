package com.example.bloodbank.utils

import com.example.bloodbank.Application
import com.example.bloodbank.data.local.SharedPreferencesManger


const val USER_DATA = "USER_DATA"
const val PHONE = "PHONE"
val API_TOKEN = SharedPreferencesManger.getINSTANCE(Application.getInstance())!!.restoreStringValue("API_TOKEN")
const val PASSWORD = "PASSWORD"
const val LATITUDE = "LATITUDE"
const val LONGITUDE = "LONGITUDE"
const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
const val IS_REMEMBER = "IS_REMEMBER"
const val FIRST_LAUNCH = true

