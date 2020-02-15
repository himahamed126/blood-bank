package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.articles.ArticlesData;
import com.example.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bloodbank.helper.HelperMethods.onLoadImageFromUrl;

public class ArticlesDetailsFragment extends BaseFragment {
    @BindView(R.id.fragment_articles_details_iv_article)
    ImageView fragmentArticlesDetails;
    @BindView(R.id.fragment_articles_details_tv_content)
    TextView fragmentArticlesDetailsTvArticle;
    @BindView(R.id.fragment_articles_details_tv_title)
    TextView fragmentArticlesDetailsTvTitle;
    @BindView(R.id.fragment_articles_details_iv_favorite)
    ImageView fragmentArticlesDetailsIvFavorite;

    public ArticlesData articlesData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_articles_details, container, false);
        ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        onLoadImageFromUrl(fragmentArticlesDetails, articlesData.getThumbnailFullPath(), getActivity());
        fragmentArticlesDetailsTvTitle.setText(articlesData.getTitle());
        fragmentArticlesDetailsTvArticle.setText(articlesData.getContent());
        if (articlesData.getIsFavourite()) {
            fragmentArticlesDetailsIvFavorite.setImageResource(R.drawable.ic_favorite_fill);
        } else {
            fragmentArticlesDetailsIvFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
