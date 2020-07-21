package com.example.bloodbank.ui.fragment.homeCycle.articles

import com.example.bloodbank.data.model.articles.ArticlesData

class ArticlePresenter(private var articleView: ArticleContract.View?)
    : ArticleContract.Model.OnFinishedListener, ArticleContract.Presenter {

    private var articleModel: ArticleContract.Model = ArticleModel()


    override fun onFinished(articlesList: MutableList<ArticlesData>) {
        if (articleView != null) {
            articleView!!.hideProgress()
        }
        articleView!!.setDataToRecyclerView(articlesList)
    }

    override fun onFailure(message: String) {
        if (articleView != null) {
            articleView!!.hideProgress()
        }
        articleView!!.onResponseFailure(message)
    }

    override fun onDestroy() {
        articleView = null
    }

    override fun getMoreData(apiToken: String, pageNo: Int) {
        if (articleView != null) {
            articleView!!.showProgress()
        }
        articleModel.getArticles(this, apiToken, pageNo)
    }

    override fun requestDataFromServer(apiToken: String) {
        if (articleView != null) {
            articleView!!.showProgress()
        }
        articleModel.getArticles(this, apiToken, 1)
    }

    override fun requestDataFromServerWithFilter(apiToken: String, keyWord: String, categoryId: Int) {
        if (articleView != null) {
            articleView!!.showProgress()
        }
        articleModel.getArticlesWithFilter(this, apiToken, 1, keyWord, categoryId)
    }
}