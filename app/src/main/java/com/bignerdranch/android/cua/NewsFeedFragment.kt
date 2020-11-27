package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.SteamApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val TAG = "NewsFeedFragment"

class NewsFeedFragment : Fragment() {
    private lateinit var newsFeedView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val steamLiveData: LiveData<String> =
            SteamQuery().fetchContents()
        steamLiveData.observe(
            this,
            Observer { responseString ->
                Log.d(TAG, "Response received: $responseString")
            })

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
}