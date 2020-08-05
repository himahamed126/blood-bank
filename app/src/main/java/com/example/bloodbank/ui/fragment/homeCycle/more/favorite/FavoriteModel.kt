package com.example.bloodbank.ui.fragment.homeCycle.more.favorite

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.articles.Articles
import com.example.bloodbank.data.model.articles.ArticlesData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavoriteModel : FavoriteContract.Model {

    private var favoriteList: MutableList<ArticlesData> = mutableListOf()

    override fun getFavoriteArticles(
            onFinishedListener: FavoriteContract.Model.OnFinishedListener,
            apiToken: String, page: Int) {
        client().getFavoriteArticles(apiToken, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Articles> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        onFinishedListener.onFailure(it)
                    }

                    override fun onNext(it: Articles) {
                        favoriteList.addAll(it.data!!.data!!)
                        onFinishedListener.onFinished(favoriteList)
                    }
                })
    }
}