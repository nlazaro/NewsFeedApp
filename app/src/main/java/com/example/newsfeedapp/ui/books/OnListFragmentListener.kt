package com.example.newsfeedapp.ui.books

import com.example.newsfeedapp.model.Article
import com.example.newsfeedapp.model.BestSellerBook

interface OnListFragmentListener {
    fun onItemClick(item: BestSellerBook?)
}
