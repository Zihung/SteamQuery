package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.NewsItem


private const val TAG = "NewsFeedFragment"
private const val ARG_APPS = "apps_map"
private const val PRE_SEARCH = "pre_search"

class NewsFeedFragment : Fragment() {
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsFeedView: RecyclerView
    private lateinit var appsMap : HashMap<String, String>
    private lateinit var presearch : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel::class.java)
        appsMap = arguments?.getSerializable(ARG_APPS) as HashMap<String, String>
        presearch = arguments!!.getString(PRE_SEARCH).toString()


        val appid : String? = appsMap[presearch.toLowerCase()]
        Log.d(TAG, "Name: $presearch, Appid: $appid")

        if (appid != null) {
            newsFeedViewModel.fetchGames(appid)
        }
        else{
            val text = "Game not found. Please enter full name of game including spaces and special characters."
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(context, text, duration)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }


        Log.d(TAG, "args bundle app map: $appsMap")



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
        fun newInstance(appsMap: HashMap<String, String>): NewsFeedFragment {

            val args = Bundle().apply{
//                putAll(ARG_APPS, appsMap)
                putSerializable(ARG_APPS, appsMap)
            }
            return NewsFeedFragment().apply{
                arguments = args
            }
        }

        fun searchInstance(appsMap: HashMap<String, String>, query: String): NewsFeedFragment {

            val args = Bundle().apply{
//                putAll(ARG_APPS, appsMap)
                putSerializable(ARG_APPS, appsMap)
                putString(PRE_SEARCH, query)
            }



            return NewsFeedFragment().apply{
                arguments = args
            }
        }


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

                    // convert name into app id
                    val appid : String? = appsMap[queryText.toLowerCase()]
                    Log.d(TAG, "Name: $queryText, Appid: $appid")

                    if (appid != null) {
                        newsFeedViewModel.fetchGames(appid)
                    }
                    else{
                        val text = "Game not found. Please enter full name of game including spaces and special characters."
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }
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

    private inner class NewsHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {


        val titleTextView: TextView =
            itemView.findViewById(R.id.news_title)
        val previewTextView: TextView = itemView.findViewById(R.id.news_preview)

        private lateinit var newsItem: NewsItem
        init {
            itemView.setOnClickListener(this)
        }

        fun bindNewsItem(item: NewsItem) {
            newsItem = item
        }

        override fun onClick(view: View) {
            val intent = NewsItemPageActivity.newIntent(requireContext(), newsItem.newsPageUri)
            startActivity(intent)
        }
    }


    private inner class NewsAdapter(var news: List<NewsItem>)
        : RecyclerView.Adapter<NewsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType:
        Int)
                : NewsHolder {
            val view = layoutInflater.inflate(R.layout.list_item_news,
                parent, false)
            return NewsHolder(view)
        }
        override fun getItemCount() = news.size
        override fun onBindViewHolder(holder: NewsHolder, position:
        Int) {
            val art = news[position]
            holder.bindNewsItem(art)
            holder.apply {
                previewTextView.text = art.appid
                titleTextView.text = art.title

            }
        }
    }
}