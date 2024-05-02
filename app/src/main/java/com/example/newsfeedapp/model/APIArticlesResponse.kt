package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class APIArticlesResponse {
    @SerializedName("status")
    var status: String? = null

    @JvmField
    @SerializedName("response")
    var response: ArticlesListResponse? = null
}