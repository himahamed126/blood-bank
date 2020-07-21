package com.example.bloodbank.ui.fragment.homeCycle.articles

import com.example.bloodbank.data.model.articles.ArticlesData

interface ArticleContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(articlesList: MutableList<ArticlesData>)
            fun onFailure(message: String)
        }

        fun getArticles(onFinishedListener: OnFinishedListener, apiToken: String, page: Int)
        fun getArticlesWithFilter(onFinishedListener: OnFinishedListener, apiToken: String,
                                  page: Int, keyWord: String, categoryId: Int)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(articlesList: MutableList<ArticlesData>)
        fun onResponseFailure(message: String)
    }

    interface Presenter {
        fun onDestroy()
        fun getMoreData(apiToken: String, pageNo: Int)
        fun requestDataFromServer(apiToken: String)
        fun requestDataFromServerWithFilter(apiToken: String, keyWord: String, categoryId: Int)
    }
}