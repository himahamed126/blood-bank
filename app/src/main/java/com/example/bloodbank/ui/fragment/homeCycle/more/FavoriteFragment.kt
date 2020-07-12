package com.example.bloodbank.ui.fragment.homeCycle.more

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.local.SharedPreferencesManger.LoadData
import com.example.bloodbank.data.model.articles.Articles
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.databinding.FragmentFavoriteBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods.dismissProgressDialog
import com.example.bloodbank.utils.HelperMethods.showProgressDialog
import com.example.bloodbank.utils.OnEndLess
import com.example.bloodbank.ui.adapter.ArticlesAndFavoriteAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : BaseFragment() {

    private var articlesAndFavoriteAdapter: ArticlesAndFavoriteAdapter? = null
    private var articlesList: MutableList<ArticlesData>? = null
    private var onEndLess: OnEndLess? = null
    private var maxPage = 0
    lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_favorite, container)
        getFavoriteArticles(1)
        initRec()
        return binding.root
    }

    private fun initRec() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.fragmentFavoriteRv.layoutManager = linearLayoutManager
        onEndLess = object : OnEndLess(linearLayoutManager, 1) {
            override fun onLoadMore(current_page: Int) {
                if (current_page < maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        getFavoriteArticles(current_page)
                    } else {
                        onEndLess!!.current_page = onEndLess!!.previous_page
                    }
                } else {
                    onEndLess!!.current_page = onEndLess!!.previous_page
                }
            }
        }
    }

    private fun getFavoriteArticles(page: Int) {
        articlesList = mutableListOf()
        showProgressDialog(activity, getString(R.string.please_wait))
        client().getFavoriteArticles(LoadData(activity!!, API_TOKEN)!!, page).enqueue(object : Callback<Articles> {
            override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    articlesList!!.addAll(response.body()!!.data!!.data!!)
                    maxPage = response.body()!!.data!!.lastPage!!
                    articlesAndFavoriteAdapter = ArticlesAndFavoriteAdapter(activity!!, articlesList!!, true)
                    binding.fragmentFavoriteRv.adapter = articlesAndFavoriteAdapter
                } else {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {}
        })
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}