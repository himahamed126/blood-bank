package com.example.bloodbank.ui.fragment.homeCycle.donations.preview_donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.donations.DonationData
import com.example.bloodbank.databinding.FragmentDonationBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.adapter.DonationAdapter
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods

class DonationFragment : BaseFragment(), DonationContract.View {

    lateinit var binding: FragmentDonationBinding
    private var donationsList: MutableList<DonationData>? = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var donationAdapter: DonationAdapter
    private lateinit var cityAdapter: GeneralResponseAdapter
    private lateinit var bloodTypeAdapter: GeneralResponseAdapter

    lateinit var donationPresenter: DonationPresenter
    private lateinit var loadingDialog: LoadingDialog

    private var pageNo = 1
    private var previousTotal: Int = 0
    private var visibleThreshold: Int = 5
    var loading: Boolean = true

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_donation, container)
        donationPresenter = DonationPresenter(this)
        loadingDialog = LoadingDialog(activity)
        init()
        donationPresenter.requestDataFromServer(API_TOKEN!!)
        return binding.root
    }

    private fun init() {

        donationAdapter = DonationAdapter(requireActivity(), donationsList!!)
        linearLayoutManager = LinearLayoutManager(activity)

        binding.rvDonationList.apply {
            layoutManager = linearLayoutManager
            adapter = donationAdapter
        }

        setSpinners()
        setListener()
    }

    private fun setSpinners() {
        bloodTypeAdapter = GeneralResponseAdapter(this.requireActivity())
        cityAdapter = GeneralResponseAdapter(this.requireActivity())

        HelperMethods.setSpinnerWithListener(
                client().getbloodTypes(), bloodTypeAdapter, binding.fragmentDonationSpBloodType,
                getString(R.string.blood_type), 0, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (position > 0) {
                    donationPresenter.requestDonationsWithFilter(
                            API_TOKEN!!, position,
                            binding.fragmentDonationSpCity.selectedItemPosition, pageNo
                            , activity!!)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })

        HelperMethods.getSpinnerCityData2(client().governorates, cityAdapter, binding.fragmentDonationSpCity,
                getString(R.string.governorate), activity)

    }

    private fun setListener() {

        binding.rvDonationList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = binding.rvDonationList.childCount
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
                    donationPresenter.getMoreData(API_TOKEN!!, pageNo)
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

    override fun setDataToRecyclerView(donationList: MutableList<DonationData>) {
        this.donationsList!!.clear()
        this.donationsList!!.addAll(donationList)
        donationAdapter.notifyDataSetChanged()
        pageNo++
    }

    override fun onResponseFailure(message: String) {
        requireActivity().createToast(message)
    }

    override fun onBack() {
        requireActivity().onBackPressed()
    }
}