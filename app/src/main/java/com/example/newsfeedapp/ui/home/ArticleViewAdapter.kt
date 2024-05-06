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
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleViewAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<ArticleViewAdapter.ViewHolder>() {
        private val articleList = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_home, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(article)
    }

    override fun getItemCount() = articleList.size

    fun setArticles(articles: List<Article>){
        articleList.clear()
        articleList.addAll(articles)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articlePubDate =  itemView.findViewById<TextView>(R.id.article_pub_date)
        private val articleHeadline = itemView.findViewById<TextView>(R.id.article_headline)
        private val articleSnippet = itemView.findViewById<TextView>(R.id.article_snippet)
        fun bind(article: Article){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSS", Locale.getDefault())
            val date = dateFormat.parse(article.pubDate)
            val newDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            articlePubDate.text = newDateFormat.format(date)
            articleHeadline.text = article.headline?.main
            articleSnippet.text = article.snippet
        }

    }
}

