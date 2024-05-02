package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class ArticlesListResponse {
    @JvmField
    @SerializedName("docs")
    var docs: List<Article>? = null
}
