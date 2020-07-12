package com.example.bloodbank.ui.fragment.homeCycle.articles

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
import com.example.bloodbank.databinding.FragmentArticleBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods
import com.example.bloodbank.utils.OnEndLess
import com.example.bloodbank.ui.adapter.ArticlesAndFavoriteAdapter
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentArticleBinding

    private var articlesAndFavoriteAdapter: ArticlesAndFavoriteAdapter? = null
    private var categoriesAdapter: GeneralResponseAdapter? = null
    private var articlesList: MutableList<ArticlesData>? = null
    private var onEndLess: OnEndLess? = null
    private var maxPage = 0
    private var filterPost = false
    private var keyWord: String? = null
    lateinit var apiToken: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_article, container)
        init()
        return binding.root
    }

    private fun init() {
        apiToken = LoadData(requireActivity(), API_TOKEN)!!
        if (apiToken.isEmpty()) {
            Log.i(TAG, "null")
        } else {
            Log.i(TAG, apiToken)
        }

        category
        getArticles(1)

        binding.fragmentArticlesBtnSearch.setOnClickListener(this)
        initRec()
    }

    private val category: Unit
        get() {
            categoriesAdapter = GeneralResponseAdapter(activity)
            HelperMethods.getSpinnerCityData2(client().categories, categoriesAdapter!!,
                    binding.fragmentArticlesSp, getString(R.string.all_articles), activity)
        }

    private fun initRec() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.fragmentArticlesRvArticles.layoutManager = linearLayoutManager

        onEndLess = object : OnEndLess(linearLayoutManager, 1) {
            override fun onLoadMore(current_page: Int) {
                if (current_page < maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if (filterPost) {
                            getArticlesWithFilter(current_page)
                        } else {
                            getArticles(current_page)
                        }
                    } else {
                        onEndLess!!.current_page = onEndLess!!.previous_page
                    }
                } else {
                    onEndLess!!.current_page = onEndLess!!.previous_page
                }
            }
        }
    }

    private fun getArticles(page: Int) {
        articlesList = mutableListOf()
        client().getArticles(apiToken, page).enqueue(object : Callback<Articles> {
            override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
//                dismissProgressDialog();
                if (response.body()!!.status == 1) {
                    articlesList!!.addAll(response.body()!!.data!!.data!!)
                    maxPage = response.body()!!.data!!.lastPage!!
                    articlesAndFavoriteAdapter = ArticlesAndFavoriteAdapter(activity!!, articlesList!!, false)
                    binding.fragmentArticlesRvArticles.adapter = articlesAndFavoriteAdapter
                } else {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {}
        })
    }

    private fun getArticlesWithFilter(page: Int) {
        articlesList = mutableListOf()
        keyWord = binding.fragmentArticlesEdSearch.text.toString()
        HelperMethods.showProgressDialog(activity, getString(R.string.please_wait))
        client().getArticlesWithFilter(LoadData(requireActivity(), API_TOKEN)!!, page,
                keyWord!!, binding.fragmentArticlesSp.selectedItemPosition).enqueue(object : Callback<Articles> {
            override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                HelperMethods.dismissProgressDialog()
                if (response.body()!!.status == 1) {
                    articlesList!!.clear()
                    articlesList!!.addAll(response.body()!!.data!!.data!!)
                    maxPage = response.body()!!.data!!.lastPage!!
                    articlesAndFavoriteAdapter = ArticlesAndFavoriteAdapter(activity!!, articlesList!!, false)
                    binding.fragmentArticlesRvArticles.adapter = articlesAndFavoriteAdapter
                } else {
                    Log.i(TAG, response.body()!!.msg.toString())
                }
            }

            override fun onFailure(call: Call<Articles>, t: Throwable) {}
        })
    }

    companion object {
        private const val TAG = "ArticleFragment"
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_articles_btn_search -> if (categoriesAdapter!!.selectedId == 0 && keyWord == "" && filterPost) {
                filterPost = false
                getArticles(1)
            } else {
                getArticlesWithFilter(1)
            }
        }
    }
}