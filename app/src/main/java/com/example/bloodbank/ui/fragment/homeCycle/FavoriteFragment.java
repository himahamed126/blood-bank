package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.ArticlesAndFavoriteAdapter;
import com.example.bloodbank.data.model.articles.Articles;
import com.example.bloodbank.data.model.articles.ArticlesData;
import com.example.bloodbank.helper.OnEndLess;
import com.example.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;

public class FavoriteFragment extends BaseFragment {
    @BindView(R.id.fragment_favorite_rv)
    RecyclerView fragmentFavoriteRv;

    private ArticlesAndFavoriteAdapter articlesAndFavoriteAdapter;
    private List<ArticlesData> articlesList;
    private OnEndLess onEndLess;
    private int maxPage;
    private static final String TAG = "FavoriteFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);

        getFavoriteArticles(1);
        initRec();
        return view;
    }

    private void initRec() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentFavoriteRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        getFavoriteArticles(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
    }

    private void getFavoriteArticles(int page) {
        articlesList = new ArrayList<>();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getFavoriteArticles(LoadData(getActivity(), API_TOKEN), page).enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    articlesList.addAll(response.body().getData().getData());
                    maxPage = response.body().getData().getLastPage();
                    articlesAndFavoriteAdapter = new ArticlesAndFavoriteAdapter(getActivity(), articlesList, true);
                    fragmentFavoriteRv.setAdapter(articlesAndFavoriteAdapter);
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
