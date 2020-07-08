package com.example.bloodbank.ui.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.model.donations.DonationData
import com.example.bloodbank.databinding.ItemDonationRequestBinding
import com.example.bloodbank.extensions.addFragment
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.homeCycle.donations.DonationDetailsFragment

class DonationAdapter(private val context: Context, private val donationsList: MutableList<DonationData>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var requestCall = 123
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val viewBinding = parent.inflateWithBinding<ItemDonationRequestBinding>(R.layout.item_donation_request)
        return DonationViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val donation = donationsList[position]
        (holder as DonationViewHolder).bind(donation)
    }

    override fun getItemCount(): Int {
        return donationsList.size
    }

    inner class DonationViewHolder(var binding: ItemDonationRequestBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(donation: DonationData) {
            binding.apply {
                this.donation = donation
                executePendingBindings()
                itemDonationIvInfo.setOnClickListener(this@DonationViewHolder)
                itemDonationIvPhone.setOnClickListener(this@DonationViewHolder)
            }
        }

        override fun onClick(view: View) {
            val donation = donationsList[adapterPosition]
            when (view.id) {
                R.id.item_donation_iv_info -> {
                    val donationDetailsFragment = DonationDetailsFragment()
                    DonationDetailsFragment.donation = donation
                    (context as AppCompatActivity).addFragment(R.id.activity_home_fl_content, donationDetailsFragment)
                }
                R.id.item_donation_iv_phone -> {
                    val call = Intent(Intent.ACTION_CALL)
                    call.data = Uri.parse("tel:" + donation.client!!.phone)
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((context as AppCompatActivity), arrayOf(Manifest.permission.CALL_PHONE), requestCall)
                    } else {
                        context.startActivity(call)
                    }
                }
            }
        }
    }
}