package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class Headline {
    @JvmField
    @SerializedName("main")
    var main: String? = null
}
