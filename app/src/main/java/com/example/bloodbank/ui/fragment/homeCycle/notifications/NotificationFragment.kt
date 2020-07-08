package com.example.bloodbank.ui.fragment.homeCycle.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.model.notifications.Notification
import com.example.bloodbank.data.model.notifications.NotificationData
import com.example.bloodbank.databinding.FragmentNotificationBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.HelperMethods.dismissProgressDialog
import com.example.bloodbank.helper.HelperMethods.showProgressDialog
import com.example.bloodbank.ui.adapter.NotificationAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : BaseFragment() {

    private var notificationData: MutableList<NotificationData>? = null
    private var notificationAdapter: NotificationAdapter? = null
    private val TAG = "notification"
    private var apiToken: String? = null
    lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_notification, container)
        apiToken = LoadData(activity!!, API_TOKEN)
        getNotification(1)
        initRecycler()
        return binding.root
    }

    private fun initRecycler() {
        notificationData = mutableListOf()
        val linearLayoutManager = LinearLayoutManager(activity)
        notificationAdapter = NotificationAdapter(notificationData!!)
        binding.fragmentNotificationRv.layoutManager = linearLayoutManager
        binding.fragmentNotificationRv.adapter = notificationAdapter
    }

    private fun getNotification(page: Int) {
        showProgressDialog(activity, getString(R.string.please_wait))
        client.getNotifications(apiToken!!, page).enqueue(object : Callback<Notification> {
            override fun onResponse(call: Call<Notification>, response: Response<Notification>) {
                dismissProgressDialog()
                try {
                    if (response.body()!!.status == 1) {
                        notificationData!!.addAll(response.body()!!.data!!.data!!)
                        notificationAdapter!!.notifyDataSetChanged()
                    } else {
                        Log.i(TAG, response.body()!!.msg.toString())
                    }
                } catch (e: Exception) {
                    Log.i(TAG, e.message.toString())
                }
            }

            override fun onFailure(call: Call<Notification>, t: Throwable) {}
        })
    }
}