package com.example.newsfeedapp.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.BestSellerBook
import com.example.newsfeedapp.network.APIClient
import com.example.newsfeedapp.network.CallbackResponse

class BooksFragment : Fragment(), OnListFragmentListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?):
            View?{
        val view = inflater.inflate(R.layout.best_seller_books_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.rvBooksList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        updateAdapter(recyclerView)
        return view
    }

    private fun updateAdapter(recyclerView: RecyclerView){
        val apiClient = APIClient()
        apiClient.getBestSellerBooksList(object: CallbackResponse<List<BestSellerBook>>{
            override fun onSuccess(model: List<BestSellerBook>)
            {
                if (isAdded){
                    recyclerView.adapter = BooksViewAdapter(requireContext(), model)
                }
            }

            override fun onFailure(error: Throwable?) {
                Log.e("BooksFragment", error!!.message!!)
            }
        })
    }

    override fun onItemClick(item: BestSellerBook?) {}

}