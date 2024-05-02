package com.example.newsfeedapp.network

import com.example.newsfeedapp.model.APIArticlesResponse
import com.example.newsfeedapp.model.APIBooksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("svc/books/v3/lists/{date}/{list}.json")
    fun getBestSellingBooks(
        @Path("date") date: String?,
        @Path("list") list: String?,
        @Query("api-key") apiKey: String?):
            Call<APIBooksResponse?>?

    @GET("svc/search/v2/articlesearch.json")
    fun getArticles(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("sort") sort: String?,
        @Query ("fl") filter: String?,
        @Query ("begin_date") beginDate : String?,
        @Query ("api-key") apiKey : String?):
            Call<APIArticlesResponse?>?
}