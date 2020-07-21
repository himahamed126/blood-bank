package com.example.bloodbank.ui.fragment.homeCycle.more.favorite

import com.example.bloodbank.data.model.articles.ArticlesData

class FavoritePresenter(private var favoriteView: FavoriteContract.View?)
    : FavoriteContract.Model.OnFinishedListener, FavoriteContract.Presenter {

    private var favoriteModel: FavoriteModel = FavoriteModel()

    override fun onFinished(articlesList: MutableList<ArticlesData>) {
        if (favoriteView != null) {
            favoriteView!!.hideProgress()
        }
        favoriteView!!.setDataToRecyclerView(articlesList)
    }

    override fun onFailure(throwable: Throwable) {
        if (favoriteView != null) {
            favoriteView!!.hideProgress()
        }
        favoriteView!!.onResponseFailure(throwable)
    }

    override fun onDestroy() {
        favoriteView = null
    }

    override fun getMoreData(apiToken: String, pageNo: Int) {
        if (favoriteView != null) {
            favoriteView!!.showProgress()
        }
        favoriteModel.getFavoriteArticles(this, apiToken, pageNo)
    }

    override fun requestDataFromServer(apiToken: String) {
        if (favoriteView != null) {
            favoriteView!!.showProgress()
        }
        favoriteModel.getFavoriteArticles(this, apiToken, 1)
    }
}