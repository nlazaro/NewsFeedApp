package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class BooksListResponse {
    @SerializedName("list_name")
    var listName: String? = null

    @JvmField
    @SerializedName("books")
    var books: List<BestSellerBook>? = null
}
