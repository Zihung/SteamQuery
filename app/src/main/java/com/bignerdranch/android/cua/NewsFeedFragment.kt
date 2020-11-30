package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.NewsItem


private const val TAG = "NewsFeedFragment"

class NewsFeedFragment : Fragment() {
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsFeedView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_news_feed,
            container, false)
        newsFeedView = view.findViewById(R.id.fragment_news_feed)
        newsFeedView.layoutManager = GridLayoutManager(context, 3)
        return view
    }
    companion object {
        fun newInstance() = NewsFeedFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        newsFeedViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer { galleryItems ->
                newsFeedView.adapter = PhotoAdapter(galleryItems)
            })
    }

    private class NewsHolder(itemTextView: TextView)
        : RecyclerView.ViewHolder(itemTextView) {
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    private class PhotoAdapter(private val galleryItems:  List<NewsItem>)
        : RecyclerView.Adapter<NewsHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): NewsHolder {
            val textView = TextView(parent.context)
            return NewsHolder(textView)
        }
        override fun getItemCount(): Int = galleryItems.size
        override fun onBindViewHolder(holder: NewsHolder, position:
        Int) {
            val galleryItem = galleryItems[position]
            holder.bindTitle(galleryItem.title)
        }
    }
}