package com.bignerdranch.android.cua

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.cua.api.SteamApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val TAG = "SteamQuery"
class SteamQuery {
    private val steamApi: SteamApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        steamApi = retrofit.create(SteamApi::class.java)
    }

    fun fetchContents(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> =
            MutableLiveData()
        val flickrRequest: Call<String> = steamApi.fetchNews()
        flickrRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d(TAG, "Response received")
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }
}