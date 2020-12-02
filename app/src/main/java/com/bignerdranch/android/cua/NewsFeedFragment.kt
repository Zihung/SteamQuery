package com.bignerdranch.android.cua

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.NewsItem
import java.io.IOException
import java.io.InputStream


private const val TAG = "NewsFeedFragment"

class NewsFeedFragment : Fragment() {
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsFeedView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_news_feed,
            container, false
        )
        newsFeedView = view.findViewById(R.id.fragment_news_feed)
        newsFeedView.layoutManager = GridLayoutManager(context, 1)
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
                newsFeedView.adapter = NewsAdapter(galleryItems)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_news_feed, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String):
                        Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    newsFeedViewModel.fetchGames(queryText)
                    return true
                }

                override fun onQueryTextChange(queryText: String):
                        Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })
        }
    }

    private class NewsHolder(view: View)
        : RecyclerView.ViewHolder(view) {
//        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
//        val bindDate: (CharSequence) -> Unit = itemTextView::setText

        val titleTextView: TextView =
            itemView.findViewById(R.id.news_title)
        val dateTextView: TextView = itemView.findViewById(R.id.news_date)
    }


    private inner class NewsAdapter(var crimes: List<NewsItem>)
        : RecyclerView.Adapter<NewsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType:
        Int)
                : NewsHolder {
            val view = layoutInflater.inflate(R.layout.list_item_news,
                parent, false)
            return NewsHolder(view)
        }
        override fun getItemCount() = crimes.size
        override fun onBindViewHolder(holder: NewsHolder, position:
        Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }
    }

//    private class PhotoAdapter(private val galleryItems: List<NewsItem>)
//        : RecyclerView.Adapter<NewsHolder>() {
//        override fun onCreateViewHolder(
//            parent: ViewGroup,
//            viewType: Int
//        ): NewsHolder {
//            val textView = TextView(parent.context)
//            return NewsHolder(textView)
//        }
//        override fun getItemCount(): Int = galleryItems.size
//        override fun onBindViewHolder(
//            holder: NewsHolder, position:
//            Int
//        ) {
//            val galleryItem = galleryItems[position]
//            holder.apply{
//                titleTextView.text = galleryItem.title
//                dateTextView.text = galleryItem.date
//            }
////            holder.bindTitle(galleryItem.title)
////            holder.bindDate(galleryItem.date)
//        }
//    }
}