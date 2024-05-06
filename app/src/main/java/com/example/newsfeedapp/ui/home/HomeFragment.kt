package com.example.newsfeedapp.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.core.widget.ContentLoadingProgressBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.Article
import com.example.newsfeedapp.network.APIClient
import com.example.newsfeedapp.network.CallbackResponse

class HomeFragment
    : Fragment() {
    private val client = APIClient()
    private var recyclerView: RecyclerView? = null
    private var progressSpinner: ContentLoadingProgressBar? = null
    private var savedQuery: String? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var adapter = ArticleViewAdapter()
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search).actionView as SearchView
        item.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                loadNewArticlesByQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.article_result_list, container, false)
        val localRecyclerView = view.findViewById<RecyclerView>(R.id.rvArticleList)
        recyclerView = localRecyclerView
        val context = view.context
        val linearLayoutManager = LinearLayoutManager(context)
        localRecyclerView.layoutManager = linearLayoutManager
        localRecyclerView.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextDataFromApi(page)
            }
        }
        localRecyclerView.addOnScrollListener(scrollListener!!)

        // Loads default article search
        if (savedQuery.isNullOrBlank()){
            loadNewArticlesByQuery("New York City")
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun loadNewArticlesByQuery(query: String) {
        Log.d("ArticleResultFragment", "load article with query $query")
        progressSpinner?.show()
        savedQuery = query
        scrollListener?.resetState()
        client.getArticlesByInput(object : CallbackResponse<List<Article>> {
            override fun onSuccess(model: List<Article>) {
                val adapter = recyclerView?.adapter as ArticleViewAdapter
                adapter.setNewArticles(model)
                adapter.notifyDataSetChanged()
                Log.d("ArticleResultFragment", "successfully loaded articles")
                progressSpinner?.hide()
            }

            override fun onFailure(error: Throwable?) {
                Log.d("ArticleResultFragment", "failure load article " + error!!.message)
                progressSpinner?.hide()
            }
        }, query)
    }

    private fun loadNextDataFromApi(page: Int) {
        client.getArticlesByInput(object : CallbackResponse<List<Article>> {
            override fun onSuccess(models: List<Article>) {
                val adapter = recyclerView?.adapter as ArticleViewAdapter
                adapter.addArticles(models)
                adapter.notifyDataSetChanged()
                Log.d("ArticleResultFragment", String.format("successfully loaded articles from page %d", page))
            }

            override fun onFailure(error: Throwable?) {
                Log.d("ArticleResultFragment", "failure load article " + error!!.message)
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }, savedQuery, page)
    }
}