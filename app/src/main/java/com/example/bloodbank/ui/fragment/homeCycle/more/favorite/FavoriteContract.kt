package com.example.bloodbank.ui.fragment.homeCycle.more.favorite

import com.example.bloodbank.data.model.articles.ArticlesData

interface FavoriteContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(articlesList: MutableList<ArticlesData>)
            fun onFailure(throwable: Throwable)
        }

        fun getFavoriteArticles(onFinishedListener: OnFinishedListener, apiToken: String, page: Int)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(articlesList: MutableList<ArticlesData>)
        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun getMoreData(apiToken: String, pageNo: Int)
        fun requestDataFromServer(apiToken: String)
    }
}