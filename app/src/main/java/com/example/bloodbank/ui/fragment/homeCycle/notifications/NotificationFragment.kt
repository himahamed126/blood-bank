package com.example.bloodbank.ui.fragment.homeCycle.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.model.notifications.NotificationData
import com.example.bloodbank.databinding.FragmentNotificationBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.adapter.NotificationAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN

class NotificationFragment : BaseFragment(), NotificationContract.View {

    lateinit var binding: FragmentNotificationBinding
    private var notificationData: MutableList<NotificationData> = mutableListOf()
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var apiToken: String

    lateinit var notificationPresenter: NotificationPresenter
    lateinit var loadingDialog: LoadingDialog

    lateinit var linearLayoutManager: LinearLayoutManager
    private var pageNo = 1
    private var previousTotal: Int = 0
    private var visibleThreshold: Int = 5
    var loading: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_notification, container)
        notificationPresenter = NotificationPresenter(this)
        init()
        notificationPresenter.requestDataFromServer(apiToken)
        setListener()
        return binding.root
    }

    private fun init() {
        apiToken = API_TOKEN!!
        loadingDialog = LoadingDialog(activity)

        notificationAdapter = NotificationAdapter(notificationData)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvNotificationList.layoutManager = linearLayoutManager
        binding.rvNotificationList.adapter = notificationAdapter
    }

    private fun setListener() {

        binding.rvNotificationList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = binding.rvNotificationList.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()


                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount
                        <= firstVisibleItem + visibleThreshold) {
                    notificationPresenter.getMoreData(apiToken, pageNo)
                    loading = true
                }
            }
        })
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(message: String) {
        requireActivity().createToast(message)
    }

    override fun setDataToRecyclerView(notificationList: MutableList<NotificationData>) {
        notificationData.addAll(notificationList)
        notificationAdapter.notifyDataSetChanged()
        pageNo++
    }
}