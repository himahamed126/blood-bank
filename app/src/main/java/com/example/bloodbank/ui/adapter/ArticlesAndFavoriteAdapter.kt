package com.example.bloodbank.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R
import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.data.model.postToggle.PostToggle
import com.example.bloodbank.databinding.ItemArticlesBinding
import com.example.bloodbank.extensions.addFragment
import com.example.bloodbank.extensions.createToast
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.homeCycle.articles.ArticlesDetailsFragment
import com.example.bloodbank.utils.API_TOKEN
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticlesAndFavoriteAdapter(
        private val context: Context,
        var articlesList: MutableList<ArticlesData>,
        private val favorite: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = parent.inflateWithBinding<ItemArticlesBinding>(R.layout.item_articles)
        return ArticlesViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = articlesList[position]
        (holder as ArticlesViewHolder).bind(article)
    }

    private fun postToggleFavourite(position: Int) {
        val article = articlesList[position]
        article.isFavourite = (!article.isFavourite!!)
        if (article.isFavourite!!) {
            (context as AppCompatActivity).createToast(context.getString(R.string.add_to_favorite))
        } else {
            (context as AppCompatActivity).createToast(context.getString(R.string.remove_to_favorite))
            if (favorite) {
                articlesList.removeAt(position)
                notifyDataSetChanged()
            }
        }
        client().postToggleFavourite(article.id!!, API_TOKEN!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<PostToggle> {
                    override fun onComplete() {
                        notifyItemChanged(position)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(it: Throwable) {
                        article.isFavourite = (!article.isFavourite!!)
                        if (article.isFavourite!!) {
                            context.createToast(context.getString(R.string.add_to_favorite))
                            if (favorite) {
                                articlesList.add(position, article)
                                notifyDataSetChanged()
                            }
                        } else {
                            context.createToast(context.getString(R.string.remove_to_favorite))
                        }
                    }

                    override fun onNext(it: PostToggle) {
                    }
                })
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    inner class ArticlesViewHolder(val binding: ItemArticlesBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(article: ArticlesData) {
            binding.apply {
                this.article = article

                executePendingBindings()
                articleContainer.setOnClickListener(this@ArticlesViewHolder)
                itemArticlesBtnLove.setOnClickListener(this@ArticlesViewHolder)
            }
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.article_container -> {
                    val articlesDetailsFragment = ArticlesDetailsFragment()
                    ArticlesDetailsFragment.articlesData = articlesList[adapterPosition]
                    (context as AppCompatActivity).addFragment(R.id.activity_home_fl_content, articlesDetailsFragment)
                }
                R.id.item_articles_btn_love -> postToggleFavourite(adapterPosition)
            }
        }
    }
}