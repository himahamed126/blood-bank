package com.example.bloodbank.ui.fragment.homeCycle.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.databinding.FragmentArticleBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.adapter.ArticlesAndFavoriteAdapter
import com.example.bloodbank.ui.adapter.GeneralResponseAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN
import com.example.bloodbank.utils.HelperMethods

class ArticleFragment : BaseFragment(), ArticleContract.View, View.OnClickListener {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var articlesAndFavoriteAdapter: ArticlesAndFavoriteAdapter
    private lateinit var articlesList: MutableList<ArticlesData>
    private var categoriesAdapter: GeneralResponseAdapter? = null
    private var filterPost = false
    private var keyWord: String? = null

    private lateinit var articlePresenter: ArticlePresenter
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var pageNo: Int = 1
    private var previousTotal: Int = 0
    private val visibleThreshold: Int = 5
    private var loading: Boolean = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_article, container)
        articlePresenter = ArticlePresenter(this)
        init()
        articlePresenter.requestDataFromServer(API_TOKEN!!)
        return binding.root
    }

    private fun init() {
        category

        binding.fragmentArticlesBtnSearch.setOnClickListener(this)
        loadingDialog = LoadingDialog(activity)
        articlesList = mutableListOf()
        articlesAndFavoriteAdapter = ArticlesAndFavoriteAdapter(this.requireActivity(), this.articlesList, false)
        linearLayoutManager = LinearLayoutManager(activity)

        binding.rvArticlesList.apply {
            layoutManager = linearLayoutManager
            adapter = articlesAndFavoriteAdapter
        }
        setListener()
    }

    private val category: Unit
        get() {
            categoriesAdapter = GeneralResponseAdapter(activity)
            HelperMethods.getSpinnerCityData2(client().categories, categoriesAdapter!!,
                    binding.fragmentArticlesSp, getString(R.string.all_articles), activity)
        }


    private fun setListener() {
        binding.rvArticlesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = binding.rvArticlesList.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    articlePresenter.getMoreData(API_TOKEN!!, pageNo)
                    loading = true
                }
            }
        })
    }

    override fun showProgress() {
//        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
//        loadingDialog.dismissDialog()
    }

    override fun setDataToRecyclerView(articlesList: MutableList<ArticlesData>) {
        this.articlesList.addAll(articlesList)
        articlesAndFavoriteAdapter.notifyDataSetChanged()
        pageNo++
    }

    override fun onResponseFailure(message: String) {
        requireActivity().createToast(message)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragment_articles_btn_search -> {
                articlesList.clear()
                if (categoriesAdapter!!.selectedId == 0 && keyWord == "" && filterPost) {
                    filterPost = false
                } else {
                    articlePresenter.requestDataFromServerWithFilter(API_TOKEN!!,
                            binding.fragmentArticlesEdSearch.text.toString(),
                            binding.fragmentArticlesSp.selectedItemPosition)
                }
            }
        }
    }
}