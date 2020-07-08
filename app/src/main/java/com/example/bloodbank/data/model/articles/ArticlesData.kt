package com.example.bloodbank.data.model.articles

import com.example.bloodbank.data.model.general.GeneralData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticlesData {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null

    @SerializedName("publish_date")
    @Expose
    var publishDate: String? = null

    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null

    @SerializedName("thumbnail_full_path")
    @Expose
    var thumbnailFullPath: String? = null

    @SerializedName("is_favourite")
    @Expose
    var isFavourite: Boolean? = null

    @SerializedName("category")
    @Expose
    var category: GeneralData? = null

    @SerializedName("pivot")
    @Expose
    var pivot: Pivot? = null

}