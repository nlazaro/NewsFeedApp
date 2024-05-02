package com.example.newsfeedapp.ui.books

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.BestSellerBook

class BooksViewAdapter(private val context: Context, private val books: List<BestSellerBook>) : RecyclerView.Adapter<BooksViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_books, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bestSellerBook = books[position]
        holder.bind(bestSellerBook)
    }

    override fun getItemCount() = books.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle =  itemView.findViewById<TextView>(R.id.book_title)
        private val bookImage = itemView.findViewById<ImageView>(R.id.book_image)
        private val bookAuthor= itemView.findViewById<TextView>(R.id.book_author)
        private val bookRanking = itemView.findViewById<TextView>(R.id.ranking)
        private val bookDescription = itemView.findViewById<TextView>(R.id.book_description)
        fun bind(book: BestSellerBook){
            bookTitle.text = book.title
            bookAuthor.text = book.author
            bookRanking.text = book.rank.toString()
            bookDescription.text = book.description
            Glide.with(context).load(book.bookImage).into(bookImage)
        }

    }
}

