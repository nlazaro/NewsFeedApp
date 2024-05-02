package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class BestSellerBook {
    @JvmField
    @SerializedName("rank")
    var rank = 0

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("author")
    var author: String? = null

    @JvmField
    @SerializedName("book_image")
    var bookImage: String? = null

    @JvmField
    @SerializedName("description")
    var description: String? = null
}
