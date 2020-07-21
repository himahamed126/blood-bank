package com.example.bloodbank.ui.fragment.homeCycle.more.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbank.R
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.databinding.FragmentFavoriteBinding
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.adapter.ArticlesAndFavoriteAdapter
import com.example.bloodbank.ui.fragment.BaseFragment
import com.example.bloodbank.ui.views.LoadingDialog
import com.example.bloodbank.utils.API_TOKEN

class FavoriteFragment : BaseFragment(), FavoriteContract.View {

    private var articlesAndFavoriteAdapter: ArticlesAndFavoriteAdapter? = null
    private var articlesList: MutableList<ArticlesData> = mutableListOf()

    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoritePresenter: FavoritePresenter
    lateinit var loadingDialog: LoadingDialog
    lateinit var apiToken: String
    lateinit var linearLayoutManager: LinearLayoutManager

    private var pageNo: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_favorite, container)

        favoritePresenter = FavoritePresenter(this)
        init()
        favoritePresenter.requestDataFromServer(apiToken)

        return binding.root
    }

    private fun init() {
        apiToken = API_TOKEN!!
        loadingDialog = LoadingDialog(requireActivity())
        articlesAndFavoriteAdapter = ArticlesAndFavoriteAdapter(requireActivity(), articlesList, true)
        linearLayoutManager = LinearLayoutManager(activity)

        binding.rvFavoriteArticlesList.apply {
            adapter = articlesAndFavoriteAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun showProgress() {
        loadingDialog.startLoadingDialog(getString(R.string.please_wait))
    }

    override fun hideProgress() {
        loadingDialog.dismissDialog()
    }

    override fun setDataToRecyclerView(articlesList: MutableList<ArticlesData>) {
        this.articlesList.addAll(articlesList)
        articlesAndFavoriteAdapter!!.notifyDataSetChanged()
        pageNo++
    }

    override fun onResponseFailure(throwable: Throwable) {
        requireActivity().createToast(throwable.toString())
    }
}