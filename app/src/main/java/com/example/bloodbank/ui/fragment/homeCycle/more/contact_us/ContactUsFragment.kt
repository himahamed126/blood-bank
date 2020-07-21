package com.example.bloodbank.ui.fragment.homeCycle.more.contact_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentContactUsBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods

class ContactUsFragment : BaseFragment(), ContactUsContract.View, View.OnClickListener {

    lateinit var binding: FragmentContactUsBinding
    private lateinit var apiToken: String
    private lateinit var contactUsPresenter: ContactUsPresenter
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_contact_us, container)

        init()
        return binding.root
    }

    private fun init() {
        contactUsPresenter = ContactUsPresenter(this)
        loadingDialog = LoadingDialog(activity)
        apiToken = API_TOKEN!!

        binding.fragmentContactUsBtnSend.setOnClickListener(this)
        binding.fragmentContactUsIvFb.setOnClickListener(this)
        binding.fragmentContactUsIvInsta.setOnClickListener(this)
        binding.fragmentContactUsIvYoutube.setOnClickListener(this)
        binding.fragmentContactUsIvTwitter.setOnClickListener(this)
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun onResponseFailure(string: String) {
        requireActivity().createToast(string)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_contact_us_btn_send -> {
                contactUsPresenter.requestContactUs(
                        apiToken,
                        binding.fragmentContactUsEdMessageTitle.text.toString(),
                        binding.fragmentContactUsEdMessageContent.text.toString(),
                        this.requireActivity())
            }
            R.id.fragment_contact_us_iv_fb -> {
                HelperMethods.openSocialMediaIntent(
                        "https://www.facebook.com/profile.php?id=100005279447315",
                        "com.facebook.katana",
                        this.requireActivity())
            }
            R.id.fragment_contact_us_iv_insta -> {
                HelperMethods.openSocialMediaIntent(
                        "https://www.instagram.com",
                        "com.instagram.android",
                        this.requireActivity())
            }
            R.id.fragment_contact_us_iv_youtube -> {
                HelperMethods.openSocialMediaIntent(
                        "https://www.youtube.com/channel/UC5wO1N8kI20qt-sG7uF6AEA",
                        "com.google.android.youtube",
                        this.requireActivity())
            }
            R.id.fragment_contact_us_iv_twitter -> {
                HelperMethods.openSocialMediaIntent(
                        "https://twitter.com/ibrahim43488287",
                        "com.twitter.android\"",
                        this.requireActivity())
            }
        }
    }
}