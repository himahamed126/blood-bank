package com.example.bloodbank.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.model.notifications.NotificationData
import com.example.bloodbank.databinding.ItemNotificationBinding
import com.example.bloodbank.extensions.inflateWithBinding

class NotificationAdapter(private var notificationsList: MutableList<NotificationData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val viewBinding = parent.inflateWithBinding<ItemNotificationBinding>(R.layout.item_notification)
        return NotificationViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notification = notificationsList[position]
        (holder as NotificationViewHolder).bind(notification)
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    inner class NotificationViewHolder(var binding: ItemNotificationBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationData) {
            binding.apply {
                this.notification = notification
                executePendingBindings()
            }
        }
    }
}