package com.example.bloodbank.ui.fragment.homeCycle.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank.R
import com.example.bloodbank.data.model.articles.ArticlesData
import com.example.bloodbank.databinding.FragmentArticlesDetailsBinding
import com.example.bloodbank.extensions.inflateWithBinding
import com.example.bloodbank.ui.fragment.BaseFragment

open class ArticlesDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentArticlesDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupActivity()
        binding = inflater.inflateWithBinding(R.layout.fragment_articles_details, container)
        binding.article = articlesData
        return binding.root
    }

    companion object {
        var articlesData: ArticlesData? = null
    }

}