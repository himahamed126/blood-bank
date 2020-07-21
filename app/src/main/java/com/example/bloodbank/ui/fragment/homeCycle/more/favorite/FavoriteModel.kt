package com.example.bloodbank.ui.fragment.homeCycle.more.favorite

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.extensions.addEnqueue

class FavoriteModel : FavoriteContract.Model {

    private var favoriteList: MutableList<ArticlesData> = mutableListOf()

    override fun getFavoriteArticles(
            onFinishedListener: FavoriteContract.Model.OnFinishedListener,
            apiToken: String, page: Int) {
        client().getFavoriteArticles(apiToken, page).addEnqueue(
                {
                    favoriteList.addAll(it.body()!!.data!!.data!!)
                    onFinishedListener.onFinished(favoriteList)
                },
                {
                    onFinishedListener.onFailure(it)
                }
        )
    }
}