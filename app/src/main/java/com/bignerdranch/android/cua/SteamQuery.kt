package com.bignerdranch.android.cua

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.cua.api.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "SteamQuery"
class SteamQuery {
    private val steamApi: SteamApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(SteamInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.steampowered.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        steamApi = retrofit.create(SteamApi::class.java)
    }

    fun searchGames(query: String): LiveData<List<NewsItem>> {
        return fetchGameMetadata(steamApi.searchGames(query), query)
    }



    private fun fetchGameMetadata(steamRequest: Call<SteamResponse>, query : String) : LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()

//        val steamRequest: Call<SteamResponse> = steamApi.fetchNews()
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
//                Log.d(TAG, steamResponse.toString())
                val newsResponse: NewsResponse? = steamResponse?.appnews
                var galleryItems: List<NewsItem> = newsResponse?.newsItems
                        ?: mutableListOf()
                galleryItems = galleryItems.filter {
                    it.appid == query
                }
                responseLiveData.value = galleryItems
            }
        })
        return responseLiveData
    }
}