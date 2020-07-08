package com.example.bloodbank.ui.fragment.homeCycle.donations

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.data.model.donations.DonationData
import com.example.bloodbank.databinding.FragmentDonationDetailsBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment

open class DonationDetailsFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDonationDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_donation_details, container)
        binding.donation = donation
        binding.fragmentDonationDetailsBtnCall.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + donation!!.client!!.phone)
        startActivity(intent)
    }

    companion object {
        var donation: DonationData? = null
    }

    override fun onBack() {
        baseActivity!!.superBackPressed()
    }
}