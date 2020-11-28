package com.bignerdranch.android.cua

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.cua.api.NewsItem
import com.bignerdranch.android.cua.api.NewsResponse
import com.bignerdranch.android.cua.api.SteamApi
import com.bignerdranch.android.cua.api.SteamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "SteamQuery"
class SteamQuery {
    private val steamApi: SteamApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        steamApi = retrofit.create(SteamApi::class.java)
    }

    fun fetchContents(): LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()

        val steamRequest: Call<SteamResponse> = steamApi.fetchNews()
        steamRequest.enqueue(object : Callback<SteamResponse> {
            override fun onFailure(call: Call<SteamResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
            override fun onResponse(
                call: Call<SteamResponse>,
                response: Response<SteamResponse>
            ) {
                Log.d(TAG, "Response received")
                val steamResponse: SteamResponse? = response.body()
                Log.d(TAG, steamResponse.toString())
                val newsResponse: NewsResponse? = steamResponse?.appnews
                var galleryItems: List<NewsItem> = newsResponse?.newsItems
                        ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.title.isBlank()
                }
                responseLiveData.value = galleryItems
            }
        })
        return responseLiveData
    }
}