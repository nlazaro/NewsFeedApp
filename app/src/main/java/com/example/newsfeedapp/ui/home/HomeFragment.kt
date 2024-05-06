package com.example.newsfeedapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.Article
import com.example.newsfeedapp.network.APIClient
import com.example.newsfeedapp.network.CallbackResponse

class HomeFragment : Fragment() {

    private val client = APIClient()
    private var recyclerView: RecyclerView? = null
    private var savedQuery: String? = null
    private var articleViewAdapter: ArticleViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflates the layout for this fragment
        val view = inflater.inflate(R.layout.article_result_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.rvArticleList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        this.recyclerView = recyclerView

        articleViewAdapter = ArticleViewAdapter(context)
        recyclerView.adapter = articleViewAdapter

        // Fetch default articles on initial load
        fetchDefaultArticles()

        return view
    }

    private fun fetchDefaultArticles() {
        client.getArticlesByInput(object : CallbackResponse<List<Article>> {
            override fun onSuccess(model: List<Article>) {
                val adapter = recyclerView?.adapter as ArticleViewAdapter
                adapter.setArticles(model)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(error: Throwable?) {
                Log.e("HomeFragment", error!!.message!!)
            }
        }, "New York")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search).actionView as SearchView
        item.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                updateAdapter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun updateAdapter(query: String) {
        savedQuery = query
        if (query.isEmpty()) {
            fetchDefaultArticles()
        } else {
            client.getArticlesByInput(object : CallbackResponse<List<Article>> {
                override fun onSuccess(model: List<Article>) {
                    val adapter = recyclerView?.adapter as ArticleViewAdapter
                    adapter.setArticles(model)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(error: Throwable?) {
                    Log.e("HomeFragment", error!!.message!!)
                }
            }, query)
        }
    }
}
