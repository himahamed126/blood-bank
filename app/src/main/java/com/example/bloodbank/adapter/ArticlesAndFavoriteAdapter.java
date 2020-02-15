package com.example.bloodbank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.articles.ArticlesData;
import com.example.bloodbank.data.model.postToggle.PostToggle;
import com.example.bloodbank.ui.fragment.homeCycle.ArticlesDetailsFragment;

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
import static com.example.bloodbank.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.bloodbank.helper.HelperMethods.replace;
import static com.example.bloodbank.helper.HelperMethods.showProgressDialog;
import static com.example.bloodbank.helper.HelperMethods.showToast;

public class ArticlesAndFavoriteAdapter extends RecyclerView.Adapter<ArticlesAndFavoriteAdapter.ViewHolder> {
    private Context context;
    private List<ArticlesData> articlesList;
    private boolean favorite;
    private static final String TAG = "ArticlesAFavAdapter";

    public ArticlesAndFavoriteAdapter(Context context, List articlesList, boolean favorite) {
        this.context = context;
        this.articlesList = articlesList;
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        ArticlesData article = articlesList.get(position);
        holder.itemArticlesTvArticleName.setText(article.getTitle());
        onLoadImageFromUrl(holder.itemArticlesIvArticle, article.getThumbnailFullPath(), context);
        if (article.getIsFavourite()) {
            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_fill);
        } else {
            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void setAction(ViewHolder holder, int position) {
        ArticlesData article = articlesList.get(position);

        holder.itemArticlesBtnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToggleFavourite(holder, position);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticlesDetailsFragment articlesDetailsFragment = new ArticlesDetailsFragment();
                articlesDetailsFragment.articlesData = article;
                replace(articlesDetailsFragment, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_home_fl_content);
            }
        });
    }

    private void postToggleFavourite(ViewHolder holder, int position) {
        ArticlesData article = articlesList.get(position);

        article.setIsFavourite(!article.getIsFavourite());

        if (article.getIsFavourite()) {
            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_fill);
            showToast((AppCompatActivity) context, context.getString(R.string.add_to_favorite));
        } else {
            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_border);
            showToast((AppCompatActivity) context, context.getString(R.string.remove_to_favorite));
            if (favorite) {
                articlesList.remove(position);
                notifyDataSetChanged();
            }
        }

        getClient().postToggleFavourite(article.getId(), LoadData((AppCompatActivity) context, API_TOKEN)).enqueue(new Callback<PostToggle>() {
            @Override
            public void onResponse(Call<PostToggle> call, Response<PostToggle> response) {
                showProgressDialog((AppCompatActivity) context, context.getString(R.string.please_wait));
                try {
                    if (response.body().getStatus() == 1) {
                        dismissProgressDialog();
                    } else {
                        article.setIsFavourite(!article.getIsFavourite());
                        if (article.getIsFavourite()) {
                            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_fill);
                            showToast((AppCompatActivity) context, context.getString(R.string.add_to_favorite));
                            if (favorite) {
                                articlesList.add(position, article);
                                notifyDataSetChanged();
                            }
                        } else {
                            holder.itemArticlesBtnLove.setImageResource(R.drawable.ic_favorite_border);
                            showToast((AppCompatActivity) context, context.getString(R.string.remove_to_favorite));
                        }
                        Log.i(TAG, response.body().getMsg());
                    }
                } catch (
                        Exception e) {
                    Log.i(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PostToggle> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_articles_iv_article)
        ImageView itemArticlesIvArticle;
        @BindView(R.id.item_articles_btn_love)
        ImageView itemArticlesBtnLove;
        @BindView(R.id.item_articles_tv_article_name)
        TextView itemArticlesTvArticleName;
        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
