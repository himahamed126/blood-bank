package com.example.bloodbank.ui.fragment.homeCycle.articles

import com.example.bloodbank.data.api.ApiClient
import com.example.bloodbank.data.model.articles.Articles
import com.example.bloodbank.data.model.articles.ArticlesData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleModel : ArticleContract.Model {

    private lateinit var articlesList: MutableList<ArticlesData>

    override fun getArticles(onFinishedListener: ArticleContract.Model.OnFinishedListener,
                             apiToken: String, page: Int) {
        articlesList = mutableListOf()
        ApiClient.client().getArticles(apiToken, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Articles> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it.message.toString())
                    }

                    override fun onNext(it: Articles) {
                        articlesList.addAll(it.data!!.data!!)
                        onFinishedListener.onFinished(articlesList)
                    }
                })
    }

    override fun getArticlesWithFilter(onFinishedListener: ArticleContract.Model.OnFinishedListener,
                                       apiToken: String, page: Int, keyWord: String, categoryId: Int) {
        articlesList = mutableListOf()
        ApiClient.client().getArticlesWithFilter(apiToken, page, keyWord, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Articles> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it.message.toString())
                    }

                    override fun onNext(it: Articles) {
                        articlesList.addAll(it.data!!.data!!)
                        onFinishedListener.onFinished(articlesList)
                    }
                })
    }
}