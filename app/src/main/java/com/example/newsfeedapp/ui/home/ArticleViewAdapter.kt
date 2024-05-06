package com.example.newsfeedapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.Article
import com.example.newsfeedapp.model.ArticlesListResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Recycler view of articles from search result
 */
class ArticleViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articleList: MutableList<Article> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ARTICLE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_home, parent, false)
            ArticleViewHolder(view)
        } else  {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_article_first_page, parent, false)
            FirstPageArticleViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FirstPageArticleViewHolder) {
            holder.firstPageHeader.text = holder.itemView.context.getString(R.string.first_page, articleList[position].sectionName)
        }
        if (holder is ArticleViewHolder) {
            val article = articleList[position]
            val articleViewHolder = holder
            articleViewHolder.headlineView.text = article.headline!!.main
            articleViewHolder.snippetView.text = article.snippet
            try {
                val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSS", Locale.getDefault())
                val date = utcDateFormat.parse(article.pubDate)
                val newDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                articleViewHolder.dateView.text = newDateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= articleList.size) {
            VIEW_TYPE_LOADING
        } else if ("1" == articleList[position].printPage) {
            VIEW_TYPE_FIRST_PAGE_ARTICLE
        } else {
            VIEW_TYPE_ARTICLE
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setNewArticles(articles: List<Article>?) {
        articleList.clear()
        articleList.addAll(articles!!)
    }

    fun addArticles(articles: List<Article>?) {
        articleList.addAll(articles!!)
    }

    internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    internal open class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headlineView: TextView
        val snippetView: TextView
        val dateView: TextView

        init {
            dateView = view.findViewById(R.id.article_pub_date)
            headlineView = view.findViewById(R.id.article_headline)
            snippetView = view.findViewById(R.id.article_snippet)
        }
    }

    internal class FirstPageArticleViewHolder(view: View) : ArticleViewHolder(view) {
        val firstPageHeader: TextView

        init {
            firstPageHeader = view.findViewById(R.id.first_page_header)
        }
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ARTICLE = 1
        const val VIEW_TYPE_FIRST_PAGE_ARTICLE = 2
    }
}