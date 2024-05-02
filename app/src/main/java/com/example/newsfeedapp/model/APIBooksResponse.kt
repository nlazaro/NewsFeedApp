package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class APIBooksResponse {
    @SerializedName("status")
    var status: String? = null

    @JvmField
    @SerializedName("results")
    var response: BooksListResponse? = null
}