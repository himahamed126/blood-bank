package com.example.bloodbank.ui.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.ArticlesAndFavoriteAdapter;
import com.example.bloodbank.adapter.GeneralResponseAdapter;
import com.example.bloodbank.data.model.articles.Articles;
import com.example.bloodbank.data.model.articles.ArticlesData;
import com.example.bloodbank.helper.OnEndLess;
import com.example.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.helper.HelperMethods.dismissProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.getSpinnerCityData2;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;

public class ArticleFragment extends BaseFragment {
    @BindView(R.id.fragment_articles_rv_articles)
    RecyclerView fragmentArticlesRvArticles;
    @BindView(R.id.fragment_articles_sp)
    Spinner fragmentArticlesSp;
    @BindView(R.id.fragment_articles_ed_search)
    EditText fragmentArticlesEdSearch;

    private ArticlesAndFavoriteAdapter articlesAndFavoriteAdapter;
    private GeneralResponseAdapter categoriesAdapter;
    private List<ArticlesData> articlesList;
    private OnEndLess onEndLess;
    private int maxPage;
    private boolean filterPost = false;
    private static final String TAG = "ArticleFragment";
    private String keyWord;

    private int[] animationList = {R.anim.layou_animation_left_to_right, R.anim.layou_animation_left_to_right};
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAcvtivity();
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

        int resId = R.anim.layou_animation_left_to_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        fragmentArticlesRvArticles.setLayoutAnimation(animation);

        Log.i(TAG, LoadData(getActivity(), API_TOKEN));

        getCategory();
        getArticles(1);
        initRec();
        return view;
    }

    private void getCategory() {
        categoriesAdapter = new GeneralResponseAdapter(getActivity());
        getSpinnerCityData2(getClient().getCategories(), categoriesAdapter,
                fragmentArticlesSp, getString(R.string.all_articles), getActivity());
    }

    private void initRec() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentArticlesRvArticles.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if (filterPost) {
                            getArticlesWithFilter(current_page);
                        } else {
                            getArticles(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
    }

    private void getArticles(int page) {
        articlesList = new ArrayList<>();
        getClient().getArticles(LoadData(getActivity(), API_TOKEN), page).enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
//                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    articlesList.addAll(response.body().getData().getData());
                    maxPage = response.body().getData().getLastPage();
                    articlesAndFavoriteAdapter = new ArticlesAndFavoriteAdapter(getActivity(), articlesList, false);
                    fragmentArticlesRvArticles.setAdapter(articlesAndFavoriteAdapter);
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

            }
        });
    }

    private void getArticlesWithFilter(int page) {
        articlesList = new ArrayList<>();
        keyWord = fragmentArticlesEdSearch.getText().toString();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getArticlesWithFilter(LoadData(getActivity(), API_TOKEN), page,
                keyWord, fragmentArticlesSp.getSelectedItemPosition()).enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    articlesList.clear();
                    articlesList.addAll(response.body().getData().getData());
                    maxPage = response.body().getData().getLastPage();
                    articlesAndFavoriteAdapter = new ArticlesAndFavoriteAdapter(getActivity(), articlesList, false);
                    fragmentArticlesRvArticles.setAdapter(articlesAndFavoriteAdapter);
                } else {
                    Log.i(TAG, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

            }
        });
    }

    private void runAnimationAgain() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), animationList[i]);

        fragmentArticlesRvArticles.setLayoutAnimation(controller);
        articlesAndFavoriteAdapter.notifyDataSetChanged();
        fragmentArticlesRvArticles.scheduleLayoutAnimation();

    }

    @OnClick({R.id.fragment_articles_btn_search, R.id.fragment_articles_fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_articles_btn_search:
                if (categoriesAdapter.selectedId == 0 && keyWord == "" && filterPost) {
                    filterPost = false;
                    getArticles(1);
                } else {
                    getArticlesWithFilter(1);
                }
                break;
            case R.id.fragment_articles_fab_add:
                AddDonationFragment addDonationFragment = new AddDonationFragment();
                replace(addDonationFragment, getActivity().getSupportFragmentManager(), R.id.activity_home_fl_content);
                break;
        }
    }


    @Override
    public void onBack() {
        super.onBack();
    }

}