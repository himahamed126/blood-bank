package com.example.bloodbank.extensions

import android.app.Activity
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun Activity.createToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

inline fun <T> Call<T>.addEnqueue(
        crossinline onSuccess: (response: Response<T>) -> Unit = { },
        crossinline onFail: (t: Throwable) -> Unit = { }
): Callback<T> {
    val callback = object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFail(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onSuccess(response)
        }
    }
    enqueue(callback)
    return callback
}