package com.example.bloodbank.ui.fragment.homeCycle.articles

import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.extensions.addEnqueue

class ArticleModel : ArticleContract.Model {

    private lateinit var articlesList: MutableList<ArticlesData>

    override fun getArticles(onFinishedListener: ArticleContract.Model.OnFinishedListener,
                             apiToken: String, page: Int) {
        articlesList = mutableListOf()
        ApiClient.client().getArticles(apiToken, page).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        articlesList.addAll(it.body()!!.data!!.data!!)
                        onFinishedListener.onFinished(articlesList)
                    } else {
                        onFinishedListener.onFailure(it.body()!!.msg!!)
                    }
                },
                {
                    onFinishedListener.onFailure(it.message.toString())
                }
        )
    }

    override fun getArticlesWithFilter(onFinishedListener: ArticleContract.Model.OnFinishedListener,
                                       apiToken: String, page: Int, keyWord: String, categoryId: Int) {
        articlesList = mutableListOf()
        ApiClient.client().getArticlesWithFilter(apiToken, page, keyWord, categoryId).addEnqueue(
                {
                    articlesList.addAll(it.body()!!.data!!.data!!)
                    onFinishedListener.onFinished(articlesList)
                },
                {
                    onFinishedListener.onFailure(it.message.toString())
                }
        )
    }
}