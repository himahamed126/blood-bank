package com.example.bloodbank.ui.binding

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bloodbank.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter("app:imagePath", "app:recycled", requireAll = false)
fun setArticleImage(
        view: ImageView,
        imagePath: String,
        recycled: Boolean = false
) {
    view.clipToOutline = true
    val drawable = getDrawable(view.context, R.color.deep_gray)

    Glide.with(view)
            .load(imagePath)
            .transition(withCrossFade()).apply {
                if (recycled) {
                    error(Glide.with(view).load(drawable))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(object : CustomTarget<Drawable>() {
                                override fun onLoadCleared(placeholder: Drawable?) {
                                    view.setImageDrawable(placeholder)
                                }

                                override fun onResourceReady(
                                        resource: Drawable,
                                        transition: Transition<in Drawable>?
                                ) {
                                    view.setImageDrawable(resource)
                                }
                            })
                } else {
                    placeholder(R.color.deep_gray)
                            .error(R.color.deep_gray)
                            .into(view)
                }
            }
}

@BindingAdapter("app:isFav")
fun setFavoriteToggle(view: ImageView, isFav: Boolean) {
    if (isFav) {
        view.setImageDrawable(getDrawable(view.context, R.drawable.ic_star_fill))
    } else {
        view.setImageDrawable(getDrawable(view.context, R.drawable.ic_star_border))
    }
}
