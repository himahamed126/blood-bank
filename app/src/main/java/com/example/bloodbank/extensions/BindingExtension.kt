package com.example.bloodbank.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.bloodbank.databinding.FragmentNotificationBinding

fun <T : ViewDataBinding> LayoutInflater.inflateWithBinding(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup?,
        attachToRoot: Boolean = false
): T {
    return DataBindingUtil.inflate(this, layoutRes, parent, attachToRoot) as T
}


fun <T : ViewDataBinding> ViewGroup.inflateWithBinding(
        @LayoutRes layoutRes: Int,
        attachToRoot: Boolean = false
): T {
    val layoutInflater = LayoutInflater.from(context)
    return DataBindingUtil.inflate(layoutInflater, layoutRes, this, attachToRoot) as T
}