package com.example.bloodbank.ui.fragment.homeCycle.donations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.model.donations.DonationData
import com.example.bloodbank.data.model.donations.Donations
import com.example.bloodbank.databinding.FragmentDonationBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.helper.API_TOKEN
import com.example.bloodbank.helper.HelperMethods
import com.example.bloodbank.helper.OnEndLess
import com.example.bloodbank.ui.adapter.DonationAdapter
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationFragment : BaseFragment() {

    lateinit var binding: FragmentDonationBinding
    private var donationAdapter: DonationAdapter? = null
    private var donationsList: MutableList<DonationData>? = mutableListOf()
    private var linearLayoutManager: LinearLayoutManager? = null
    private var onEndLess: OnEndLess? = null
    private var maxPage = 0
    private var bloodTypeAdapter: GeneralResponseAdapter? = null
    private var cityAdapter: GeneralResponseAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_donation, container)
        init()
        return binding.root
    }

    private fun init() {
        governoters()
        bloodType()

        getAllDonation(1)
        initRec()
    }

    private fun governoters() {

        cityAdapter = GeneralResponseAdapter(this.activity!!)
        HelperMethods.getSpinnerCityData2(client.governorates, cityAdapter!!, binding.fragmentDonationSpCity,
                getString(R.string.governorate), activity)
    }

    private fun bloodType() {
        bloodTypeAdapter = GeneralResponseAdapter(this.activity!!)
        HelperMethods.getSpinnerCityData(client.getbloodTypes(), bloodTypeAdapter!!, binding.fragmentDonationSpBloodType, getString(R.string.blood_type), 0, object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                getAllDonationWithFilter(1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    private fun initRec() {

        linearLayoutManager = LinearLayoutManager(activity)
        binding.fragmentDonationRvDonationRequest.layoutManager = linearLayoutManager
        donationsList = mutableListOf()
        binding.fragmentDonationRvDonationRequest.adapter = donationAdapter
        onEndLess = object : OnEndLess(linearLayoutManager, 1) {
            override fun onLoadMore(current_page: Int) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess!!.previous_page = current_page
                        getAllDonation(current_page)
                        donationAdapter!!.notifyDataSetChanged()
                    } else {
                        onEndLess!!.current_page = onEndLess!!.previous_page
                    }
                } else {
                    onEndLess!!.current_page = onEndLess!!.previous_page
                }
            }
        }
        binding.fragmentDonationRvDonationRequest.addOnScrollListener(onEndLess as OnEndLess)
    }

    private fun getAllDonation(page: Int) {
//        showProgressDialog(getActivity(), getString(R.string.please_wait));
        client.getAllDonation(LoadData(activity!!, API_TOKEN)!!, page).enqueue(object : Callback<Donations> {
            override fun onResponse(call: Call<Donations>, response: Response<Donations>) {
//                dismissProgressDialog();
                if (response.body()!!.status == 1) {
                    donationsList!!.addAll(response.body()!!.data!!.data!!)
                    maxPage = response.body()!!.data!!.lastPage!!
                    donationAdapter = DonationAdapter(activity!!, donationsList!!)
                    donationAdapter!!.notifyDataSetChanged()
                } else {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Donations>, t: Throwable) {}
        })
    }

    private fun getAllDonationWithFilter(page: Int) {
        donationsList = mutableListOf()
        HelperMethods.showProgressDialog(activity, getString(R.string.please_wait))
        client.getAllDonationWithFilter(LoadData(activity!!, API_TOKEN)!!,
                binding.fragmentDonationSpBloodType!!.selectedItemPosition, binding.fragmentDonationSpCity!!.selectedItemPosition,
                page).enqueue(object : Callback<Donations> {
            override fun onResponse(call: Call<Donations>, response: Response<Donations>) {
                HelperMethods.dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    donationsList!!.addAll(response.body()!!.data!!.data!!)
                    maxPage = response.body()!!.data!!.lastPage!!
                    donationAdapter = DonationAdapter(activity!!, donationsList!!)
                    binding.fragmentDonationRvDonationRequest.adapter = donationAdapter
                } else {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Donations>, t: Throwable) {}
        })
    }

    override fun onBack() {}

    companion object {
        private const val TAG = "DonationFragment"
    }
}