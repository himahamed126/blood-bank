package com.example.bloodbank.extensions

import android.view.View
import android.widget.ImageView
import com.example.bloodbank.R

fun View.visible(imageView: ImageView) {
    if (visibility == View.GONE) {
        visibility = View.VISIBLE
        imageView.setImageResource(R.drawable.ic_remove)
    } else {
        visibility = View.GONE
        imageView.setImageResource(R.drawable.ic_add)
    }
}