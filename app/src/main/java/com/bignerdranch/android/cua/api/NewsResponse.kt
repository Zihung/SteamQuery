package com.bignerdranch.android.cua.api

import com.google.gson.annotations.SerializedName

class NewsResponse {
    @SerializedName("newsitems")
    lateinit var newsItems: List<NewsItem>
}