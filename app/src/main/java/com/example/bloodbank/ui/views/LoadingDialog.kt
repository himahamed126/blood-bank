package com.example.bloodbank.ui.views

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.bloodbank.R

class LoadingDialog(var activity: Activity?) {

    private var dialog: AlertDialog? = null

    fun startLoadingDialog(string: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val view: View = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null)

        val textView = view.findViewById<TextView>(R.id.dialog_progress_tv)
        textView.text = string

        builder.setView(view)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog!!.show()
    }

    fun dismissDialog() {
        dialog!!.cancel()
    }
}


