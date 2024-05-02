package com.example.newsfeedapp.model

import com.google.gson.annotations.SerializedName

class Article {
    @SerializedName("id")
    var id: String? = null

    @JvmField
    @SerializedName("headline")
    var headline: Headline? = null

    @SerializedName("web_url")
    var webUrl: String? = null

    @JvmField
    @SerializedName("snippet")
    var snippet: String? = null

    @SerializedName("word_count")
    var wordCount: String? = null

    @SerializedName("print_section")
    var printSection: String? = null

    @JvmField
    @SerializedName("section_name")
    var sectionName: String? = null

    @JvmField
    @SerializedName("print_page")
    var printPage: String? = null

    @JvmField
    @SerializedName("pub_date")
    var pubDate: String? = null

    @SerializedName("multimedia")
    var multimedia: List<Multimedia>? = null

}
