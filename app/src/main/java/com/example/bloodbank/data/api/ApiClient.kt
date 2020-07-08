package com.example.bloodbank.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://ipda3-tech.com/blood-bank/api/v1/"
    private var retrofit: Retrofit? = null
    @kotlin.jvm.JvmStatic
    val client: ApiServices
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!.create(ApiServices::class.java)
        }
}