package com.example.newsfeedapp.network

import com.example.newsfeedapp.BuildConfig.NYTIMES_API_KEY
import com.example.newsfeedapp.model.APIArticlesResponse
import com.example.newsfeedapp.model.APIBooksResponse
import com.example.newsfeedapp.model.Article
import com.example.newsfeedapp.model.ArticlesListResponse
import com.example.newsfeedapp.model.BestSellerBook
import com.example.newsfeedapp.model.BooksListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = NYTIMES_API_KEY
private const val API_FILTER = "headline, web_url, snippet, pub_date, word_count, print_page, print_section, section_name"
private const val BEGIN_DATE = "20100101"
private const val SORT_BY = "newest"
private const val BASE_URL = "https://api.nytimes.com"
private const val BOOKS_LIST = "hardcover-nonfiction"
class APIClient {
    private val apiService: APIService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(APIService::class.java)
    }
    fun getBestSellerBooksList(booksListResponse: CallbackResponse<List<BestSellerBook>>) {
        val current = apiService.getBestSellingBooks("current", BOOKS_LIST, API_KEY)
        current?.enqueue(object: Callback<APIBooksResponse?> {
            override fun onResponse(
                call: Call<APIBooksResponse?>,
                response: Response<APIBooksResponse?>
            ) {
                val model = response.body()
                val books = model?.response?.books
                if (response.isSuccessful && books != null) {
                    booksListResponse.onSuccess(books)
                } else {
                    booksListResponse.onFailure(Throwable("error: " + response.code() + response.message()))
                }
            }

            override fun onFailure(
                call: Call<APIBooksResponse?>,
                t: Throwable
            ) {
                booksListResponse.onFailure(t)
            }
        }
        )}

    // Get articles by specific input
    fun getArticlesByInput(articlesListResponse: CallbackResponse<List<Article>>, query: String?, pageNumber: Int){
        val current = apiService.getArticles(
            query,
            pageNumber,
            SORT_BY,
            API_FILTER,
            BEGIN_DATE,
            API_KEY
        )
        current?.enqueue(object: Callback<APIArticlesResponse?>{
            override fun onResponse(
                call: Call<APIArticlesResponse?>,
                response: Response<APIArticlesResponse?>
            ) {
                val model = response.body()
                val docs = model?.response?.docs
                if (response.isSuccessful && docs != null){
                    articlesListResponse.onSuccess(docs)
                }
                else{
                    articlesListResponse.onFailure(Throwable("error: " + response.code() + response.message()))
                }
            }

            override fun onFailure(call: Call<APIArticlesResponse?>, t: Throwable) {
                articlesListResponse.onFailure(t)
            }

        })
    }
}