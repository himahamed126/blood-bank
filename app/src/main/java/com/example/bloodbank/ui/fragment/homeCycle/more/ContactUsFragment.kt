package com.example.bloodbank.ui.fragment.homeCycle.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.model.contactUs.ContactUs
import com.example.bloodbank.databinding.FragmentContactUsBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.HelperMethods.dismissProgressDialog
import com.example.bloodbank.helper.HelperMethods.showProgressDialog
import com.example.bloodbank.helper.HelperMethods.showToast
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentContactUsBinding
    private var apiToken: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_contact_us, container)
        apiToken = LoadData(activity!!, API_TOKEN)
        return binding.root
    }

    private fun contactUs() {
        val title = binding.fragmentContactUsEdMessageTitle.text.toString()
        val content = binding.fragmentContactUsEdMessageContent.text.toString()
        showProgressDialog(activity, getString(R.string.please_wait))
        client.contactUs(apiToken!!, title, content).enqueue(object : Callback<ContactUs> {
            override fun onResponse(call: Call<ContactUs>, response: Response<ContactUs>) {
                dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    showToast(activity, response.body()!!.msg)
                } else {
                    showToast(activity, response.body()!!.msg)
                }
            }

            override fun onFailure(call: Call<ContactUs>, t: Throwable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_contact_us_btn_send -> contactUs()
        }
    }
}